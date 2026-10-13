/*
 * Recover a BST with two swapped nodes, inorder successor, trim BST, BST from preorder.
 */
const assert = require('node:assert/strict');
const { TreeNode, build, toLevelOrder, inorder } = require('./tree_node');

function recoverTree(root) {
  let prev = null;
  let first = null;
  let second = null;
  const walk = (n) => {
    if (!n) return;
    walk(n.left);
    if (prev && prev.val > n.val) {
      first ??= prev;
      second = n;
    }
    prev = n;
    walk(n.right);
  };
  walk(root);
  [first.val, second.val] = [second.val, first.val];
}

function inorderSuccessor(root, x) {
  let candidate = null;
  for (let n = root; n; ) {
    if (x < n.val) {
      candidate = n.val;
      n = n.left;
    } else n = n.right;
  }
  return candidate;
}

function trimBST(n, low, high) {
  if (!n) return null;
  if (n.val < low) return trimBST(n.right, low, high);
  if (n.val > high) return trimBST(n.left, low, high);
  n.left = trimBST(n.left, low, high);
  n.right = trimBST(n.right, low, high);
  return n;
}

function bstFromPreorder(preorder) {
  let i = 0;
  const go = (bound) => {
    if (i === preorder.length || preorder[i] > bound) return null;
    const n = new TreeNode(preorder[i++]);
    n.left = go(n.val);
    n.right = go(bound);
    return n;
  };
  return go(Infinity);
}

const a = build(1, 3, null, null, 2);
recoverTree(a);
assert.deepEqual(toLevelOrder(a), [3, 1, null, null, 2]);
const b = build(3, 1, 4, null, null, 2);
recoverTree(b);
assert.deepEqual(inorder(b), [1, 2, 3, 4]);
const bst = build(5, 3, 6, 2, 4, null, null, 1);
assert.equal(inorderSuccessor(bst, 4), 5);
assert.equal(inorderSuccessor(bst, 1), 2);
assert.equal(inorderSuccessor(bst, 6), null);
assert.deepEqual(toLevelOrder(trimBST(build(3, 0, 4, null, 2, null, null, 1), 1, 3)), [3, 2, null, 1]);
assert.deepEqual(toLevelOrder(bstFromPreorder([8, 5, 1, 7, 10, 12])), [8, 5, 10, 1, 7, null, 12]);
console.log('all checks passed');
