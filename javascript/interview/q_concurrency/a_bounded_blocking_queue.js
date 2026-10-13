/*
 * Async bounded queue (producer-consumer) for Node's event loop.
 * There are no threads to block, so put()/take() return Promises that resolve when space / an
 * item is available. Same idea as a blocking queue: back-pressure on producers.
 * (Real threads in Node: worker_threads + SharedArrayBuffer + Atomics.wait.)
 */
const assert = require('node:assert/strict');

class AsyncBoundedQueue {
  constructor(capacity) {
    this.capacity = capacity;
    this.items = [];
    this.waitingTakers = []; // resolve functions
    this.waitingPutters = []; // [resolve, item]
  }

  put(item) {
    if (this.waitingTakers.length) {
      this.waitingTakers.shift()(item); // hand directly to a waiting consumer
      return Promise.resolve();
    }
    if (this.items.length < this.capacity) {
      this.items.push(item);
      return Promise.resolve();
    }
    return new Promise((resolve) => this.waitingPutters.push([resolve, item]));
  }

  take() {
    if (this.items.length) {
      const item = this.items.shift();
      if (this.waitingPutters.length) {
        const [resolve, pending] = this.waitingPutters.shift();
        this.items.push(pending);
        resolve();
      }
      return Promise.resolve(item);
    }
    return new Promise((resolve) => this.waitingTakers.push(resolve));
  }

  get size() {
    return this.items.length;
  }
}

async function main() {
  const q = new AsyncBoundedQueue(5);
  const producers = 4;
  const perProducer = 2000;
  let maxSeen = 0;
  const produce = async () => {
    for (let i = 1; i <= perProducer; i++) {
      await q.put(i);
      maxSeen = Math.max(maxSeen, q.size);
    }
  };
  const consume = async () => {
    let sum = 0;
    for (let i = 0; i < perProducer; i++) sum += await q.take();
    return sum;
  };
  const sums = await Promise.all([
    ...Array.from({ length: producers }, produce),
    ...Array.from({ length: producers }, consume),
  ]);
  const consumed = sums.slice(producers).reduce((a, b) => a + b, 0);
  assert.equal(consumed, (producers * perProducer * (perProducer + 1)) / 2);
  assert.ok(maxSeen <= 5, 'capacity respected');
  assert.equal(q.size, 0);
  console.log('all checks passed');
}

main();
