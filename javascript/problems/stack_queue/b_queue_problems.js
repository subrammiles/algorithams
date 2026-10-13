/*
 * Stack using one queue, moving average, recent counter. A plain array + head index is an O(1) queue.
 */
const assert = require('node:assert/strict');

class StackUsingQueue {
  q = [];

  push(x) {
    this.q.push(x);
    for (let i = 1; i < this.q.length; i++) this.q.push(this.q.shift()); // rotate x to the front
  }

  pop() {
    return this.q.shift();
  }

  top() {
    return this.q[0];
  }

  empty() {
    return this.q.length === 0;
  }
}

class MovingAverage {
  constructor(size) {
    this.size = size;
    this.window = [];
    this.head = 0;
    this.sum = 0;
  }

  next(val) {
    this.window.push(val);
    this.sum += val;
    if (this.window.length - this.head > this.size) this.sum -= this.window[this.head++];
    return this.sum / (this.window.length - this.head);
  }
}

class RecentCounter {
  pings = [];
  head = 0;

  ping(t) {
    this.pings.push(t);
    while (this.pings[this.head] < t - 3000) this.head++;
    return this.pings.length - this.head;
  }
}

const s = new StackUsingQueue();
[1, 2, 3].forEach((x) => s.push(x));
assert.equal(s.top(), 3);
assert.equal(s.pop(), 3);
assert.equal(s.pop(), 2);
assert.equal(s.empty(), false);
const m = new MovingAverage(3);
assert.equal(m.next(1), 1);
assert.equal(m.next(10), 5.5);
assert.ok(Math.abs(m.next(3) - 14 / 3) < 1e-9);
assert.equal(m.next(5), 6);
const rc = new RecentCounter();
assert.deepEqual([1, 100, 3001, 3002].map((t) => rc.ping(t)), [1, 2, 3, 3]);
console.log('all checks passed');
