/*
 * Reconstruct Itinerary with Hierholzer's algorithm (every edge once, lexically smallest).
 */
const assert = require('node:assert/strict');

function findItinerary(tickets) {
  const graph = new Map();
  for (const [src, dst] of [...tickets].sort((a, b) => (a[1] < b[1] ? 1 : -1))) { // reverse sort: pop() gives smallest
    if (!graph.has(src)) graph.set(src, []);
    graph.get(src).push(dst);
  }
  const route = [];
  const stack = ['JFK'];
  while (stack.length) {
    const next = graph.get(stack[stack.length - 1]);
    if (next && next.length) stack.push(next.pop());
    else route.push(stack.pop());
  }
  return route.reverse();
}

assert.deepEqual(findItinerary([['MUC', 'LHR'], ['JFK', 'MUC'], ['SFO', 'SJC'], ['LHR', 'SFO']]), ['JFK', 'MUC', 'LHR', 'SFO', 'SJC']);
assert.deepEqual(findItinerary([['JFK', 'SFO'], ['JFK', 'ATL'], ['SFO', 'ATL'], ['ATL', 'JFK'], ['ATL', 'SFO']]), ['JFK', 'ATL', 'JFK', 'SFO', 'ATL', 'SFO']);
assert.deepEqual(findItinerary([['JFK', 'KUL'], ['JFK', 'NRT'], ['NRT', 'JFK']]), ['JFK', 'NRT', 'JFK', 'KUL']);
console.log('all checks passed');
