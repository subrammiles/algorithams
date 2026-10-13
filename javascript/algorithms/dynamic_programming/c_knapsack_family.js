/*
 * Knapsack family: 0/1 (capacity DOWN), unbounded (capacity UP), rod cutting, target sum,
 * minimum subset sum difference.
 */
const assert = require('node:assert/strict');

function knapsack01Table(wt, val, capacity) {
  const dp = Array.from({ length: wt.length + 1 }, () => new Array(capacity + 1).fill(0));
  for (let i = 1; i <= wt.length; i++) {
    for (let w = 0; w <= capacity; w++) {
      dp[i][w] = dp[i - 1][w];
      if (wt[i - 1] <= w) dp[i][w] = Math.max(dp[i][w], dp[i - 1][w - wt[i - 1]] + val[i - 1]);
    }
  }
  return dp[wt.length][capacity];
}

function knapsack01(wt, val, capacity) {
  const dp = new Array(capacity + 1).fill(0);
  wt.forEach((w0, i) => {
    for (let w = capacity; w >= w0; w--) dp[w] = Math.max(dp[w], dp[w - w0] + val[i]);
  });
  return dp[capacity];
}

function unboundedKnapsack(wt, val, capacity) {
  const dp = new Array(capacity + 1).fill(0);
  wt.forEach((w0, i) => {
    for (let w = w0; w <= capacity; w++) dp[w] = Math.max(dp[w], dp[w - w0] + val[i]);
  });
  return dp[capacity];
}

function rodCutting(prices, length) {
  const dp = new Array(length + 1).fill(0);
  for (let n = 1; n <= length; n++) for (let piece = 1; piece <= n; piece++) dp[n] = Math.max(dp[n], prices[piece - 1] + dp[n - piece]);
  return dp[length];
}

function targetSumWays(nums, target) {
  const total = nums.reduce((a, b) => a + b, 0);
  if (Math.abs(target) > total || (total + target) % 2) return 0;
  const goal = (total + target) / 2;
  const ways = [1, ...new Array(goal).fill(0)];
  for (const x of nums) for (let s = goal; s >= x; s--) ways[s] += ways[s - x];
  return ways[goal];
}

function minSubsetSumDifference(nums) {
  const total = nums.reduce((a, b) => a + b, 0);
  const half = Math.floor(total / 2);
  const reachable = [true, ...new Array(half).fill(false)];
  for (const x of nums) for (let s = half; s >= x; s--) reachable[s] ||= reachable[s - x];
  return total - 2 * reachable.lastIndexOf(true);
}

const wt = [1, 3, 4, 5];
const val = [1, 4, 5, 7];
assert.equal(knapsack01Table(wt, val, 7), 9);
assert.equal(knapsack01(wt, val, 7), 9);
assert.equal(unboundedKnapsack([1, 3, 4], [15, 50, 60], 8), 130);
assert.equal(rodCutting([1, 5, 8, 9, 10, 17, 17, 20], 8), 22);
assert.equal(targetSumWays([1, 1, 1, 1, 1], 3), 5);
assert.equal(targetSumWays([1], 2), 0);
assert.equal(minSubsetSumDifference([1, 6, 11, 5]), 1);
console.log('all checks passed');
