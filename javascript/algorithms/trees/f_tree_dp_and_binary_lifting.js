/*
 * House robber III, binary tree cameras, binary lifting for k-th ancestor and LCA.
 */
const assert = require('node:assert/strict');
const { build } = require('./tree_node');

function rob(root) {
  const pair = (n) => {
    if (!n) return [0, 0]; // [robbed, notRobbed]
    const l = pair(n.left);
    const r = pair(n.right);
    return [n.val + l[1] + r[1], Math.max(...l) + Math.max(...r)];
  };
  return Math.max(...pair(root));
}

function minCameraCover(root) {
  let cameras = 0;
  const cover = (n) => {
    if (!n) return 2; // 0 not covered, 1 camera, 2 covered
    const l = cover(n.left);
    const r = cover(n.right);
    if (l === 0 || r === 0) {
      cameras++;
      return 1;
    }
    return l === 1 || r === 1 ? 2 : 0;
  };
  if (cover(root) === 0) cameras++;
  return cameras;
}

class BinaryLifting {
  constructor(parent) {
    const n = parent.length;
    this.log = Math.max(1, Math.ceil(Math.log2(n)));
    this.up = [[...parent]];
    this.depth = new Array(n).fill(0);
    const children = Array.from({ length: n }, () => []);
    parent.forEach((p, v) => p !== -1 && children[p].push(v));
    const stack = [parent.indexOf(-1)];
    while (stack.length) {
      const v = stack.pop();
      for (const c of children[v]) {
        this.depth[c] = this.depth[v] + 1;
        stack.push(c);
      }
    }
    for (let j = 1; j <= this.log; j++) {
      const prev = this.up[j - 1];
      this.up.push(prev.map((p) => (p === -1 ? -1 : prev[p])));
    }
  }

  kthAncestor(v, k) {
    if (k >= 2 ** (this.log + 1)) return -1;
    for (let j = 0; j <= this.log && v !== -1; j++) if ((k >> j) & 1) v = this.up[j][v];
    return v;
  }

  lca(a, b) {
    if (this.depth[a] < this.depth[b]) [a, b] = [b, a];
    a = this.kthAncestor(a, this.depth[a] - this.depth[b]);
    if (a === b) return a;
    for (let j = this.log; j >= 0; j--) {
      if (this.up[j][a] !== this.up[j][b]) {
        a = this.up[j][a];
        b = this.up[j][b];
      }
    }
    return this.up[0][a];
  }
}

assert.equal(rob(build(3, 2, 3, null, 3, null, 1)), 7);
assert.equal(rob(build(3, 4, 5, 1, 3, null, 1)), 9);
assert.equal(minCameraCover(build(0, 0, null, 0, 0)), 1);
assert.equal(minCameraCover(build(0, 0, null, 0, null, 0, null, null, 0)), 2);
const bl = new BinaryLifting([-1, 0, 0, 1, 1, 2, 2]);
assert.equal(bl.kthAncestor(3, 1), 1);
assert.equal(bl.kthAncestor(5, 2), 0);
assert.equal(bl.kthAncestor(6, 3), -1);
assert.equal(bl.lca(3, 4), 1);
assert.equal(bl.lca(3, 5), 0);
assert.equal(bl.lca(5, 2), 2);
assert.equal(bl.lca(4, 4), 4);
console.log('all checks passed');
