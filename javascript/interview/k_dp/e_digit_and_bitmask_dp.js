/*
 * Digit DP, longest valid parentheses, k-equal-subsets bitmask DP (full explanations in Java).
 *   countSpecialNumbers      digit DP over (pos, mask, tight, started); memo for !tight && started.
 *   longestValidParentheses  stack of indices with the last unmatched ')' as the base.
 *   canPartitionKSubsets     dp[mask] = fill level of the current bucket, -1 if unreachable.
 */
const assert = require('node:assert/strict');

function countSpecialNumbers(n) {
  const digits = String(n).split('').map(Number);
  const memo = new Map();
  const go = (pos, mask, tight, started) => {
    if (pos === digits.length) return started ? 1 : 0;
    const key = pos * 1024 + mask;
    if (!tight && started && memo.has(key)) return memo.get(key);
    const limit = tight ? digits[pos] : 9;
    let total = 0;
    for (let d = 0; d <= limit; d++) {
      const nextTight = tight && d === limit;
      if (!started && d === 0) total += go(pos + 1, mask, nextTight, false);
      else if (!(mask & (1 << d))) total += go(pos + 1, mask | (1 << d), nextTight, true);
    }
    if (!tight && started) memo.set(key, total);
    return total;
  };
  return go(0, 0, true, false);
}

function longestValidParentheses(s) {
  const stack = [-1];
  let best = 0;
  for (let i = 0; i < s.length; i++) {
    if (s[i] === '(') {
      stack.push(i);
    } else {
      stack.pop();
      if (!stack.length) stack.push(i);
      else best = Math.max(best, i - stack.at(-1));
    }
  }
  return best;
}

function canPartitionKSubsets(nums, k) {
  const total = nums.reduce((a, b) => a + b, 0);
  if (total % k) return false;
  const target = total / k;
  const n = nums.length;
  const dp = new Array(1 << n).fill(-1);
  dp[0] = 0;
  for (let mask = 0; mask < 1 << n; mask++) {
    if (dp[mask] < 0) continue;
    for (let i = 0; i < n; i++) {
      const next = mask | (1 << i);
      if (next !== mask && dp[next] < 0 && dp[mask] + nums[i] <= target) {
        dp[next] = (dp[mask] + nums[i]) % target;
      }
    }
  }
  return dp[(1 << n) - 1] === 0;
}

assert.equal(countSpecialNumbers(20), 19);
assert.equal(countSpecialNumbers(5), 5);
assert.equal(countSpecialNumbers(135), 110);

assert.equal(longestValidParentheses('(()'), 2);
assert.equal(longestValidParentheses(')()())'), 4);
assert.equal(longestValidParentheses('()(())'), 6);
assert.equal(longestValidParentheses(''), 0);

assert.equal(canPartitionKSubsets([4, 3, 2, 3, 5, 2, 1], 4), true);
assert.equal(canPartitionKSubsets([1, 2, 3, 4], 3), false);
console.log('all checks passed');
