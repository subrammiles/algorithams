/*
 * Serialize / deserialize with preorder + '#' for null. O(n).
 */
const assert = require('node:assert/strict');
const { TreeNode, build } = require('./tree_node');

function serialize(root) {
  const out = [];
  const write = (n) => {
    if (!n) return out.push('#');
    out.push(String(n.val));
    write(n.left);
    write(n.right);
  };
  write(root);
  return out.join(',');
}

function deserialize(data) {
  const tokens = data.split(',');
  let i = 0;
  const read = () => {
    const t = tokens[i++];
    if (t === '#') return null;
    const n = new TreeNode(Number(t));
    n.left = read();
    n.right = read();
    return n;
  };
  return read();
}

const s = serialize(build(1, 2, 3, null, null, 4, 5));
assert.equal(s, '1,2,#,#,3,4,#,#,5,#,#');
assert.equal(serialize(deserialize(s)), s);
assert.equal(serialize(deserialize(serialize(null))), '#');
console.log('all checks passed');
