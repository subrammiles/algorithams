/*
 * Min cost to connect all points: dense graph -> array Prim O(V^2); Kruskal O(V^2 log V) for comparison.
 */
const assert = require('node:assert/strict');

const dist = (a, b) => Math.abs(a[0] - b[0]) + Math.abs(a[1] - b[1]);

function minCostPrim(points) {
  const n = points.length;
  const best = new Array(n).fill(Infinity);
  const inTree = new Array(n).fill(false);
  best[0] = 0;
  let total = 0;
  for (let step = 0; step < n; step++) {
    let u = -1;
    for (let v = 0; v < n; v++) if (!inTree[v] && (u === -1 || best[v] < best[u])) u = v;
    inTree[u] = true;
    total += best[u];
    for (let v = 0; v < n; v++) if (!inTree[v]) best[v] = Math.min(best[v], dist(points[u], points[v]));
  }
  return total;
}

function minCostKruskal(points) {
  const n = points.length;
  const edges = [];
  for (let i = 0; i < n; i++) for (let j = i + 1; j < n; j++) edges.push([dist(points[i], points[j]), i, j]);
  edges.sort((a, b) => a[0] - b[0]);
  const parent = Array.from({ length: n }, (_, i) => i);
  const find = (x) => (parent[x] === x ? x : (parent[x] = find(parent[x])));
  let total = 0;
  for (const [w, i, j] of edges) {
    const a = find(i);
    const b = find(j);
    if (a !== b) {
      parent[a] = b;
      total += w;
    }
  }
  return total;
}

const pts = [[0, 0], [2, 2], [3, 10], [5, 2], [7, 0]];
assert.equal(minCostPrim(pts), 20);
assert.equal(minCostKruskal(pts), 20);
assert.equal(minCostPrim([[3, 12], [-2, 5], [-4, 1]]), 18);
assert.equal(minCostKruskal([[3, 12], [-2, 5], [-4, 1]]), 18);
assert.equal(minCostPrim([[1, 1]]), 0);
console.log('all checks passed');
