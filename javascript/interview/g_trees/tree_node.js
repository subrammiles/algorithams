/* Shared TreeNode helpers: build() from LeetCode-style level order with nulls. */
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

const find = (root, val) => (!root || root.val === val ? root : find(root.left, val) ?? find(root.right, val));

module.exports = { TreeNode, build, find };
