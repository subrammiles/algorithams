/*
 * Running median with two heaps: max-heap low half, min-heap high half. add O(log n), median O(1).
 */
const assert = require('node:assert/strict');
const { Heap } = require('../../lib/heap');

class MedianFinder {
  low = new Heap((a, b) => b - a);
  high = new Heap((a, b) => a - b);

  addNum(x) {
    this.low.push(x);
    this.high.push(this.low.pop());
    if (this.high.size > this.low.size) this.low.push(this.high.pop());
  }

  findMedian() {
    if (this.low.size > this.high.size) return this.low.peek();
    return (this.low.peek() + this.high.peek()) / 2;
  }
}

const m = new MedianFinder();
m.addNum(1);
m.addNum(2);
assert.equal(m.findMedian(), 1.5);
m.addNum(3);
assert.equal(m.findMedian(), 2);
m.addNum(-5);
m.addNum(10);
assert.equal(m.findMedian(), 2);
console.log('all checks passed');
