/*
 * Sudoku: backtracking with row/col/box bitmasks and the MRV heuristic.
 */
const assert = require('node:assert/strict');

function solve(board) {
  const rows = new Array(9).fill(0);
  const cols = new Array(9).fill(0);
  const boxes = new Array(9).fill(0);
  const box = (r, c) => Math.floor(r / 3) * 3 + Math.floor(c / 3);
  const toggle = (r, c, d) => {
    rows[r] ^= 1 << d;
    cols[c] ^= 1 << d;
    boxes[box(r, c)] ^= 1 << d;
  };
  const candidates = (r, c) => ~(rows[r] | cols[c] | boxes[box(r, c)]) & 0x1ff;
  const popcount = (x) => x.toString(2).replace(/0/g, '').length;
  for (let r = 0; r < 9; r++) for (let c = 0; c < 9; c++) if (board[r][c] !== '.') toggle(r, c, board[r][c] - 1);

  const backtrack = () => {
    let best = null;
    for (let r = 0; r < 9; r++) {
      for (let c = 0; c < 9; c++) {
        if (board[r][c] !== '.') continue;
        const count = popcount(candidates(r, c));
        if (!best || count < best[0]) best = [count, r, c];
      }
    }
    if (!best) return true;
    const [, r, c] = best;
    for (let mask = candidates(r, c); mask; mask &= mask - 1) {
      const d = 31 - Math.clz32(mask & -mask);
      toggle(r, c, d);
      board[r][c] = String(d + 1);
      if (backtrack()) return true;
      toggle(r, c, d);
      board[r][c] = '.';
    }
    return false;
  };
  if (!backtrack()) throw new Error('unsolvable');
}

const puzzle = ['53..7....', '6..195...', '.98....6.', '8...6...3', '4..8.3..1', '7...2...6', '.6....28.', '...419..5', '....8..79'];
const solution = ['534678912', '672195348', '198342567', '859761423', '426853791', '713924856', '961537284', '287419635', '345286179'];
const board = puzzle.map((row) => [...row]);
solve(board);
assert.deepEqual(board.map((row) => row.join('')), solution);
console.log('all checks passed');
