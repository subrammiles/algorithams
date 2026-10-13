/*
 * Fenwick tree and Count of Smaller Numbers After Self (coordinate compression). O(n log n).
 */
const assert = require('node:assert/strict');

class Fenwick {
  constructor(n) {
    this.tree = new Array(n + 1).fill(0);
  }

  add(i, delta) {
    for (; i < this.tree.length; i += i & -i) this.tree[i] += delta;
  }

  prefixSum(i) {
    let s = 0;
    for (; i > 0; i -= i & -i) s += this.tree[i];
    return s;
  }

  rangeSum(l, r) {
    return this.prefixSum(r) - this.prefixSum(l - 1);
  }
}

function countSmaller(nums) {
  const rank = new Map([...new Set(nums)].sort((a, b) => a - b).map((v, i) => [v, i + 1]));
  const bit = new Fenwick(rank.size);
  const result = new Array(nums.length);
  for (let i = nums.length - 1; i >= 0; i--) {
    result[i] = bit.prefixSum(rank.get(nums[i]) - 1);
    bit.add(rank.get(nums[i]), 1);
  }
  return result;
}

const f = new Fenwick(5);
[1, 3, 5, 7, 9].forEach((v, i) => f.add(i + 1, v));
assert.equal(f.rangeSum(2, 4), 15);
f.add(3, 10);
assert.equal(f.rangeSum(2, 4), 25);
assert.deepEqual(countSmaller([5, 2, 6, 1]), [2, 1, 1, 0]);
assert.deepEqual(countSmaller([-1, -1]), [0, 0]);
console.log('all checks passed');
