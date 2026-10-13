/*
 * Alien dictionary: edges from the first differing letters, then Kahn's topological sort.
 */
const assert = require('node:assert/strict');

function alienOrder(words) {
  const graph = new Map();
  const indegree = new Map();
  for (const w of words) {
    for (const c of w) {
      if (!graph.has(c)) {
        graph.set(c, new Set());
        indegree.set(c, 0);
      }
    }
  }
  for (let i = 0; i + 1 < words.length; i++) {
    const [a, b] = [words[i], words[i + 1]];
    if (a.length > b.length && a.startsWith(b)) return '';
    for (let j = 0; j < Math.min(a.length, b.length); j++) {
      if (a[j] !== b[j]) {
        if (!graph.get(a[j]).has(b[j])) {
          graph.get(a[j]).add(b[j]);
          indegree.set(b[j], indegree.get(b[j]) + 1);
        }
        break;
      }
    }
  }
  const order = [...graph.keys()].filter((c) => indegree.get(c) === 0).sort();
  for (let head = 0; head < order.length; head++) {
    for (const next of [...graph.get(order[head])].sort()) {
      indegree.set(next, indegree.get(next) - 1);
      if (indegree.get(next) === 0) order.push(next);
    }
  }
  return order.length === graph.size ? order.join('') : '';
}

assert.equal(alienOrder(['wrt', 'wrf', 'er', 'ett', 'rftt']), 'wertf');
assert.equal(alienOrder(['z', 'x', 'z']), '');
assert.equal(alienOrder(['abc', 'ab']), '');
console.log('all checks passed');
