/*
 * Course Schedule II: Kahn's algorithm, and DFS 3-colour cycle detection. O(V + E).
 */
const assert = require('node:assert/strict');

function findOrderKahn(n, prerequisites) {
  const graph = Array.from({ length: n }, () => []);
  const indegree = new Array(n).fill(0);
  for (const [a, b] of prerequisites) {
    graph[b].push(a);
    indegree[a]++;
  }
  const order = [];
  indegree.forEach((d, i) => d === 0 && order.push(i));
  for (let head = 0; head < order.length; head++) {
    for (const v of graph[order[head]]) if (--indegree[v] === 0) order.push(v);
  }
  return order.length === n ? order : [];
}

function hasCycleDfs(n, prerequisites) {
  const graph = Array.from({ length: n }, () => []);
  for (const [a, b] of prerequisites) graph[b].push(a);
  const color = new Array(n).fill(0); // 0 white, 1 grey, 2 black
  const dfs = (u) => {
    color[u] = 1;
    for (const v of graph[u]) if (color[v] === 1 || (color[v] === 0 && dfs(v))) return true;
    color[u] = 2;
    return false;
  };
  return color.some((c, i) => c === 0 && dfs(i));
}

const pre = [[1, 0], [2, 0], [3, 1], [3, 2]];
assert.deepEqual(findOrderKahn(4, pre), [0, 1, 2, 3]);
assert.deepEqual(findOrderKahn(2, [[0, 1], [1, 0]]), []);
assert.equal(hasCycleDfs(4, pre), false);
assert.ok(hasCycleDfs(3, [[1, 0], [2, 1], [0, 2]]));
console.log('all checks passed');
