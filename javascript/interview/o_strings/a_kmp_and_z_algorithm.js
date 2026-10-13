/*
 * KMP (lps table) and Z-algorithm pattern search, both O(n + m).
 */
const assert = require('node:assert/strict');

function lps(p) {
  const table = new Array(p.length).fill(0);
  for (let i = 1, len = 0; i < p.length; ) {
    if (p[i] === p[len]) table[i++] = ++len;
    else if (len) len = table[len - 1];
    else i++;
  }
  return table;
}

function kmpSearch(text, p) {
  const table = lps(p);
  const matches = [];
  for (let i = 0, j = 0; i < text.length; i++) {
    while (j && text[i] !== p[j]) j = table[j - 1];
    if (text[i] === p[j] && ++j === p.length) {
      matches.push(i - j + 1);
      j = table[j - 1];
    }
  }
  return matches;
}

function zArray(s) {
  const z = new Array(s.length).fill(0);
  for (let i = 1, l = 0, r = 0; i < s.length; i++) {
    if (i < r) z[i] = Math.min(r - i, z[i - l]);
    while (i + z[i] < s.length && s[z[i]] === s[i + z[i]]) z[i]++;
    if (i + z[i] > r) [l, r] = [i, i + z[i]];
  }
  return z;
}

function zSearch(text, p) {
  const z = zArray(`${p}$${text}`);
  const out = [];
  for (let i = p.length + 1; i < z.length; i++) if (z[i] === p.length) out.push(i - p.length - 1);
  return out;
}

function repeatedSubstringPattern(s) {
  const longest = lps(s)[s.length - 1];
  return longest > 0 && s.length % (s.length - longest) === 0;
}

assert.deepEqual(lps('aabaaab'), [0, 1, 0, 1, 2, 2, 3]);
assert.deepEqual(kmpSearch('ababcabcabababd', 'ababd'), [10]);
assert.deepEqual(kmpSearch('aaaa', 'aa'), [0, 1, 2]);
assert.deepEqual(zSearch('ababcabcabababd', 'ababd'), [10]);
assert.ok(repeatedSubstringPattern('abcabcabc'));
assert.equal(repeatedSubstringPattern('aba'), false);
console.log('all checks passed');
