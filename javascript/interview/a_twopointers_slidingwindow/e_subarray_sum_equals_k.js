/*
 * Count subarrays summing to k with prefix sums + a Map of prefix counts. O(n).
 */
const assert = require('node:assert/strict');

function subarraySum(nums, k) {
  const seen = new Map([[0, 1]]);
  let prefix = 0;
  let count = 0;
  for (const x of nums) {
    prefix += x;
    count += seen.get(prefix - k) ?? 0;
    seen.set(prefix, (seen.get(prefix) ?? 0) + 1);
  }
  return count;
}

assert.equal(subarraySum([1, 1, 1], 2), 2);
assert.equal(subarraySum([3, 4, 7, 2, -3, 1, 4, 2], 7), 4);
assert.equal(subarraySum([0, 0], 0), 3);
console.log('all checks passed');
