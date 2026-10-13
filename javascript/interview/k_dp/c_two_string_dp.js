/*
 * Two-string DP: LCS (rolling rows) and regular expression matching with '.' and '*'.
 */
const assert = require('node:assert/strict');

function lcs(s, t) {
  let prev = new Array(t.length + 1).fill(0);
  for (let i = 1; i <= s.length; i++) {
    const cur = new Array(t.length + 1).fill(0);
    for (let j = 1; j <= t.length; j++) {
      cur[j] = s[i - 1] === t[j - 1] ? prev[j - 1] + 1 : Math.max(prev[j], cur[j - 1]);
    }
    prev = cur;
  }
  return prev[t.length];
}

function isMatch(s, p) {
  const m = s.length;
  const n = p.length;
  const dp = Array.from({ length: m + 1 }, () => new Array(n + 1).fill(false));
  dp[0][0] = true;
  const matches = (sc, pc) => pc === '.' || pc === sc;
  for (let i = 0; i <= m; i++) {
    for (let j = 1; j <= n; j++) {
      if (p[j - 1] === '*') dp[i][j] = dp[i][j - 2] || (i > 0 && matches(s[i - 1], p[j - 2]) && dp[i - 1][j]);
      else dp[i][j] = i > 0 && matches(s[i - 1], p[j - 1]) && dp[i - 1][j - 1];
    }
  }
  return dp[m][n];
}

assert.equal(lcs('abcde', 'ace'), 3);
assert.equal(lcs('abc', 'def'), 0);
assert.ok(isMatch('aa', 'a*'));
assert.ok(isMatch('ab', '.*'));
assert.ok(isMatch('aab', 'c*a*b'));
assert.equal(isMatch('mississippi', 'mis*is*p*.'), false);
console.log('all checks passed');
