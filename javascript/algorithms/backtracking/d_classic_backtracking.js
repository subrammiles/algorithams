/*
 * Classic backtracking: rat in a maze, m-colouring, Hamiltonian cycle, knight's tour.
 * Full explanations in D_ClassicBacktracking.java.
 * Skeleton: if complete -> record; for each choice: if valid -> apply, recurse, UNDO.
 */
const assert = require('node:assert/strict');

const MOVES = [[1, 0, 'D'], [0, -1, 'L'], [0, 1, 'R'], [-1, 0, 'U']]; // alphabetical -> sorted output
const KNIGHT = [[2, 1], [1, 2], [-1, 2], [-2, 1], [-2, -1], [-1, -2], [1, -2], [2, -1]];

function ratInMaze(maze) {
  const n = maze.length;
  const out = [];
  if (!n || !maze[0][0] || !maze[n - 1][n - 1]) return out;
  const seen = maze.map((row) => row.map(() => false));
  seen[0][0] = true;
  const path = [];
  const walk = (r, c) => {
    if (r === n - 1 && c === n - 1) {
      out.push(path.join(''));
      return;
    }
    for (const [dr, dc, ch] of MOVES) {
      const nr = r + dr;
      const nc = c + dc;
      if (nr < 0 || nc < 0 || nr >= n || nc >= n || !maze[nr][nc] || seen[nr][nc]) continue;
      seen[nr][nc] = true;
      path.push(ch);
      walk(nr, nc);
      path.pop(); // undo
      seen[nr][nc] = false;
    }
  };
  walk(0, 0);
  return out;
}

function mColoring(n, edges, m) {
  const adj = Array.from({ length: n }, () => []);
  for (const [u, v] of edges) {
    adj[u].push(v);
    adj[v].push(u);
  }
  const color = new Array(n).fill(0);
  const colorFrom = (v) => {
    if (v === n) return true;
    for (let c = 1; c <= m; c++) {
      if (adj[v].some((u) => color[u] === c)) continue;
      color[v] = c;
      if (colorFrom(v + 1)) return true;
      color[v] = 0;
    }
    return false;
  };
  return colorFrom(0) ? color : [];
}

function hamiltonianCycle(n, edges) {
  const adj = Array.from({ length: n }, () => new Array(n).fill(false));
  for (const [u, v] of edges) adj[u][v] = adj[v][u] = true;
  const path = [0];
  const used = new Array(n).fill(false);
  used[0] = true;
  const extend = () => {
    const last = path.at(-1);
    if (path.length === n) return adj[last][0];
    for (let v = 1; v < n; v++) {
      if (used[v] || !adj[last][v]) continue;
      used[v] = true;
      path.push(v);
      if (extend()) return true;
      path.pop();
      used[v] = false;
    }
    return false;
  };
  return extend() ? [...path, 0] : [];
}

function knightsTour(n) {
  const board = Array.from({ length: n }, () => new Array(n).fill(-1));
  board[0][0] = 0;
  const free = (r, c) => r >= 0 && c >= 0 && r < n && c < n && board[r][c] === -1;
  const onward = (r, c) => KNIGHT.filter(([dr, dc]) => free(r + dr, c + dc)).length;
  const tour = (r, c, step) => {
    if (step === n * n) return true;
    const next = KNIGHT.map(([dr, dc]) => [r + dr, c + dc])
      .filter(([nr, nc]) => free(nr, nc))
      .map(([nr, nc]) => [nr, nc, onward(nr, nc)])
      .sort((a, b) => a[2] - b[2]); // Warnsdorff; Array.sort is stable
    for (const [nr, nc] of next) {
      board[nr][nc] = step;
      if (tour(nr, nc, step + 1)) return true;
      board[nr][nc] = -1;
    }
    return false;
  };
  return tour(0, 0, 1) ? board : [];
}

function isValidTour(board) {
  const n = board.length;
  const where = new Array(n * n);
  for (let r = 0; r < n; r++) {
    for (let c = 0; c < n; c++) {
      const s = board[r][c];
      if (s < 0 || s >= n * n || where[s]) return false;
      where[s] = [r, c];
    }
  }
  for (let s = 1; s < n * n; s++) {
    if (Math.abs(where[s][0] - where[s - 1][0]) * Math.abs(where[s][1] - where[s - 1][1]) !== 2) return false;
  }
  return true;
}

const maze = [[1, 0, 0, 0], [1, 1, 0, 1], [1, 1, 0, 0], [0, 1, 1, 1]];
assert.deepEqual(ratInMaze(maze), ['DDRDRR', 'DRDDRR']);
assert.deepEqual(ratInMaze([[1, 0], [0, 1]]), []);

const graph = [[0, 1], [1, 2], [2, 3], [3, 0], [0, 2]];
assert.deepEqual(mColoring(4, graph, 3), [1, 2, 3, 2]);
assert.deepEqual(mColoring(4, graph, 2), []);

const g1 = [[0, 1], [0, 3], [1, 2], [1, 3], [1, 4], [2, 4], [3, 4]];
assert.deepEqual(hamiltonianCycle(5, g1), [0, 1, 2, 4, 3, 0]);
assert.deepEqual(hamiltonianCycle(5, g1.slice(0, -1)), []);

assert.ok(isValidTour(knightsTour(5)));
assert.ok(isValidTour(knightsTour(8)));
assert.deepEqual(knightsTour(3), []);
assert.deepEqual(knightsTour(4), []);
console.log('all checks passed');
