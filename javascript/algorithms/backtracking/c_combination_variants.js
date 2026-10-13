/*
 * Combination sum II, letter case permutation, combinations C(n, k), permutations, subsets by bitmask.
 */
const assert = require('node:assert/strict');

function combinationSum2(candidates, target) {
  const a = [...candidates].sort((x, y) => x - y);
  const out = [];
  const go = (start, remaining, cur) => {
    if (remaining === 0) return out.push([...cur]);
    for (let i = start; i < a.length && a[i] <= remaining; i++) {
      if (i > start && a[i] === a[i - 1]) continue;
      go(i + 1, remaining - a[i], [...cur, a[i]]);
    }
  };
  go(0, target, []);
  return out;
}

function letterCasePermutation(s) {
  let out = [''];
  for (const ch of s) {
    const options = /[a-z]/i.test(ch) ? [ch.toLowerCase(), ch.toUpperCase()] : [ch];
    out = out.flatMap((p) => options.map((o) => p + o));
  }
  return out.sort();
}

function combine(n, k) {
  const out = [];
  const go = (start, cur) => {
    if (cur.length === k) return out.push([...cur]);
    for (let i = start; i <= n - (k - cur.length) + 1; i++) go(i + 1, [...cur, i]);
  };
  go(1, []);
  return out;
}

function permute(nums) {
  const out = [];
  const used = new Array(nums.length).fill(false);
  const go = (cur) => {
    if (cur.length === nums.length) return out.push([...cur]);
    nums.forEach((x, i) => {
      if (used[i]) return;
      used[i] = true;
      go([...cur, x]);
      used[i] = false;
    });
  };
  go([]);
  return out;
}

const subsetsBitmask = (nums) =>
  Array.from({ length: 1 << nums.length }, (_, mask) => nums.filter((_, i) => (mask >> i) & 1));

assert.deepEqual(combinationSum2([10, 1, 2, 7, 6, 1, 5], 8), [[1, 1, 6], [1, 2, 5], [1, 7], [2, 6]]);
assert.deepEqual(letterCasePermutation('a1b2'), ['A1B2', 'A1b2', 'a1B2', 'a1b2']);
assert.deepEqual(combine(4, 2), [[1, 2], [1, 3], [1, 4], [2, 3], [2, 4], [3, 4]]);
assert.deepEqual(permute([1, 2, 3]), [[1, 2, 3], [1, 3, 2], [2, 1, 3], [2, 3, 1], [3, 1, 2], [3, 2, 1]]);
assert.deepEqual(subsetsBitmask([1, 2, 3]), [[], [1], [2], [1, 2], [3], [1, 3], [2, 3], [1, 2, 3]]);
console.log('all checks passed');
