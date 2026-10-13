/*
 * Network delay time (Dijkstra, lazy deletion) and cheapest flights within K stops (Bellman-Ford rounds).
 */
const assert = require('node:assert/strict');
const { Heap } = require('../lib/heap');

function networkDelayTime(times, n, source) {
  const graph = new Map();
  for (const [u, v, w] of times) {
    if (!graph.has(u)) graph.set(u, []);
    graph.get(u).push([v, w]);
  }
  const dist = new Map();
  const heap = new Heap((a, b) => a[0] - b[0]);
  heap.push([0, source]);
  while (heap.size) {
    const [d, u] = heap.pop();
    if (dist.has(u)) continue; // stale
    dist.set(u, d);
    for (const [v, w] of graph.get(u) ?? []) if (!dist.has(v)) heap.push([d + w, v]);
  }
  return dist.size === n ? Math.max(...dist.values()) : -1;
}

function findCheapestPrice(n, flights, src, dst, k) {
  let cost = new Array(n).fill(Infinity);
  cost[src] = 0;
  for (let round = 0; round <= k; round++) {
    const next = [...cost];
    for (const [u, v, w] of flights) if (cost[u] + w < next[v]) next[v] = cost[u] + w;
    cost = next;
  }
  return cost[dst] === Infinity ? -1 : cost[dst];
}

assert.equal(networkDelayTime([[2, 1, 1], [2, 3, 1], [3, 4, 1]], 4, 2), 2);
assert.equal(networkDelayTime([[1, 2, 1]], 2, 2), -1);
const flights = [[0, 1, 100], [1, 2, 100], [2, 0, 100], [1, 3, 600], [2, 3, 200]];
assert.equal(findCheapestPrice(4, flights, 0, 3, 1), 700);
assert.equal(findCheapestPrice(4, flights, 0, 3, 2), 400);
console.log('all checks passed');
