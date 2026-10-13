/*
 * Segment tree with lazy propagation: range add + range sum in O(log n).
 */
const assert = require('node:assert/strict');

class SegmentTreeLazy {
  constructor(values) {
    this.n = values.length;
    this.sum = new Array(4 * this.n).fill(0);
    this.lazy = new Array(4 * this.n).fill(0);
    const build = (node, l, r) => {
      if (l === r) return void (this.sum[node] = values[l]);
      const mid = (l + r) >> 1;
      build(2 * node, l, mid);
      build(2 * node + 1, mid + 1, r);
      this.sum[node] = this.sum[2 * node] + this.sum[2 * node + 1];
    };
    build(1, 0, this.n - 1);
  }

  #apply(node, l, r, delta) {
    this.sum[node] += delta * (r - l + 1);
    this.lazy[node] += delta;
  }

  #push(node, l, r) {
    if (!this.lazy[node]) return;
    const mid = (l + r) >> 1;
    this.#apply(2 * node, l, mid, this.lazy[node]);
    this.#apply(2 * node + 1, mid + 1, r, this.lazy[node]);
    this.lazy[node] = 0;
  }

  rangeAdd(ql, qr, delta, node = 1, l = 0, r = this.n - 1) {
    if (qr < l || r < ql) return;
    if (ql <= l && r <= qr) return this.#apply(node, l, r, delta);
    this.#push(node, l, r);
    const mid = (l + r) >> 1;
    this.rangeAdd(ql, qr, delta, 2 * node, l, mid);
    this.rangeAdd(ql, qr, delta, 2 * node + 1, mid + 1, r);
    this.sum[node] = this.sum[2 * node] + this.sum[2 * node + 1];
  }

  rangeSum(ql, qr, node = 1, l = 0, r = this.n - 1) {
    if (qr < l || r < ql) return 0;
    if (ql <= l && r <= qr) return this.sum[node];
    this.#push(node, l, r);
    const mid = (l + r) >> 1;
    return this.rangeSum(ql, qr, 2 * node, l, mid) + this.rangeSum(ql, qr, 2 * node + 1, mid + 1, r);
  }
}

const st = new SegmentTreeLazy([1, 2, 3, 4, 5]);
assert.equal(st.rangeSum(0, 4), 15);
st.rangeAdd(1, 3, 10);
assert.equal(st.rangeSum(0, 4), 45);
assert.equal(st.rangeSum(2, 2), 13);
assert.equal(st.rangeSum(3, 4), 19);
console.log('all checks passed');
