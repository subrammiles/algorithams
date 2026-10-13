/*
 * Search in a rotated sorted array: one half around mid is always sorted. O(log n).
 */
const assert = require('node:assert/strict');

function search(a, target) {
  let lo = 0;
  let hi = a.length - 1;
  while (lo <= hi) {
    const mid = (lo + hi) >> 1;
    if (a[mid] === target) return mid;
    if (a[lo] <= a[mid]) {
      if (a[lo] <= target && target < a[mid]) hi = mid - 1;
      else lo = mid + 1;
    } else if (a[mid] < target && target <= a[hi]) lo = mid + 1;
    else hi = mid - 1;
  }
  return -1;
}

function findMinIndex(a) {
  let lo = 0;
  let hi = a.length - 1;
  while (lo < hi) {
    const mid = (lo + hi) >> 1;
    if (a[mid] > a[hi]) lo = mid + 1;
    else hi = mid;
  }
  return lo;
}

const a = [4, 5, 6, 7, 0, 1, 2];
assert.equal(search(a, 0), 4);
assert.equal(search(a, 3), -1);
assert.equal(search([1, 3], 3), 1);
assert.equal(findMinIndex(a), 4);
console.log('all checks passed');
