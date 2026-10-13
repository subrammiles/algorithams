/*
 * Path sum II, path sum III (prefix sums), root-to-leaf numbers, all nodes at distance K.
 */
const assert = require('node:assert/strict');
const { build, find } = require('./tree_node');

function pathSum(root, target) {
  const out = [];
  const go = (n, remaining, path) => {
    if (!n) return;
    path.push(n.val);
    if (!n.left && !n.right && remaining === n.val) out.push([...path]);
    go(n.left, remaining - n.val, path);
    go(n.right, remaining - n.val, path);
    path.pop();
  };
  go(root, target, []);
  return out;
}

function pathSumAnyDownward(root, target) {
  const prefixCount = new Map([[0, 1]]);
  const go = (n, prefix) => {
    if (!n) return 0;
    prefix += n.val;
    let count = prefixCount.get(prefix - target) ?? 0;
    prefixCount.set(prefix, (prefixCount.get(prefix) ?? 0) + 1);
    count += go(n.left, prefix) + go(n.right, prefix);
    prefixCount.set(prefix, prefixCount.get(prefix) - 1); // leave the path
    return count;
  };
  return go(root, 0);
}

function sumNumbers(n, value = 0) {
  if (!n) return 0;
  value = value * 10 + n.val;
  if (!n.left && !n.right) return value;
  return sumNumbers(n.left, value) + sumNumbers(n.right, value);
}

function distanceK(root, target, k) {
  const parent = new Map();
  const record = (n, p) => {
    if (!n) return;
    parent.set(n, p);
    record(n.left, n);
    record(n.right, n);
  };
  record(root, null);
  let frontier = [target];
  const seen = new Set(frontier);
  for (let d = 0; d < k; d++) {
    const next = [];
    for (const n of frontier) {
      for (const nb of [n.left, n.right, parent.get(n)]) {
        if (nb && !seen.has(nb)) {
          seen.add(nb);
          next.push(nb);
        }
      }
    }
    frontier = next;
  }
  return frontier.map((n) => n.val).sort((a, b) => a - b);
}

assert.deepEqual(pathSum(build(5, 4, 8, 11, null, 13, 4, 7, 2, null, null, 5, 1), 22), [[5, 4, 11, 2], [5, 8, 4, 5]]);
assert.equal(pathSumAnyDownward(build(10, 5, -3, 3, 2, null, 11, 3, -2, null, 1), 8), 3);
assert.equal(sumNumbers(build(1, 2, 3)), 25);
assert.equal(sumNumbers(build(4, 9, 0, 5, 1)), 1026);
const r = build(3, 5, 1, 6, 2, 0, 8, null, null, 7, 4);
assert.deepEqual(distanceK(r, find(r, 5), 2), [1, 4, 7]);
console.log('all checks passed');
