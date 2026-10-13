/*
 * Minimum window substring: expand right until valid, shrink left while valid. O(|s| + |t|).
 */
const assert = require('node:assert/strict');

function minWindow(s, t) {
  const need = new Map();
  for (const ch of t) need.set(ch, (need.get(ch) ?? 0) + 1);
  let missing = t.length;
  let bestStart = 0;
  let bestLen = Infinity;
  let left = 0;
  for (let right = 0; right < s.length; right++) {
    const ch = s[right];
    if ((need.get(ch) ?? 0) > 0) missing--;
    need.set(ch, (need.get(ch) ?? 0) - 1);
    while (missing === 0) {
      if (right - left + 1 < bestLen) {
        bestLen = right - left + 1;
        bestStart = left;
      }
      const out = s[left++];
      need.set(out, need.get(out) + 1);
      if (need.get(out) > 0) missing++;
    }
  }
  return bestLen === Infinity ? '' : s.slice(bestStart, bestStart + bestLen);
}

assert.equal(minWindow('ADOBECODEBANC', 'ABC'), 'BANC');
assert.equal(minWindow('a', 'a'), 'a');
assert.equal(minWindow('a', 'aa'), '');
console.log('all checks passed');
