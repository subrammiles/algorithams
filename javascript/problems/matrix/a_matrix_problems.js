/*
 * Set zeroes in O(1) extra space, staircase search, Game of Life in place.
 */
const assert = require('node:assert/strict');

function setZeroes(m) {
  const rows = m.length;
  const cols = m[0].length;
  const firstRowZero = m[0].includes(0);
  const firstColZero = m.some((row) => row[0] === 0);
  for (let r = 1; r < rows; r++) for (let c = 1; c < cols; c++) if (m[r][c] === 0) m[r][0] = m[0][c] = 0;
  for (let r = 1; r < rows; r++) for (let c = 1; c < cols; c++) if (m[r][0] === 0 || m[0][c] === 0) m[r][c] = 0;
  if (firstRowZero) m[0].fill(0);
  if (firstColZero) m.forEach((row) => (row[0] = 0));
}

function searchMatrixII(m, target) {
  let r = 0;
  let c = m[0].length - 1;
  while (r < m.length && c >= 0) {
    if (m[r][c] === target) return true;
    if (m[r][c] > target) c--;
    else r++;
  }
  return false;
}

function gameOfLife(board) {
  const rows = board.length;
  const cols = board[0].length;
  for (let r = 0; r < rows; r++) {
    for (let c = 0; c < cols; c++) {
      let live = 0;
      for (let dr = -1; dr <= 1; dr++) for (let dc = -1; dc <= 1; dc++) if (dr || dc) live += (board[r + dr]?.[c + dc] ?? 0) & 1;
      const alive = board[r][c] & 1;
      if ((alive && (live === 2 || live === 3)) || (!alive && live === 3)) board[r][c] |= 2;
    }
  }
  for (const row of board) for (let c = 0; c < cols; c++) row[c] >>= 1;
}

const a = [[1, 1, 1], [1, 0, 1], [1, 1, 1]];
setZeroes(a);
assert.deepEqual(a, [[1, 0, 1], [0, 0, 0], [1, 0, 1]]);
const b = [[0, 1, 2, 0], [3, 4, 5, 2], [1, 3, 1, 5]];
setZeroes(b);
assert.deepEqual(b, [[0, 0, 0, 0], [0, 4, 5, 0], [0, 3, 1, 0]]);
const m = [[1, 4, 7, 11, 15], [2, 5, 8, 12, 19], [3, 6, 9, 16, 22], [10, 13, 14, 17, 24], [18, 21, 23, 26, 30]];
assert.ok(searchMatrixII(m, 5));
assert.equal(searchMatrixII(m, 20), false);
const life = [[0, 1, 0], [0, 0, 1], [1, 1, 1], [0, 0, 0]];
gameOfLife(life);
assert.deepEqual(life, [[0, 0, 0], [1, 0, 1], [0, 1, 1], [0, 1, 0]]);
console.log('all checks passed');
