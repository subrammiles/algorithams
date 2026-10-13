/*
 * Max flow (Edmonds-Karp) and maximum bipartite matching (Kuhn).
 */
const assert = require('node:assert/strict');

function maxFlow(capacity, source, sink) {
  const n = capacity.length;
  const residual = capacity.map((row) => [...row]);
  let flow = 0;
  for (;;) {
    const parent = new Array(n).fill(-1);
    parent[source] = source;
    const queue = [source];
    for (let head = 0; head < queue.length && parent[sink] === -1; head++) {
      const u = queue[head];
      for (let v = 0; v < n; v++) {
        if (parent[v] === -1 && residual[u][v] > 0) {
          parent[v] = u;
          queue.push(v);
        }
      }
    }
    if (parent[sink] === -1) return flow;
    let bottleneck = Infinity;
    for (let v = sink; v !== source; v = parent[v]) bottleneck = Math.min(bottleneck, residual[parent[v]][v]);
    for (let v = sink; v !== source; v = parent[v]) {
      residual[parent[v]][v] -= bottleneck;
      residual[v][parent[v]] += bottleneck;
    }
    flow += bottleneck;
  }
}

function maxBipartiteMatching(adj, rightCount) {
  const matchOfRight = new Array(rightCount).fill(-1);
  const tryAugment = (left, seen) => {
    for (const right of adj[left]) {
      if (seen.has(right)) continue;
      seen.add(right);
      if (matchOfRight[right] === -1 || tryAugment(matchOfRight[right], seen)) {
        matchOfRight[right] = left;
        return true;
      }
    }
    return false;
  };
  return adj.reduce((count, _, left) => count + (tryAugment(left, new Set()) ? 1 : 0), 0);
}

const cap = [[0, 16, 13, 0, 0, 0], [0, 0, 10, 12, 0, 0], [0, 4, 0, 0, 14, 0], [0, 0, 9, 0, 0, 20], [0, 0, 0, 7, 0, 4], [0, 0, 0, 0, 0, 0]];
assert.equal(maxFlow(cap, 0, 5), 23);
assert.equal(maxBipartiteMatching([[0, 1], [0], [0]], 2), 2);
assert.equal(maxBipartiteMatching([[0, 1], [0, 2], [1]], 3), 3);
console.log('all checks passed');
