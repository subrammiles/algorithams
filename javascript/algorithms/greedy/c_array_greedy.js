/*
 * Array greedy: jump game, assign cookies, boats, two city scheduling; greedy coins only work for
 * canonical systems ({1,3,4}, 6 -> greedy 3 coins, optimal 2).
 */
const assert = require('node:assert/strict');

function canJump(nums) {
  let furthest = 0;
  for (let i = 0; i < nums.length; i++) {
    if (i > furthest) return false;
    furthest = Math.max(furthest, i + nums[i]);
  }
  return true;
}

function findContentChildren(greed, cookies) {
  const g = [...greed].sort((a, b) => a - b);
  const s = [...cookies].sort((a, b) => a - b);
  let child = 0;
  for (const cookie of s) if (child < g.length && cookie >= g[child]) child++;
  return child;
}

function numRescueBoats(people, limit) {
  const p = [...people].sort((a, b) => a - b);
  let boats = 0;
  for (let lo = 0, hi = p.length - 1; lo <= hi; hi--, boats++) if (p[lo] + p[hi] <= limit) lo++;
  return boats;
}

function twoCitySchedCost(costs) {
  const sorted = [...costs].sort((a, b) => a[0] - a[1] - (b[0] - b[1]));
  const half = sorted.length / 2;
  return sorted.reduce((sum, c, i) => sum + (i < half ? c[0] : c[1]), 0);
}

function greedyCoinCount(coinsDescending, amount) {
  let count = 0;
  for (const coin of coinsDescending) {
    count += Math.floor(amount / coin);
    amount %= coin;
  }
  return amount === 0 ? count : -1;
}

assert.ok(canJump([2, 3, 1, 1, 4]));
assert.equal(canJump([3, 2, 1, 0, 4]), false);
assert.equal(findContentChildren([1, 2, 3], [1, 1]), 1);
assert.equal(findContentChildren([1, 2], [1, 2, 3]), 2);
assert.equal(numRescueBoats([3, 2, 2, 1], 3), 3);
assert.equal(numRescueBoats([3, 5, 3, 4], 5), 4);
assert.equal(twoCitySchedCost([[10, 20], [30, 200], [400, 50], [30, 20]]), 110);
assert.equal(greedyCoinCount([25, 10, 5, 1], 63), 6);
assert.equal(greedyCoinCount([4, 3, 1], 6), 3);
console.log('all checks passed');
