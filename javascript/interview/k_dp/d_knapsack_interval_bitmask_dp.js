/*
 * 0/1 knapsack (partition), interval DP (burst balloons), bitmask DP (TSP).
 */
const assert = require('node:assert/strict');

function canPartition(nums) {
  const total = nums.reduce((a, b) => a + b, 0);
  if (total % 2) return false;
  const target = total / 2;
  const dp = [true, ...new Array(target).fill(false)];
  for (const x of nums) for (let c = target; c >= x; c--) dp[c] = dp[c] || dp[c - x]; // downwards: use once
  return dp[target];
}

function maxCoins(nums) {
  const v = [1, ...nums, 1];
  const n = v.length;
  const dp = Array.from({ length: n }, () => new Array(n).fill(0));
  for (let len = 2; len < n; len++) {
    for (let l = 0; l + len < n; l++) {
      const r = l + len;
      for (let k = l + 1; k < r; k++) dp[l][r] = Math.max(dp[l][r], dp[l][k] + dp[k][r] + v[l] * v[k] * v[r]);
    }
  }
  return dp[0][n - 1];
}

function tsp(dist) {
  const n = dist.length;
  const full = 1 << n;
  const dp = Array.from({ length: full }, () => new Array(n).fill(Infinity));
  dp[1][0] = 0;
  for (let mask = 1; mask < full; mask++) {
    for (let last = 0; last < n; last++) {
      if (dp[mask][last] === Infinity || !(mask & (1 << last))) continue;
      for (let next = 0; next < n; next++) {
        if (mask & (1 << next)) continue;
        const nm = mask | (1 << next);
        dp[nm][next] = Math.min(dp[nm][next], dp[mask][last] + dist[last][next]);
      }
    }
  }
  let best = Infinity;
  for (let last = 1; last < n; last++) best = Math.min(best, dp[full - 1][last] + dist[last][0]);
  return best;
}

assert.ok(canPartition([1, 5, 11, 5]));
assert.equal(canPartition([1, 2, 3, 5]), false);
assert.equal(maxCoins([3, 1, 5, 8]), 167);
assert.equal(tsp([[0, 10, 15, 20], [10, 0, 35, 25], [15, 35, 0, 30], [20, 25, 30, 0]]), 80);
console.log('all checks passed');
