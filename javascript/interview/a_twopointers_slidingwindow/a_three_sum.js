/*
 * 3Sum: sort, fix i, two pointers on the rest, skip duplicates. O(n^2).
 * Note: Array.prototype.sort() compares as strings by default - always pass (a, b) => a - b.
 */
const assert = require('node:assert/strict');

function threeSum(nums) {
  const a = [...nums].sort((x, y) => x - y);
  const result = [];
  for (let i = 0; i < a.length - 2; i++) {
    if (i > 0 && a[i] === a[i - 1]) continue;
    if (a[i] > 0) break;
    let lo = i + 1;
    let hi = a.length - 1;
    while (lo < hi) {
      const sum = a[i] + a[lo] + a[hi];
      if (sum < 0) lo++;
      else if (sum > 0) hi--;
      else {
        result.push([a[i], a[lo], a[hi]]);
        while (lo < hi && a[lo] === a[lo + 1]) lo++;
        while (lo < hi && a[hi] === a[hi - 1]) hi--;
        lo++;
        hi--;
      }
    }
  }
  return result;
}

assert.deepEqual(threeSum([-1, 0, 1, 2, -1, -4]), [[-1, -1, 2], [-1, 0, 1]]);
assert.deepEqual(threeSum([0, 0, 0, 0]), [[0, 0, 0]]);
assert.deepEqual(threeSum([1, 2, 3]), []);
console.log('all checks passed');
