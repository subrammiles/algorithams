/*
 * Zero-Even-Odd ordering with async "semaphores" (permission handed from task to task).
 * Async tasks interleave at every await, so the same ordering bugs as with threads can happen.
 */
const assert = require('node:assert/strict');

class Semaphore {
  constructor(permits) {
    this.permits = permits;
    this.waiters = [];
  }

  async acquire() {
    if (this.permits > 0) {
      this.permits--;
      return;
    }
    await new Promise((resolve) => this.waiters.push(resolve));
  }

  release() {
    if (this.waiters.length) this.waiters.shift()();
    else this.permits++;
  }
}

async function run(n) {
  const out = [];
  const zeroTurn = new Semaphore(1);
  const oddTurn = new Semaphore(0);
  const evenTurn = new Semaphore(0);
  const zero = async () => {
    for (let i = 1; i <= n; i++) {
      await zeroTurn.acquire();
      out.push(0);
      (i % 2 ? oddTurn : evenTurn).release();
    }
  };
  const odd = async () => {
    for (let i = 1; i <= n; i += 2) {
      await oddTurn.acquire();
      out.push(i);
      zeroTurn.release();
    }
  };
  const even = async () => {
    for (let i = 2; i <= n; i += 2) {
      await evenTurn.acquire();
      out.push(i);
      zeroTurn.release();
    }
  };
  await Promise.all([even(), odd(), zero()]); // start order doesn't matter
  return out.join('');
}

(async () => {
  assert.equal(await run(5), '0102030405');
  assert.equal(await run(2), '0102');
  console.log('all checks passed');
})();
