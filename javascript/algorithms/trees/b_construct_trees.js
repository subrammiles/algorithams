/*
 * Build from preorder + inorder / inorder + postorder, sorted array -> balanced BST, BST iterator.
 */
const assert = require('node:assert/strict');
const { TreeNode, build, toLevelOrder, inorder } = require('./tree_node');

function buildFromPreIn(preorder, inorderVals) {
  const index = new Map(inorderVals.map((v, i) => [v, i]));
  let p = 0;
  const go = (lo, hi) => {
    if (lo > hi) return null;
    const root = new TreeNode(preorder[p++]);
    const mid = index.get(root.val);
    root.left = go(lo, mid - 1);
    root.right = go(mid + 1, hi);
    return root;
  };
  return go(0, inorderVals.length - 1);
}

function buildFromInPost(inorderVals, postorder) {
  const index = new Map(inorderVals.map((v, i) => [v, i]));
  let p = postorder.length - 1;
  const go = (lo, hi) => {
    if (lo > hi) return null;
    const root = new TreeNode(postorder[p--]);
    const mid = index.get(root.val);
    root.right = go(mid + 1, hi); // right first: it sits just before the root in postorder
    root.left = go(lo, mid - 1);
    return root;
  };
  return go(0, inorderVals.length - 1);
}

function sortedArrayToBST(a, lo = 0, hi = a.length - 1) {
  if (lo > hi) return null;
  const mid = (lo + hi) >> 1;
  return new TreeNode(a[mid], sortedArrayToBST(a, lo, mid - 1), sortedArrayToBST(a, mid + 1, hi));
}

class BSTIterator {
  constructor(root) {
    this.stack = [];
    this.pushLeft(root);
  }

  pushLeft(n) {
    for (; n; n = n.left) this.stack.push(n);
  }

  hasNext() {
    return this.stack.length > 0;
  }

  next() {
    const n = this.stack.pop();
    this.pushLeft(n.right);
    return n.val;
  }
}

const height = (n) => (n ? 1 + Math.max(height(n.left), height(n.right)) : 0);

const expected = [3, 9, 20, null, null, 15, 7];
assert.deepEqual(toLevelOrder(buildFromPreIn([3, 9, 20, 15, 7], [9, 3, 15, 20, 7])), expected);
assert.deepEqual(toLevelOrder(buildFromInPost([9, 3, 15, 20, 7], [9, 15, 7, 20, 3])), expected);
const bst = sortedArrayToBST([-10, -3, 0, 5, 9]);
assert.deepEqual(inorder(bst), [-10, -3, 0, 5, 9]);
assert.equal(height(bst), 3);
const it = new BSTIterator(build(7, 3, 15, null, null, 9, 20));
const out = [];
while (it.hasNext()) out.push(it.next());
assert.deepEqual(out, [3, 7, 9, 15, 20]);
console.log('all checks passed');
