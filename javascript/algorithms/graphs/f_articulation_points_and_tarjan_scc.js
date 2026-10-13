/*
 * Tarjan low-link: articulation points and single-pass strongly connected components.
 */
const assert = require('node:assert/strict');

function articulationPoints(n, edges) {
  const graph = Array.from({ length: n }, () => []);
  for (const [u, v] of edges) {
    graph[u].push(v);
    graph[v].push(u);
  }
  const disc = new Array(n).fill(-1);
  const low = new Array(n).fill(0);
  const cut = new Set();
  let timer = 0;
  const dfs = (u, parent) => {
    disc[u] = low[u] = timer++;
    let children = 0;
    for (const v of graph[u]) {
      if (v === parent) continue;
      if (disc[v] === -1) {
        children++;
        dfs(v, u);
        low[u] = Math.min(low[u], low[v]);
        if (parent !== -1 && low[v] >= disc[u]) cut.add(u);
      } else low[u] = Math.min(low[u], disc[v]);
    }
    if (parent === -1 && children >= 2) cut.add(u);
  };
  for (let i = 0; i < n; i++) if (disc[i] === -1) dfs(i, -1);
  return [...cut].sort((a, b) => a - b);
}

function tarjanSCC(n, edges) {
  const graph = Array.from({ length: n }, () => []);
  for (const [u, v] of edges) graph[u].push(v);
  const disc = new Array(n).fill(-1);
  const low = new Array(n).fill(0);
  const onStack = new Array(n).fill(false);
  const stack = [];
  const components = [];
  let timer = 0;
  const dfs = (u) => {
    disc[u] = low[u] = timer++;
    stack.push(u);
    onStack[u] = true;
    for (const v of graph[u]) {
      if (disc[v] === -1) {
        dfs(v);
        low[u] = Math.min(low[u], low[v]);
      } else if (onStack[v]) low[u] = Math.min(low[u], disc[v]);
    }
    if (low[u] === disc[u]) {
      const component = [];
      let v;
      do {
        v = stack.pop();
        onStack[v] = false;
        component.push(v);
      } while (v !== u);
      components.push(component.sort((a, b) => a - b));
    }
  };
  for (let i = 0; i < n; i++) if (disc[i] === -1) dfs(i);
  return components.sort((a, b) => a[0] - b[0]);
}

const edges = [[1, 0], [0, 2], [2, 1], [0, 3], [3, 4]];
assert.deepEqual(articulationPoints(5, edges), [0, 3]);
assert.deepEqual(articulationPoints(3, [[0, 1], [1, 2], [2, 0]]), []);
assert.deepEqual(tarjanSCC(5, edges), [[0, 1, 2], [3], [4]]);
console.log('all checks passed');
