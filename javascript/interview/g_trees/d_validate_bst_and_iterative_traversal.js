/*
 * Validate BST with bounds; iterative and Morris (O(1) space) inorder traversal.
 */
const assert = require('node:assert/strict');
const { build } = require('./tree_node');

function isValidBST(n, low = -Infinity, high = Infinity) {
  if (!n) return true;
  if (n.val <= low || n.val >= high) return false;
  return isValidBST(n.left, low, n.val) && isValidBST(n.right, n.val, high);
}

function inorderIterative(root) {
  const out = [];
  const stack = [];
  let cur = root;
  while (cur || stack.length) {
    while (cur) {
      stack.push(cur);
      cur = cur.left;
    }
    cur = stack.pop();
    out.push(cur.val);
    cur = cur.right;
  }
  return out;
}

function inorderMorris(root) {
  const out = [];
  let cur = root;
  while (cur) {
    if (!cur.left) {
      out.push(cur.val);
      cur = cur.right;
    } else {
      let pred = cur.left;
      while (pred.right && pred.right !== cur) pred = pred.right;
      if (!pred.right) {
        pred.right = cur; // thread
        cur = cur.left;
      } else {
        pred.right = null; // unthread
        out.push(cur.val);
        cur = cur.right;
      }
    }
  }
  return out;
}

assert.ok(isValidBST(build(2, 1, 3)));
assert.equal(isValidBST(build(5, 4, 6, null, null, 3, 7)), false);
const t = build(4, 2, 6, 1, 3, 5, 7);
assert.deepEqual(inorderIterative(t), [1, 2, 3, 4, 5, 6, 7]);
assert.deepEqual(inorderMorris(t), [1, 2, 3, 4, 5, 6, 7]);
assert.deepEqual(inorderIterative(t), [1, 2, 3, 4, 5, 6, 7]);
console.log('all checks passed');
