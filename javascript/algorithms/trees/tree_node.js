/* Shared tree helpers: build from / serialise to LeetCode-style level order (null = missing). */
class TreeNode {
  constructor(val, left = null, right = null) {
    this.val = val;
    this.left = left;
    this.right = right;
  }
}

function build(...levelOrder) {
  if (!levelOrder.length || levelOrder[0] === null) return null;
  const root = new TreeNode(levelOrder[0]);
  const queue = [root];
  let i = 1;
  for (let head = 0; i < levelOrder.length; head++) {
    const n = queue[head];
    for (const side of ['left', 'right']) {
      if (i < levelOrder.length && levelOrder[i] !== null) queue.push((n[side] = new TreeNode(levelOrder[i])));
      i++;
    }
  }
  return root;
}

function toLevelOrder(root) {
  const out = [];
  const queue = [root];
  for (let head = 0; head < queue.length; head++) {
    const n = queue[head];
    out.push(n ? n.val : null);
    if (n) queue.push(n.left, n.right);
  }
  while (out.length && out[out.length - 1] === null) out.pop();
  return out;
}

const find = (root, val) => (!root || root.val === val ? root : find(root.left, val) ?? find(root.right, val));
const inorder = (n) => (n ? [...inorder(n.left), n.val, ...inorder(n.right)] : []);

module.exports = { TreeNode, build, toLevelOrder, find, inorder };
