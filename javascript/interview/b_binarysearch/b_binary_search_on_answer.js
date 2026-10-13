/*
 * Binary search on the answer: Koko eating bananas, split array largest sum. O(n log range).
 */
const assert = require('node:assert/strict');

function minEatingSpeed(piles, h) {
  let lo = 1;
  let hi = Math.max(...piles);
  while (lo < hi) {
    const mid = Math.floor((lo + hi) / 2);
    const hours = piles.reduce((sum, p) => sum + Math.ceil(p / mid), 0);
    if (hours <= h) hi = mid;
    else lo = mid + 1;
  }
  return lo;
}

function splitArray(nums, m) {
  const partsNeeded = (limit) => {
    let parts = 1;
    let running = 0;
    for (const x of nums) {
      if (running + x > limit) {
        parts++;
        running = 0;
      }
      running += x;
    }
    return parts;
  };
  let lo = Math.max(...nums);
  let hi = nums.reduce((a, b) => a + b, 0);
  while (lo < hi) {
    const mid = Math.floor((lo + hi) / 2);
    if (partsNeeded(mid) <= m) hi = mid;
    else lo = mid + 1;
  }
  return lo;
}

assert.equal(minEatingSpeed([3, 6, 7, 11], 8), 4);
assert.equal(minEatingSpeed([30, 11, 23, 4, 20], 5), 30);
assert.equal(splitArray([7, 2, 5, 10, 8], 2), 18);
assert.equal(splitArray([1, 2, 3, 4, 5], 2), 9);
console.log('all checks passed');
