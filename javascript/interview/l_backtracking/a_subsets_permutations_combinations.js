/*
 * Backtracking template (choose -> recurse -> un-choose): subsets II, permutations II, combination sum.
 */
const assert = require('node:assert/strict');

function subsetsWithDup(nums) {
  const a = [...nums].sort((x, y) => x - y);
  const out = [];
  const go = (start, cur) => {
    out.push([...cur]);
    for (let i = start; i < a.length; i++) {
      if (i > start && a[i] === a[i - 1]) continue;
      cur.push(a[i]);
      go(i + 1, cur);
      cur.pop();
    }
  };
  go(0, []);
  return out;
}

function permuteUnique(nums) {
  const a = [...nums].sort((x, y) => x - y);
  const out = [];
  const used = new Array(a.length).fill(false);
  const go = (cur) => {
    if (cur.length === a.length) return out.push([...cur]);
    for (let i = 0; i < a.length; i++) {
      if (used[i] || (i > 0 && a[i] === a[i - 1] && !used[i - 1])) continue;
      used[i] = true;
      cur.push(a[i]);
      go(cur);
      cur.pop();
      used[i] = false;
    }
  };
  go([]);
  return out;
}

function combinationSum(candidates, target) {
  const a = [...candidates].sort((x, y) => x - y);
  const out = [];
  const go = (start, remaining, cur) => {
    if (remaining === 0) return out.push([...cur]);
    for (let i = start; i < a.length && a[i] <= remaining; i++) {
      cur.push(a[i]);
      go(i, remaining - a[i], cur);
      cur.pop();
    }
  };
  go(0, target, []);
  return out;
}

assert.deepEqual(subsetsWithDup([1, 2, 2]), [[], [1], [1, 2], [1, 2, 2], [2], [2, 2]]);
assert.deepEqual(permuteUnique([1, 1, 2]), [[1, 1, 2], [1, 2, 1], [2, 1, 1]]);
assert.deepEqual(combinationSum([2, 3, 6, 7], 7), [[2, 2, 3], [7]]);
console.log('all checks passed');
