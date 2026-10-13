/*
 * Longest repeating character replacement: valid while length - maxFreq <= k. O(n).
 */
const assert = require('node:assert/strict');

function characterReplacement(s, k) {
  const count = new Array(26).fill(0);
  let left = 0;
  let maxFreq = 0;
  let best = 0;
  for (let right = 0; right < s.length; right++) {
    maxFreq = Math.max(maxFreq, ++count[s.charCodeAt(right) - 65]);
    while (right - left + 1 - maxFreq > k) count[s.charCodeAt(left++) - 65]--;
    best = Math.max(best, right - left + 1);
  }
  return best;
}

assert.equal(characterReplacement('ABAB', 2), 4);
assert.equal(characterReplacement('AABABBA', 1), 4);
console.log('all checks passed');
