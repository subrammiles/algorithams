/*
 * Clone graph, valid tree (n-1 edges + connected), connected components, all paths in a DAG.
 */
const assert = require('node:assert/strict');

const node = (val) => ({ val, neighbors: [] });

function cloneGraph(n, copies = new Map()) {
  if (!n) return null;
  if (copies.has(n)) return copies.get(n);
  const copy = node(n.val);
  copies.set(n, copy); // register before recursing (cycles)
  copy.neighbors = n.neighbors.map((nb) => cloneGraph(nb, copies));
  return copy;
}

function countComponents(n, edges) {
  const graph = Array.from({ length: n }, () => []);
  for (const [u, v] of edges) {
    graph[u].push(v);
    graph[v].push(u);
  }
  const seen = new Array(n).fill(false);
  let components = 0;
  for (let s = 0; s < n; s++) {
    if (seen[s]) continue;
    components++;
    const stack = [s];
    seen[s] = true;
    while (stack.length) {
      for (const v of graph[stack.pop()]) {
        if (!seen[v]) {
          seen[v] = true;
          stack.push(v);
        }
      }
    }
  }
  return components;
}

const validTree = (n, edges) => edges.length === n - 1 && countComponents(n, edges) === 1;

function allPathsSourceTarget(graph) {
  const paths = [];
  const go = (u, path) => {
    if (u === graph.length - 1) return paths.push([...path]);
    for (const v of graph[u]) go(v, [...path, v]);
  };
  go(0, [0]);
  return paths;
}

const nodes = [1, 2, 3, 4].map(node);
nodes.forEach((n, i) => (n.neighbors = [nodes[(i + 1) % 4], nodes[(i + 3) % 4]]));
const copy = cloneGraph(nodes[0]);
assert.ok(copy !== nodes[0] && copy.val === 1);
assert.deepEqual(copy.neighbors.map((n) => n.val), [2, 4]);
assert.equal(copy.neighbors[0].neighbors[0].neighbors[0].neighbors[0], copy);
assert.ok(validTree(5, [[0, 1], [0, 2], [0, 3], [1, 4]]));
assert.equal(validTree(5, [[0, 1], [1, 2], [2, 3], [1, 3], [1, 4]]), false);
assert.equal(countComponents(5, [[0, 1], [1, 2], [3, 4]]), 2);
assert.deepEqual(allPathsSourceTarget([[1, 2], [3], [3], []]), [[0, 1, 3], [0, 2, 3]]);
console.log('all checks passed');
