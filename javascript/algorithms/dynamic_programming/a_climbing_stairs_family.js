/*
 * 1D DP: climbing stairs, min cost stairs, house robber II (circular), tribonacci, delete and earn.
 */
const assert = require('node:assert/strict');

function climbStairs(n) {
  let [a, b] = [1, 1];
  for (let i = 2; i <= n; i++) [a, b] = [b, a + b];
  return b;
}

function minCostClimbingStairs(cost) {
  let [prev2, prev1] = [0, 0];
  for (let i = 2; i <= cost.length; i++) [prev2, prev1] = [prev1, Math.min(prev1 + cost[i - 1], prev2 + cost[i - 2])];
  return prev1;
}

function robLinear(nums) {
  let [prev2, prev1] = [0, 0];
  for (const x of nums) [prev2, prev1] = [prev1, Math.max(prev1, prev2 + x)];
  return prev1;
}

const robCircular = (nums) => (nums.length === 1 ? nums[0] : Math.max(robLinear(nums.slice(0, -1)), robLinear(nums.slice(1))));

function tribonacci(n) {
  let [a, b, c] = [0, 1, 1];
  for (let i = 0; i < n; i++) [a, b, c] = [b, c, a + b + c];
  return a;
}

function deleteAndEarn(nums) {
  const points = new Array(Math.max(...nums) + 1).fill(0);
  for (const x of nums) points[x] += x;
  return robLinear(points);
}

assert.equal(climbStairs(5), 8);
assert.equal(climbStairs(1), 1);
assert.equal(minCostClimbingStairs([10, 15, 20]), 15);
assert.equal(minCostClimbingStairs([1, 100, 1, 1, 1, 100, 1, 1, 100, 1]), 6);
assert.equal(robCircular([2, 3, 2]), 3);
assert.equal(robCircular([1, 2, 3, 1]), 4);
assert.equal(robCircular([1]), 1);
assert.equal(tribonacci(25), 1389537);
assert.equal(deleteAndEarn([3, 4, 2]), 6);
assert.equal(deleteAndEarn([2, 2, 3, 3, 3, 4]), 9);
console.log('all checks passed');
