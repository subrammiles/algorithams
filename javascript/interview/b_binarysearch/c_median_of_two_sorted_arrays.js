/*
 * Median of two sorted arrays: binary search the partition of the smaller array. O(log min(m, n)).
 */
const assert = require('node:assert/strict');

function findMedian(a, b) {
  if (a.length > b.length) return findMedian(b, a);
  const m = a.length;
  const n = b.length;
  const half = (m + n + 1) >> 1;
  let lo = 0;
  let hi = m;
  while (lo <= hi) {
    const i = (lo + hi) >> 1;
    const j = half - i;
    const aLeft = i === 0 ? -Infinity : a[i - 1];
    const aRight = i === m ? Infinity : a[i];
    const bLeft = j === 0 ? -Infinity : b[j - 1];
    const bRight = j === n ? Infinity : b[j];
    if (aLeft <= bRight && bLeft <= aRight) {
      const leftMax = Math.max(aLeft, bLeft);
      return (m + n) % 2 ? leftMax : (leftMax + Math.min(aRight, bRight)) / 2;
    }
    if (aLeft > bRight) hi = i - 1;
    else lo = i + 1;
  }
  throw new Error('inputs are not sorted');
}

assert.equal(findMedian([1, 3], [2]), 2);
assert.equal(findMedian([1, 2], [3, 4]), 2.5);
assert.equal(findMedian([], [1]), 1);
console.log('all checks passed');
