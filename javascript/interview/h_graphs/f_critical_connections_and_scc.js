/*
 * Bridges (Tarjan low-link) and SCC count (Kosaraju). O(V + E).
 */
const assert = require('node:assert/strict');

function criticalConnections(n, edges) {
  const graph = Array.from({ length: n }, () => []);
  for (const [u, v] of edges) {
    graph[u].push(v);
    graph[v].push(u);
  }
  const disc = new Array(n).fill(-1);
  const low = new Array(n).fill(0);
  const bridges = [];
  let timer = 0;
  const dfs = (u, parent) => {
    disc[u] = low[u] = timer++;
    for (const v of graph[u]) {
      if (v === parent) continue;
      if (disc[v] === -1) {
        dfs(v, u);
        low[u] = Math.min(low[u], low[v]);
        if (low[v] > disc[u]) bridges.push([Math.min(u, v), Math.max(u, v)]);
      } else low[u] = Math.min(low[u], disc[v]);
    }
  };
  for (let i = 0; i < n; i++) if (disc[i] === -1) dfs(i, -1);
  return bridges;
}

function countSCC(n, edges) {
  const graph = Array.from({ length: n }, () => []);
  const rev = Array.from({ length: n }, () => []);
  for (const [u, v] of edges) {
    graph[u].push(v);
    rev[v].push(u);
  }
  let seen = new Array(n).fill(false);
  const order = [];
  const fill = (u) => {
    seen[u] = true;
    for (const v of graph[u]) if (!seen[v]) fill(v);
    order.push(u);
  };
  const mark = (u) => {
    seen[u] = true;
    for (const v of rev[u]) if (!seen[v]) mark(v);
  };
  for (let i = 0; i < n; i++) if (!seen[i]) fill(i);
  seen = new Array(n).fill(false);
  let components = 0;
  for (const u of order.reverse()) {
    if (!seen[u]) {
      components++;
      mark(u);
    }
  }
  return components;
}

assert.deepEqual(criticalConnections(4, [[0, 1], [1, 2], [2, 0], [1, 3]]), [[1, 3]]);
assert.equal(countSCC(5, [[1, 0], [0, 2], [2, 1], [0, 3], [3, 4]]), 3);
console.log('all checks passed');
