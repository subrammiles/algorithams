/*
 * K closest points (max-heap of size k), kth largest in a stream, ugly numbers (3 pointers), last stone weight.
 */
const assert = require('node:assert/strict');
const { Heap } = require('../../lib/heap');

function kClosest(points, k) {
  const d = ([x, y]) => x * x + y * y;
  const heap = new Heap((a, b) => d(b) - d(a)); // max-heap on distance
  for (const p of points) {
    heap.push(p);
    if (heap.size > k) heap.pop();
  }
  return heap.a.sort((p, q) => p[0] - q[0] || p[1] - q[1]);
}

class KthLargest {
  constructor(k, nums) {
    this.k = k;
    this.heap = new Heap();
    nums.forEach((x) => this.add(x));
  }

  add(val) {
    this.heap.push(val);
    if (this.heap.size > this.k) this.heap.pop();
    return this.heap.peek();
  }
}

function nthUglyNumber(n) {
  const ugly = [1];
  let [i2, i3, i5] = [0, 0, 0];
  while (ugly.length < n) {
    const next = Math.min(ugly[i2] * 2, ugly[i3] * 3, ugly[i5] * 5);
    ugly.push(next);
    if (next === ugly[i2] * 2) i2++;
    if (next === ugly[i3] * 3) i3++;
    if (next === ugly[i5] * 5) i5++;
  }
  return ugly[n - 1];
}

function lastStoneWeight(stones) {
  const heap = new Heap((a, b) => b - a);
  stones.forEach((s) => heap.push(s));
  while (heap.size > 1) {
    const a = heap.pop();
    const b = heap.pop();
    if (a !== b) heap.push(a - b);
  }
  return heap.size ? heap.peek() : 0;
}

assert.deepEqual(kClosest([[1, 3], [-2, 2]], 1), [[-2, 2]]);
assert.deepEqual(kClosest([[3, 3], [5, -1], [-2, 4]], 2), [[-2, 4], [3, 3]]);
const kth = new KthLargest(3, [4, 5, 8, 2]);
assert.deepEqual([3, 5, 10, 9, 4].map((x) => kth.add(x)), [4, 5, 5, 8, 8]);
assert.equal(nthUglyNumber(10), 12);
assert.equal(nthUglyNumber(1), 1);
assert.equal(lastStoneWeight([2, 7, 4, 1, 8, 1]), 1);
console.log('all checks passed');
