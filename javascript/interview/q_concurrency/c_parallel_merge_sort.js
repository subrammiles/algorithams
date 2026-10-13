/*
 * Parallel sort with worker_threads: split into chunks, sort each in a worker, k-way merge.
 * CPU-bound work needs workers - async/await alone runs on one core.
 */
const assert = require('node:assert/strict');
const { Worker, isMainThread, parentPort, workerData } = require('node:worker_threads');

if (!isMainThread) {
  parentPort.postMessage(Float64Array.from(workerData).sort());
} else {
  const sortInWorker = (chunk) =>
    new Promise((resolve, reject) => {
      const w = new Worker(__filename, { workerData: chunk });
      w.once('message', resolve);
      w.once('error', reject);
    });

  const mergeAll = (chunks) => {
    const out = [];
    const idx = chunks.map(() => 0);
    const total = chunks.reduce((s, c) => s + c.length, 0);
    while (out.length < total) {
      let best = -1;
      chunks.forEach((c, i) => {
        if (idx[i] < c.length && (best === -1 || c[idx[i]] < chunks[best][idx[best]])) best = i;
      });
      out.push(chunks[best][idx[best]++]);
    }
    return out;
  };

  (async () => {
    const data = Array.from({ length: 200_000 }, () => Math.floor(Math.random() * 1e9) - 5e8);
    const workers = 4;
    const size = Math.ceil(data.length / workers);
    const start = Date.now();
    const chunks = await Promise.all(
      Array.from({ length: workers }, (_, i) => sortInWorker(data.slice(i * size, (i + 1) * size))),
    );
    const result = mergeAll(chunks);
    console.log(`parallel sort of ${data.length} numbers took ${Date.now() - start} ms`);
    assert.deepEqual(result, [...data].sort((a, b) => a - b));
    console.log('all checks passed');
  })();
}
