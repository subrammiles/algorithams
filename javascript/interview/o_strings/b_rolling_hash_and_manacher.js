/*
 * Rabin-Karp, longest duplicate substring and Manacher (full explanations in the Java version).
 *   rabinKarp            rolling hash; slide in O(1); VERIFY on a hash match (collisions).
 *   longestDupSubstring  binary search on length + rolling-hash check, O(n log n).
 *   longestPalindrome    Manacher, O(n).
 * Numbers: hash < 1e9+7 and BASE = 131, so hash * BASE < 2^53 - plain Numbers stay exact.
 */
const assert = require('node:assert/strict');

const MOD = 1_000_000_007;
const BASE = 131;
const mod = (x) => ((x % MOD) + MOD) % MOD;

function powMod(b, e) {
  let r = 1;
  for (let i = 0; i < e; i++) r = (r * b) % MOD;
  return r;
}

function rabinKarp(text, pattern) {
  const n = text.length;
  const m = pattern.length;
  if (m === 0 || m > n) return [];
  const power = powMod(BASE, m - 1);
  let target = 0;
  let window = 0;
  for (let i = 0; i < m; i++) {
    target = (target * BASE + pattern.charCodeAt(i)) % MOD;
    window = (window * BASE + text.charCodeAt(i)) % MOD;
  }
  const out = [];
  for (let i = 0; i + m <= n; i++) {
    if (window === target && text.startsWith(pattern, i)) out.push(i);
    if (i + m < n) {
      window = mod(window - ((text.charCodeAt(i) * power) % MOD));
      window = (window * BASE + text.charCodeAt(i + m)) % MOD;
    }
  }
  return out;
}

function duplicateOfLength(s, len) {
  const power = powMod(BASE, len - 1);
  const seen = new Map();
  let h = 0;
  for (let i = 0; i < s.length; i++) {
    if (i >= len) h = mod(h - ((s.charCodeAt(i - len) * power) % MOD));
    h = (h * BASE + s.charCodeAt(i)) % MOD;
    if (i < len - 1) continue;
    const start = i - len + 1;
    const starts = seen.get(h) ?? [];
    for (const other of starts) if (s.substr(other, len) === s.substr(start, len)) return start;
    starts.push(start);
    seen.set(h, starts);
  }
  return -1;
}

function longestDupSubstring(s) {
  let lo = 1;
  let hi = s.length - 1;
  let best = '';
  while (lo <= hi) {
    const mid = (lo + hi) >> 1;
    const start = duplicateOfLength(s, mid);
    if (start >= 0) {
      best = s.substr(start, mid);
      lo = mid + 1;
    } else {
      hi = mid - 1;
    }
  }
  return best;
}

function longestPalindrome(s) {
  if (!s) return s;
  const t = `#${s.split('').join('#')}#`;
  const n = t.length;
  const p = new Array(n).fill(0);
  let center = 0;
  let right = 0;
  let best = 0;
  for (let i = 0; i < n; i++) {
    if (i < right) p[i] = Math.min(right - i, p[2 * center - i]);
    while (i - p[i] - 1 >= 0 && i + p[i] + 1 < n && t[i - p[i] - 1] === t[i + p[i] + 1]) p[i]++;
    if (i + p[i] > right) {
      center = i;
      right = i + p[i];
    }
    if (p[i] > p[best]) best = i;
  }
  const start = (best - p[best]) / 2;
  return s.substr(start, p[best]);
}

assert.deepEqual(rabinKarp('abracadabra', 'abra'), [0, 7]);
assert.deepEqual(rabinKarp('aaaa', 'aa'), [0, 1, 2]);
assert.deepEqual(rabinKarp('abc', 'abcd'), []);

assert.equal(longestDupSubstring('banana'), 'ana');
assert.equal(longestDupSubstring('abcd'), '');
assert.equal(longestDupSubstring('aaaaa'), 'aaaa');

assert.equal(longestPalindrome('babad').length, 3);
assert.equal(longestPalindrome('cbbd'), 'bb');
assert.equal(longestPalindrome('forgeeksskeegfor'), 'geeksskeeg');
assert.equal(longestPalindrome('a'), 'a');
console.log('all checks passed');
