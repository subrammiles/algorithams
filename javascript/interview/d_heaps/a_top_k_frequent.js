/*
 * Top k frequent: size-k min-heap O(n log k), bucket sort O(n); kth largest by quickselect.
 */
const assert = require('node:assert/strict');
const { Heap } = require('../../lib/heap');

function frequencies(nums) {
  const freq = new Map();
  for (const x of nums) freq.set(x, (freq.get(x) ?? 0) + 1);
  return freq;
}

function topKHeap(nums, k) {
  const heap = new Heap((a, b) => a[1] - b[1]);
  for (const entry of frequencies(nums)) {
    heap.push(entry);
    if (heap.size > k) heap.pop();
  }
  const result = [];
  while (heap.size) result.unshift(heap.pop()[0]);
  return result;
}

function topKBucket(nums, k) {
  const buckets = Array.from({ length: nums.length + 1 }, () => []);
  for (const [value, f] of frequencies(nums)) buckets[f].push(value);
  return buckets.reverse().flat().slice(0, k);
}

function kthLargestQuickselect(nums, k) {
  const a = [...nums];
  const target = a.length - k;
  let lo = 0;
  let hi = a.length - 1;
  for (;;) {
    const p = lo + Math.floor(Math.random() * (hi - lo + 1));
    [a[p], a[hi]] = [a[hi], a[p]];
    let store = lo;
    for (let i = lo; i < hi; i++) {
      if (a[i] < a[hi]) {
        // keep store++ out of the destructuring swap: the right side is evaluated first
        [a[i], a[store]] = [a[store], a[i]];
        store++;
      }
    }
    [a[store], a[hi]] = [a[hi], a[store]];
    if (store === target) return a[store];
    if (store < target) lo = store + 1;
    else hi = store - 1;
  }
}

const nums = [1, 1, 1, 2, 2, 3];
assert.deepEqual(topKHeap(nums, 2), [1, 2]);
assert.deepEqual(topKBucket(nums, 2), [1, 2]);
assert.equal(kthLargestQuickselect([3, 2, 3, 1, 2, 4, 5, 5, 6], 4), 4);
console.log('all checks passed');
