/*
 * Zigzag level order, right side view, vertical order (col, row, value), boundary traversal.
 */
const assert = require('node:assert/strict');
const { build } = require('./tree_node');

function zigzagLevelOrder(root) {
  const levels = [];
  let level = root ? [root] : [];
  while (level.length) {
    const values = level.map((n) => n.val);
    levels.push(levels.length % 2 ? values.reverse() : values);
    level = level.flatMap((n) => [n.left, n.right].filter(Boolean));
  }
  return levels;
}

function rightSideView(root) {
  const view = [];
  const go = (n, depth) => {
    if (!n) return;
    if (depth === view.length) view.push(n.val);
    go(n.right, depth + 1);
    go(n.left, depth + 1);
  };
  go(root, 0);
  return view;
}

function verticalOrder(root) {
  const cells = new Map();
  const go = (n, row, col) => {
    if (!n) return;
    if (!cells.has(col)) cells.set(col, []);
    cells.get(col).push([row, n.val]);
    go(n.left, row + 1, col - 1);
    go(n.right, row + 1, col + 1);
  };
  go(root, 0, 0);
  return [...cells.keys()]
    .sort((a, b) => a - b)
    .map((col) => cells.get(col).sort((a, b) => a[0] - b[0] || a[1] - b[1]).map(([, v]) => v));
}

function boundary(root) {
  if (!root) return [];
  const isLeaf = (n) => !n.left && !n.right;
  const out = isLeaf(root) ? [] : [root.val];
  for (let n = root.left; n; n = n.left ?? n.right) if (!isLeaf(n)) out.push(n.val);
  const leaves = (n) => {
    if (!n) return;
    if (isLeaf(n)) out.push(n.val);
    leaves(n.left);
    leaves(n.right);
  };
  leaves(root);
  const right = [];
  for (let n = root.right; n; n = n.right ?? n.left) if (!isLeaf(n)) right.push(n.val);
  return [...out, ...right.reverse()];
}

const t = build(3, 9, 20, null, null, 15, 7);
assert.deepEqual(zigzagLevelOrder(t), [[3], [20, 9], [15, 7]]);
assert.deepEqual(rightSideView(build(1, 2, 3, null, 5, null, 4)), [1, 3, 4]);
assert.deepEqual(verticalOrder(t), [[9], [3, 15], [20], [7]]);
assert.deepEqual(verticalOrder(build(1, 2, 3, 4, 5, 6, 7)), [[4], [2], [1, 5, 6], [3], [7]]);
assert.deepEqual(boundary(build(1, 2, 3, 4, 5, 6, null, null, null, 7, 8, 9, 10)), [1, 2, 4, 7, 8, 9, 10, 6, 3]);
console.log('all checks passed');
