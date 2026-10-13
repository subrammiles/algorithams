/*
 * LCA in a binary tree (O(n)) and in a BST (O(h)).
 */
const assert = require('node:assert/strict');
const { build, find } = require('./tree_node');

function lca(root, p, q) {
  if (!root || root === p || root === q) return root;
  const left = lca(root.left, p, q);
  const right = lca(root.right, p, q);
  if (left && right) return root;
  return left ?? right;
}

function lcaBst(root, p, q) {
  while (root) {
    if (p < root.val && q < root.val) root = root.left;
    else if (p > root.val && q > root.val) root = root.right;
    else return root;
  }
  return null;
}

const root = build(3, 5, 1, 6, 2, 0, 8, null, null, 7, 4);
assert.equal(lca(root, find(root, 5), find(root, 1)).val, 3);
assert.equal(lca(root, find(root, 5), find(root, 4)).val, 5);
const bst = build(6, 2, 8, 0, 4, 7, 9, null, null, 3, 5);
assert.equal(lcaBst(bst, 2, 8).val, 6);
assert.equal(lcaBst(bst, 3, 5).val, 4);
console.log('all checks passed');
