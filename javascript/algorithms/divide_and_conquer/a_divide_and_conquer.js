/*
 * Divide and conquer: count inversions (merge sort), max subarray (left / right / crossing),
 * majority element. O(n log n) each.
 */
const assert = require('node:assert/strict');

function countInversions(nums) {
  const sortCount = (a) => {
    if (a.length <= 1) return [a, 0];
    const mid = a.length >> 1;
    const [left, x] = sortCount(a.slice(0, mid));
    const [right, y] = sortCount(a.slice(mid));
    const merged = [];
    let count = x + y;
    let i = 0;
    let j = 0;
    while (i < left.length && j < right.length) {
      if (left[i] <= right[j]) merged.push(left[i++]);
      else {
        count += left.length - i; // remaining left elements are all bigger
        merged.push(right[j++]);
      }
    }
    return [[...merged, ...left.slice(i), ...right.slice(j)], count];
  };
  return sortCount(nums)[1];
}

function maxSubarray(a, lo = 0, hi = a.length - 1) {
  if (lo === hi) return a[lo];
  const mid = (lo + hi) >> 1;
  let bestLeft = -Infinity;
  let bestRight = -Infinity;
  for (let i = mid, s = 0; i >= lo; i--) bestLeft = Math.max(bestLeft, (s += a[i]));
  for (let i = mid + 1, s = 0; i <= hi; i++) bestRight = Math.max(bestRight, (s += a[i]));
  return Math.max(bestLeft + bestRight, maxSubarray(a, lo, mid), maxSubarray(a, mid + 1, hi));
}

function majorityElement(a, lo = 0, hi = a.length - 1) {
  if (lo === hi) return a[lo];
  const mid = (lo + hi) >> 1;
  const left = majorityElement(a, lo, mid);
  const right = majorityElement(a, mid + 1, hi);
  if (left === right) return left;
  const count = (x) => a.slice(lo, hi + 1).filter((v) => v === x).length;
  return count(left) > count(right) ? left : right;
}

assert.equal(countInversions([2, 4, 1, 3, 5]), 3);
assert.equal(countInversions([5, 4, 3, 2, 1]), 10);
assert.equal(countInversions([1, 2, 3]), 0);
assert.equal(maxSubarray([-2, 1, -3, 4, -1, 2, 1, -5, 4]), 6);
assert.equal(maxSubarray([-3, -1, -2]), -1);
assert.equal(majorityElement([2, 2, 1, 1, 1, 2, 2]), 2);
console.log('all checks passed');
