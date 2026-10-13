/*
 * Counting DP: unique BSTs (Catalan), palindromic substrings, dice rolls to a target (mod 1e9+7).
 */
const assert = require('node:assert/strict');

const MOD = 1_000_000_007;

function numTrees(n) {
  const g = [1, ...new Array(n).fill(0)];
  for (let nodes = 1; nodes <= n; nodes++) for (let root = 1; root <= nodes; root++) g[nodes] += g[root - 1] * g[nodes - root];
  return g[n];
}

function countPalindromicSubstrings(s) {
  let count = 0;
  for (let centre = 0; centre < 2 * s.length - 1; centre++) {
    for (let lo = centre >> 1, hi = lo + (centre % 2); lo >= 0 && hi < s.length && s[lo] === s[hi]; lo--, hi++) count++;
  }
  return count;
}

function numRollsToTarget(dice, faces, target) {
  let ways = [1, ...new Array(target).fill(0)];
  for (let d = 0; d < dice; d++) {
    const next = new Array(target + 1).fill(0);
    for (let t = 1; t <= target; t++) for (let f = 1; f <= faces && f <= t; f++) next[t] = (next[t] + ways[t - f]) % MOD;
    ways = next;
  }
  return ways[target];
}

assert.equal(numTrees(3), 5);
assert.equal(numTrees(5), 42);
assert.equal(countPalindromicSubstrings('aaa'), 6);
assert.equal(countPalindromicSubstrings('abc'), 3);
assert.equal(numRollsToTarget(2, 6, 7), 6);
assert.equal(numRollsToTarget(30, 30, 500), 222616187);
console.log('all checks passed');
