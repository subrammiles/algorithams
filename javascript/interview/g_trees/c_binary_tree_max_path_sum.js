/*
 * Max path sum and diameter: return the best downward path, record the best bending path.
 */
const assert = require('node:assert/strict');
const { build } = require('./tree_node');

function maxPathSum(root) {
  let best = -Infinity;
  const gain = (n) => {
    if (!n) return 0;
    const left = Math.max(0, gain(n.left));
    const right = Math.max(0, gain(n.right));
    best = Math.max(best, n.val + left + right);
    return n.val + Math.max(left, right);
  };
  gain(root);
  return best;
}

function diameter(root) {
  let best = 0;
  const depth = (n) => {
    if (!n) return 0;
    const l = depth(n.left);
    const r = depth(n.right);
    best = Math.max(best, l + r);
    return 1 + Math.max(l, r);
  };
  depth(root);
  return best;
}

assert.equal(maxPathSum(build(-10, 9, 20, null, null, 15, 7)), 42);
assert.equal(maxPathSum(build(-3)), -3);
assert.equal(diameter(build(1, 2, 3, 4, 5)), 3);
console.log('all checks passed');
