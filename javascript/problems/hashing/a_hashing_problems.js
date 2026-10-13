/*
 * Two sum, group anagrams, longest consecutive sequence O(n), nearby duplicate, 4Sum II.
 */
const assert = require('node:assert/strict');

function twoSum(nums, target) {
  const seen = new Map();
  for (let i = 0; i < nums.length; i++) {
    if (seen.has(target - nums[i])) return [seen.get(target - nums[i]), i];
    seen.set(nums[i], i);
  }
  return [];
}

function groupAnagrams(words) {
  const groups = new Map();
  for (const w of words) {
    const key = [...w].sort().join('');
    if (!groups.has(key)) groups.set(key, []);
    groups.get(key).push(w);
  }
  return [...groups.values()].map((g) => g.sort()).sort((a, b) => (a[0] < b[0] ? -1 : 1));
}

function longestConsecutive(nums) {
  const set = new Set(nums);
  let best = 0;
  for (const x of set) {
    if (set.has(x - 1)) continue;
    let len = 1;
    while (set.has(x + len)) len++;
    best = Math.max(best, len);
  }
  return best;
}

function containsNearbyDuplicate(nums, k) {
  const last = new Map();
  for (let i = 0; i < nums.length; i++) {
    if (last.has(nums[i]) && i - last.get(nums[i]) <= k) return true;
    last.set(nums[i], i);
  }
  return false;
}

function fourSumCount(a, b, c, d) {
  const sums = new Map();
  for (const x of a) for (const y of b) sums.set(x + y, (sums.get(x + y) ?? 0) + 1);
  let count = 0;
  for (const x of c) for (const y of d) count += sums.get(-(x + y)) ?? 0;
  return count;
}

assert.deepEqual(twoSum([2, 7, 11, 15], 9), [0, 1]);
assert.deepEqual(groupAnagrams(['eat', 'tea', 'tan', 'ate', 'nat', 'bat']), [['ate', 'eat', 'tea'], ['bat'], ['nat', 'tan']]);
assert.equal(longestConsecutive([100, 4, 200, 1, 3, 2]), 4);
assert.equal(longestConsecutive([0, 3, 7, 2, 5, 8, 4, 6, 0, 1]), 9);
assert.ok(containsNearbyDuplicate([1, 2, 3, 1], 3));
assert.equal(containsNearbyDuplicate([1, 2, 3, 1, 2, 3], 2), false);
assert.equal(fourSumCount([1, 2], [-2, -1], [-1, 2], [0, 2]), 2);
console.log('all checks passed');
