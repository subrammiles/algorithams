/*
 * Multi-source BFS: 01 matrix, walls and gates, shortest bridge.
 */
const assert = require('node:assert/strict');

const DIRS = [[1, 0], [-1, 0], [0, 1], [0, -1]];
const INF = 2147483647;

function updateMatrix(mat) {
  const dist = mat.map((row) => row.map((v) => (v === 0 ? 0 : -1)));
  const queue = [];
  mat.forEach((row, r) => row.forEach((v, c) => v === 0 && queue.push([r, c])));
  for (let head = 0; head < queue.length; head++) {
    const [r, c] = queue[head];
    for (const [dr, dc] of DIRS) {
      if (dist[r + dr]?.[c + dc] === -1) {
        dist[r + dr][c + dc] = dist[r][c] + 1;
        queue.push([r + dr, c + dc]);
      }
    }
  }
  return dist;
}

function wallsAndGates(rooms) {
  const queue = [];
  rooms.forEach((row, r) => row.forEach((v, c) => v === 0 && queue.push([r, c])));
  for (let head = 0; head < queue.length; head++) {
    const [r, c] = queue[head];
    for (const [dr, dc] of DIRS) {
      if (rooms[r + dr]?.[c + dc] === INF) {
        rooms[r + dr][c + dc] = rooms[r][c] + 1;
        queue.push([r + dr, c + dc]);
      }
    }
  }
}

function shortestBridge(grid) {
  const g = grid.map((row) => [...row]);
  const n = g.length;
  let start;
  for (let r = 0; r < n && !start; r++) for (let c = 0; c < n && !start; c++) if (g[r][c] === 1) start = [r, c];
  let queue = [];
  const stack = [start];
  g[start[0]][start[1]] = 2;
  while (stack.length) {
    const [r, c] = stack.pop();
    queue.push([r, c]);
    for (const [dr, dc] of DIRS) {
      if (g[r + dr]?.[c + dc] === 1) {
        g[r + dr][c + dc] = 2;
        stack.push([r + dr, c + dc]);
      }
    }
  }
  for (let steps = 0; queue.length; steps++) {
    const next = [];
    for (const [r, c] of queue) {
      for (const [dr, dc] of DIRS) {
        const v = g[r + dr]?.[c + dc];
        if (v === 1) return steps;
        if (v === 0) {
          g[r + dr][c + dc] = 2;
          next.push([r + dr, c + dc]);
        }
      }
    }
    queue = next;
  }
  return -1;
}

assert.deepEqual(updateMatrix([[0, 0, 0], [0, 1, 0], [1, 1, 1]]), [[0, 0, 0], [0, 1, 0], [1, 2, 1]]);
const rooms = [[INF, -1, 0, INF], [INF, INF, INF, -1], [INF, -1, INF, -1], [0, -1, INF, INF]];
wallsAndGates(rooms);
assert.deepEqual(rooms, [[3, -1, 0, 1], [2, 2, 1, -1], [1, -1, 2, -1], [0, -1, 3, 4]]);
assert.equal(shortestBridge([[0, 1], [1, 0]]), 1);
assert.equal(shortestBridge([[0, 1, 0], [0, 0, 0], [0, 0, 1]]), 2);
assert.equal(shortestBridge([[1, 1, 1, 1, 1], [1, 0, 0, 0, 1], [1, 0, 1, 0, 1], [1, 0, 0, 0, 1], [1, 1, 1, 1, 1]]), 1);
console.log('all checks passed');
