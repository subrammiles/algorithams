/*
 * 0-1 BFS, max-probability Dijkstra, swim in rising water (minimax), A* on a grid.
 */
const assert = require('node:assert/strict');
const { Heap } = require('../../lib/heap');

const DIRS = [[1, 0], [-1, 0], [0, 1], [0, -1]];

function minimumObstacles(grid) {
  const rows = grid.length;
  const cols = grid[0].length;
  const dist = grid.map((row) => row.map(() => Infinity));
  dist[0][0] = 0;
  const dq = [[0, 0]]; // small grids: array unshift is fine; use a real deque for big inputs
  while (dq.length) {
    const [r, c] = dq.shift();
    for (const [dr, dc] of DIRS) {
      const nr = r + dr;
      const nc = c + dc;
      if (nr < 0 || nc < 0 || nr >= rows || nc >= cols) continue;
      if (dist[r][c] + grid[nr][nc] < dist[nr][nc]) {
        dist[nr][nc] = dist[r][c] + grid[nr][nc];
        if (grid[nr][nc] === 0) dq.unshift([nr, nc]);
        else dq.push([nr, nc]);
      }
    }
  }
  return dist[rows - 1][cols - 1];
}

function maxProbability(n, edges, prob, start, end) {
  const graph = Array.from({ length: n }, () => []);
  edges.forEach(([u, v], i) => {
    graph[u].push([v, prob[i]]);
    graph[v].push([u, prob[i]]);
  });
  const best = new Array(n).fill(0);
  best[start] = 1;
  const heap = new Heap((a, b) => b[0] - a[0]);
  heap.push([1, start]);
  while (heap.size) {
    const [p, u] = heap.pop();
    if (u === end) return p;
    if (p < best[u]) continue;
    for (const [v, w] of graph[u]) {
      if (p * w > best[v]) {
        best[v] = p * w;
        heap.push([best[v], v]);
      }
    }
  }
  return 0;
}

function swimInWater(grid) {
  const n = grid.length;
  const seen = new Set(['0,0']);
  const heap = new Heap((a, b) => a[0] - b[0]);
  heap.push([grid[0][0], 0, 0]);
  while (heap.size) {
    const [t, r, c] = heap.pop();
    if (r === n - 1 && c === n - 1) return t;
    for (const [dr, dc] of DIRS) {
      const nr = r + dr;
      const nc = c + dc;
      if (nr >= 0 && nc >= 0 && nr < n && nc < n && !seen.has(`${nr},${nc}`)) {
        seen.add(`${nr},${nc}`);
        heap.push([Math.max(t, grid[nr][nc]), nr, nc]);
      }
    }
  }
  return -1;
}

function aStar(grid) {
  const rows = grid.length;
  const cols = grid[0].length;
  if (grid[0][0] === '#') return -1;
  const h = (r, c) => rows - 1 - r + (cols - 1 - c);
  const g = grid.map((row) => [...row].map(() => Infinity));
  g[0][0] = 0;
  const open = new Heap((a, b) => a[0] - b[0]);
  open.push([h(0, 0), 0, 0]);
  while (open.size) {
    const [f, r, c] = open.pop();
    if (r === rows - 1 && c === cols - 1) return g[r][c];
    if (f > g[r][c] + h(r, c)) continue; // stale
    for (const [dr, dc] of DIRS) {
      const nr = r + dr;
      const nc = c + dc;
      if (nr < 0 || nc < 0 || nr >= rows || nc >= cols || grid[nr][nc] === '#') continue;
      if (g[r][c] + 1 < g[nr][nc]) {
        g[nr][nc] = g[r][c] + 1;
        open.push([g[nr][nc] + h(nr, nc), nr, nc]);
      }
    }
  }
  return -1;
}

assert.equal(minimumObstacles([[0, 1, 1], [1, 1, 0], [1, 1, 0]]), 2);
assert.equal(minimumObstacles([[0, 1, 0, 0, 0], [0, 1, 0, 1, 0], [0, 0, 0, 1, 0]]), 0);
const edges = [[0, 1], [1, 2], [0, 2]];
assert.ok(Math.abs(maxProbability(3, edges, [0.5, 0.5, 0.2], 0, 2) - 0.25) < 1e-9);
assert.ok(Math.abs(maxProbability(3, edges, [0.5, 0.5, 0.3], 0, 2) - 0.3) < 1e-9);
assert.equal(swimInWater([[0, 2], [1, 3]]), 3);
assert.equal(swimInWater([[0, 1, 2, 3, 4], [24, 23, 22, 21, 5], [12, 13, 14, 15, 16], [11, 17, 18, 19, 20], [10, 9, 8, 7, 6]]), 16);
assert.equal(aStar(['.....', '.###.', '...#.', '##.#.', '.....']), 8);
assert.equal(aStar(['.....', '####.', '.....', '.####', '.....']), 16);
assert.equal(aStar(['.#', '#.']), -1);
console.log('all checks passed');
