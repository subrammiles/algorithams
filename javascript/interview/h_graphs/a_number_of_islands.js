/*
 * Number of islands (flood fill with an explicit stack) and rotting oranges (multi-source BFS).
 */
const assert = require('node:assert/strict');

const DIRS = [[1, 0], [-1, 0], [0, 1], [0, -1]];

function numIslands(grid) {
  const rows = grid.length;
  const cols = grid[0].length;
  const seen = grid.map((row) => new Array(row.length).fill(false));
  let count = 0;
  for (let r = 0; r < rows; r++) {
    for (let c = 0; c < cols; c++) {
      if (grid[r][c] !== '1' || seen[r][c]) continue;
      count++;
      const stack = [[r, c]];
      seen[r][c] = true;
      while (stack.length) {
        const [cr, cc] = stack.pop();
        for (const [dr, dc] of DIRS) {
          const nr = cr + dr;
          const nc = cc + dc;
          if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && grid[nr][nc] === '1' && !seen[nr][nc]) {
            seen[nr][nc] = true;
            stack.push([nr, nc]);
          }
        }
      }
    }
  }
  return count;
}

function orangesRotting(grid) {
  const g = grid.map((row) => [...row]);
  let queue = [];
  let fresh = 0;
  g.forEach((row, r) => row.forEach((v, c) => (v === 2 ? queue.push([r, c]) : v === 1 && fresh++)));
  let minutes = 0;
  while (queue.length && fresh) {
    minutes++;
    const next = [];
    for (const [r, c] of queue) {
      for (const [dr, dc] of DIRS) {
        if (g[r + dr]?.[c + dc] === 1) {
          g[r + dr][c + dc] = 2;
          fresh--;
          next.push([r + dr, c + dc]);
        }
      }
    }
    queue = next;
  }
  return fresh === 0 ? minutes : -1;
}

assert.equal(numIslands(['11000', '11000', '00100', '00011']), 3);
assert.equal(orangesRotting([[2, 1, 1], [1, 1, 0], [0, 1, 1]]), 4);
assert.equal(orangesRotting([[2, 1, 1], [0, 1, 1], [1, 0, 1]]), -1);
console.log('all checks passed');
