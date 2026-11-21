/*
 * The three sliding-window templates (full explanations in the Java version).
 *   A) longest valid:  grow right; while invalid shrink left; record after.
 *   B) shortest valid: grow right; while valid record, then shrink left.
 *   C) fixed size k:   add right, record once right >= k-1, remove right-k+1.
 *   exactly(K) = atMost(K) - atMost(K-1).
 */
const assert = require('node:assert/strict');

function lengthOfLongestSubstring(s) {
  const last = new Map();
  let best = 0;
  for (let left = 0, right = 0; right < s.length; right++) {
    const c = s[right];
    if (last.has(c)) left = Math.max(left, last.get(c) + 1);
    last.set(c, right);
    best = Math.max(best, right - left + 1);
  }
  return best;
}

function findAnagrams(s, p) {
  const k = p.length;
  const out = [];
  const need = new Array(26).fill(0);
  const window = new Array(26).fill(0);
  const idx = (c) => c.charCodeAt(0) - 97;
  for (const c of p) need[idx(c)]++;
  for (let right = 0; right < s.length; right++) {
    window[idx(s[right])]++;
    if (right >= k) window[idx(s[right - k])]--;
    if (right >= k - 1 && need.every((v, i) => v === window[i])) out.push(right - k + 1);
  }
  return out;
}

function longestOnes(nums, k) {
  let best = 0;
  let zeros = 0;
  for (let left = 0, right = 0; right < nums.length; right++) {
    if (nums[right] === 0) zeros++;
    while (zeros > k) if (nums[left++] === 0) zeros--;
    best = Math.max(best, right - left + 1);
  }
  return best;
}

function atMostKDistinct(nums, k) {
  const count = new Map();
  let total = 0;
  for (let left = 0, right = 0; right < nums.length; right++) {
    count.set(nums[right], (count.get(nums[right]) ?? 0) + 1);
    while (count.size > k) {
      const c = count.get(nums[left]) - 1;
      if (c === 0) count.delete(nums[left]);
      else count.set(nums[left], c);
      left++;
    }
    total += right - left + 1;
  }
  return total;
}

const subarraysWithKDistinct = (nums, k) => atMostKDistinct(nums, k) - atMostKDistinct(nums, k - 1);

function minSubArrayLen(target, nums) {
  let best = Infinity;
  let sum = 0;
  for (let left = 0, right = 0; right < nums.length; right++) {
    sum += nums[right];
    while (sum >= target) {
      best = Math.min(best, right - left + 1);
      sum -= nums[left++];
    }
  }
  return best === Infinity ? 0 : best;
}

assert.equal(lengthOfLongestSubstring('abcabcbb'), 3);
assert.equal(lengthOfLongestSubstring('bbbbb'), 1);
assert.equal(lengthOfLongestSubstring('pwwkew'), 3);
assert.equal(lengthOfLongestSubstring('abba'), 2);
assert.equal(lengthOfLongestSubstring(''), 0);

assert.deepEqual(findAnagrams('cbaebabacd', 'abc'), [0, 6]);
assert.deepEqual(findAnagrams('abab', 'ab'), [0, 1, 2]);

assert.equal(longestOnes([1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0], 2), 6);
assert.equal(longestOnes([0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1], 3), 10);

assert.equal(subarraysWithKDistinct([1, 2, 1, 2, 3], 2), 7);
assert.equal(subarraysWithKDistinct([1, 2, 1, 3, 4], 3), 3);

assert.equal(minSubArrayLen(7, [2, 3, 1, 2, 4, 3]), 2);
assert.equal(minSubArrayLen(4, [1, 4, 4]), 1);
assert.equal(minSubArrayLen(11, [1, 1, 1, 1, 1, 1, 1, 1]), 0);
console.log('all checks passed');
