/*
 * 1D DP: house robber, coin change (min coins / number of ways), decode ways.
 */
const assert = require('node:assert/strict');

function rob(nums) {
  let [prev2, prev1] = [0, 0];
  for (const x of nums) [prev2, prev1] = [prev1, Math.max(prev1, prev2 + x)];
  return prev1;
}

function coinChangeMin(coins, amount) {
  const dp = [0, ...new Array(amount).fill(amount + 1)];
  for (let a = 1; a <= amount; a++) for (const c of coins) if (c <= a) dp[a] = Math.min(dp[a], dp[a - c] + 1);
  return dp[amount] > amount ? -1 : dp[amount];
}

function coinChangeWays(coins, amount) {
  const dp = [1, ...new Array(amount).fill(0)];
  for (const c of coins) for (let a = c; a <= amount; a++) dp[a] += dp[a - c]; // coins outer: combinations
  return dp[amount];
}

function numDecodings(s) {
  let [prev2, prev1] = [1, s[0] === '0' ? 0 : 1];
  for (let i = 1; i < s.length; i++) {
    let cur = s[i] !== '0' ? prev1 : 0;
    const two = Number(s.slice(i - 1, i + 1));
    if (two >= 10 && two <= 26) cur += prev2;
    [prev2, prev1] = [prev1, cur];
  }
  return prev1;
}

assert.equal(rob([2, 7, 9, 3, 1]), 12);
assert.equal(coinChangeMin([1, 3, 4], 6), 2);
assert.equal(coinChangeMin([2], 3), -1);
assert.equal(coinChangeWays([1, 2, 5], 5), 4);
assert.equal(numDecodings('226'), 3);
assert.equal(numDecodings('06'), 0);
console.log('all checks passed');
