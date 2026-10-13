/*
 * String DP: edit distance, longest palindromic subsequence / substring, word break,
 * distinct subsequences, interleaving string.
 */
const assert = require('node:assert/strict');

function editDistance(a, b) {
  let prev = Array.from({ length: b.length + 1 }, (_, j) => j);
  for (let i = 1; i <= a.length; i++) {
    const cur = [i];
    for (let j = 1; j <= b.length; j++) {
      cur[j] = a[i - 1] === b[j - 1] ? prev[j - 1] : 1 + Math.min(prev[j - 1], prev[j], cur[j - 1]);
    }
    prev = cur;
  }
  return prev[b.length];
}

function longestPalindromicSubsequence(s) {
  const n = s.length;
  const dp = Array.from({ length: n }, () => new Array(n).fill(0));
  for (let i = n - 1; i >= 0; i--) {
    dp[i][i] = 1;
    for (let j = i + 1; j < n; j++) dp[i][j] = s[i] === s[j] ? 2 + dp[i + 1][j - 1] : Math.max(dp[i + 1][j], dp[i][j - 1]);
  }
  return dp[0][n - 1];
}

function longestPalindromicSubstring(s) {
  let best = '';
  for (let centre = 0; centre < 2 * s.length - 1; centre++) {
    let lo = centre >> 1;
    let hi = lo + (centre % 2);
    while (lo >= 0 && hi < s.length && s[lo] === s[hi]) {
      lo--;
      hi++;
    }
    if (hi - lo - 1 > best.length) best = s.slice(lo + 1, hi);
  }
  return best;
}

function wordBreak(s, words) {
  const dict = new Set(words);
  const maxLen = Math.max(...words.map((w) => w.length));
  const ok = [true];
  for (let i = 1; i <= s.length; i++) {
    ok[i] = false;
    for (let len = 1; len <= Math.min(i, maxLen) && !ok[i]; len++) ok[i] = ok[i - len] && dict.has(s.slice(i - len, i));
  }
  return ok[s.length];
}

function numDistinct(s, t) {
  const dp = [1, ...new Array(t.length).fill(0)];
  for (const ch of s) for (let j = t.length; j >= 1; j--) if (ch === t[j - 1]) dp[j] += dp[j - 1];
  return dp[t.length];
}

function isInterleave(a, b, c) {
  if (a.length + b.length !== c.length) return false;
  const dp = new Array(b.length + 1).fill(false);
  for (let i = 0; i <= a.length; i++) {
    for (let j = 0; j <= b.length; j++) {
      if (i === 0 && j === 0) dp[j] = true;
      else dp[j] = (i > 0 && dp[j] && a[i - 1] === c[i + j - 1]) || (j > 0 && dp[j - 1] && b[j - 1] === c[i + j - 1]);
    }
  }
  return dp[b.length];
}

assert.equal(editDistance('horse', 'ros'), 3);
assert.equal(editDistance('intention', 'execution'), 5);
assert.equal(longestPalindromicSubsequence('bbbab'), 4);
assert.equal(longestPalindromicSubstring('babad'), 'bab');
assert.equal(longestPalindromicSubstring('cbbd'), 'bb');
assert.ok(wordBreak('leetcode', ['leet', 'code']));
assert.equal(wordBreak('catsandog', ['cats', 'dog', 'sand', 'and', 'cat']), false);
assert.equal(numDistinct('rabbbit', 'rabbit'), 3);
assert.ok(isInterleave('aabcc', 'dbbca', 'aadbbcbcac'));
assert.equal(isInterleave('aabcc', 'dbbca', 'aadbbbaccc'), false);
console.log('all checks passed');
