/*
 * Symmetric tree, subtree of another tree, flatten to a linked list, count complete tree nodes O(log^2 n).
 */
const assert = require('node:assert/strict');
const { build } = require('./tree_node');

const mirror = (a, b) => (!a || !b ? a === b : a.val === b.val && mirror(a.left, b.right) && mirror(a.right, b.left));
const isSymmetric = (root) => !root || mirror(root.left, root.right);
const sameTree = (a, b) => (!a || !b ? a === b : a.val === b.val && sameTree(a.left, b.left) && sameTree(a.right, b.right));
const isSubtree = (root, sub) => (!root ? sub === null : sameTree(root, sub) || isSubtree(root.left, sub) || isSubtree(root.right, sub));

function flatten(root) {
  let prev = null;
  const go = (n) => {
    if (!n) return;
    go(n.right);
    go(n.left);
    n.right = prev;
    n.left = null;
    prev = n;
  };
  go(root);
}

function countCompleteNodes(root) {
  if (!root) return 0;
  let leftDepth = 0;
  let rightDepth = 0;
  for (let n = root; n; n = n.left) leftDepth++;
  for (let n = root; n; n = n.right) rightDepth++;
  if (leftDepth === rightDepth) return 2 ** leftDepth - 1;
  return 1 + countCompleteNodes(root.left) + countCompleteNodes(root.right);
}

assert.ok(isSymmetric(build(1, 2, 2, 3, 4, 4, 3)));
assert.equal(isSymmetric(build(1, 2, 2, null, 3, null, 3)), false);
assert.ok(isSubtree(build(3, 4, 5, 1, 2), build(4, 1, 2)));
assert.equal(isSubtree(build(3, 4, 5, 1, 2, null, null, null, null, 0), build(4, 1, 2)), false);
const t = build(1, 2, 5, 3, 4, null, 6);
flatten(t);
const chain = [];
for (let n = t; n; n = n.right) {
  assert.equal(n.left, null);
  chain.push(n.val);
}
assert.deepEqual(chain, [1, 2, 3, 4, 5, 6]);
assert.equal(countCompleteNodes(build(1, 2, 3, 4, 5, 6)), 6);
assert.equal(countCompleteNodes(null), 0);
console.log('all checks passed');
