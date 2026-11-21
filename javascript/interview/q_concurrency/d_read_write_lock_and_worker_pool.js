/*
 * Async read-write lock and a concurrency-limited worker pool (full explanations in the Java version).
 *
 * Node runs JS on one thread, but async critical sections still interleave at every `await`, so
 * "many readers OR one writer" is a real problem (e.g. refreshing a cached token while requests read it).
 *
 * AsyncRWLock: a FIFO queue of waiters. The front is granted when possible; a run of readers at the
 * front is granted together. A writer waits for active readers to finish, and readers that arrive
 * after a waiting writer queue behind it -> fair to both, nobody starves.
 *
 * runPool(tasks, limit): run async task functions with at most `limit` in flight, results in input
 * order (like p-limit / Promise.all with a cap). For CPU-bound work use worker_threads instead.
 */
const assert = require('node:assert/strict');

class AsyncRWLock {
  #readers = 0;
  #writing = false;
  #queue = []; // { type: 'r' | 'w', resolve }

  #drain() {
    while (this.#queue.length) {
      const next = this.#queue[0];
      if (next.type === 'w') {
        if (this.#writing || this.#readers > 0) return;
        this.#queue.shift();
        this.#writing = true;
        next.resolve();
        return;
      }
      if (this.#writing) return;
      this.#queue.shift();
      this.#readers++;
      next.resolve();
    }
  }

  acquireRead() {
    return new Promise((resolve) => {
      this.#queue.push({ type: 'r', resolve });
      this.#drain();
    });
  }

  acquireWrite() {
    return new Promise((resolve) => {
      this.#queue.push({ type: 'w', resolve });
      this.#drain();
    });
  }

  releaseRead() {
    this.#readers--;
    this.#drain();
  }

  releaseWrite() {
    this.#writing = false;
    this.#drain();
  }
}

async function runPool(tasks, limit) {
  const results = new Array(tasks.length);
  let next = 0;
  async function worker() {
    while (next < tasks.length) {
      const i = next++; // safe: no await between the check and the increment
      try {
        results[i] = { ok: true, value: await tasks[i]() };
      } catch (error) {
        results[i] = { ok: false, error }; // one failure must not stop the worker
      }
    }
  }
  await Promise.all(Array.from({ length: Math.min(limit, tasks.length) }, worker));
  return results;
}

const tick = () => new Promise((r) => setImmediate(r));

(async () => {
  const rw = new AsyncRWLock();
  let readers = 0;
  let writers = 0;
  let violations = 0;
  let maxReaders = 0;
  let counter = 0;
  const jobs = [];
  for (let i = 0; i < 200; i++) {
    if (i % 5 === 0) {
      jobs.push(
        (async () => {
          await rw.acquireWrite();
          if (++writers !== 1 || readers !== 0) violations++;
          const seen = counter;
          await tick(); // yield inside the critical section: a broken lock would lose updates
          counter = seen + 1;
          writers--;
          rw.releaseWrite();
        })(),
      );
    } else {
      jobs.push(
        (async () => {
          await rw.acquireRead();
          readers++;
          maxReaders = Math.max(maxReaders, readers);
          if (writers !== 0) violations++;
          await tick();
          readers--;
          rw.releaseRead();
        })(),
      );
    }
  }
  await Promise.all(jobs);
  assert.equal(violations, 0);
  assert.equal(counter, 40);
  assert.ok(maxReaders > 1, 'readers should share the lock');

  let inFlight = 0;
  let maxInFlight = 0;
  const tasks = Array.from({ length: 50 }, (_, i) => async () => {
    maxInFlight = Math.max(maxInFlight, ++inFlight);
    await tick();
    inFlight--;
    if (i === 7) throw new Error('boom');
    return i * i;
  });
  const results = await runPool(tasks, 4);
  assert.ok(maxInFlight <= 4);
  assert.equal(maxInFlight, 4);
  assert.equal(results[7].ok, false);
  assert.equal(results[9].value, 81);
  assert.equal(results.filter((r) => r.ok).length, 49);
  console.log('all checks passed');
})().catch((e) => {
  console.error(e);
  process.exit(1);
});
