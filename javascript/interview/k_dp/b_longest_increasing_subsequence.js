/*
 * LIS in O(n^2) and O(n log n) (patience sorting); Russian doll envelopes.
 */
const assert = require('node:assert/strict');

function lisQuadratic(a) {
  const dp = new Array(a.length).fill(1);
  for (let i = 0; i < a.length; i++) for (let j = 0; j < i; j++) if (a[j] < a[i]) dp[i] = Math.max(dp[i], dp[j] + 1);
  return Math.max(0, ...dp);
}

function lisFast(a) {
  const tails = [];
  for (const x of a) {
    let lo = 0;
    let hi = tails.length;
    while (lo < hi) {
      const mid = (lo + hi) >> 1;
      if (tails[mid] < x) lo = mid + 1;
      else hi = mid;
    }
    tails[lo] = x;
  }
  return tails.length;
}

const maxEnvelopes = (envelopes) =>
  lisFast([...envelopes].sort((x, y) => x[0] - y[0] || y[1] - x[1]).map((e) => e[1]));

const a = [10, 9, 2, 5, 3, 7, 101, 18];
assert.equal(lisQuadratic(a), 4);
assert.equal(lisFast(a), 4);
assert.equal(lisFast([7, 7, 7]), 1);
assert.equal(maxEnvelopes([[5, 4], [6, 4], [6, 7], [2, 3]]), 3);
console.log('all checks passed');
