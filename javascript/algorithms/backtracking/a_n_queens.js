/*
 * N-Queens: boards via row-by-row placement with column/diagonal sets; counting with bitmasks.
 */
const assert = require('node:assert/strict');

function solveNQueens(n) {
  const out = [];
  const cols = new Set();
  const diag = new Set();
  const anti = new Set();
  const queenCol = [];
  const place = (r) => {
    if (r === n) return out.push(queenCol.map((c) => '.'.repeat(c) + 'Q' + '.'.repeat(n - c - 1)));
    for (let c = 0; c < n; c++) {
      if (cols.has(c) || diag.has(r - c) || anti.has(r + c)) continue;
      queenCol[r] = c;
      cols.add(c);
      diag.add(r - c);
      anti.add(r + c);
      place(r + 1);
      cols.delete(c);
      diag.delete(r - c);
      anti.delete(r + c);
    }
  };
  place(0);
  return out;
}

function totalNQueens(n) {
  const full = (1 << n) - 1;
  const count = (cols, left, right) => {
    if (cols === full) return 1;
    let total = 0;
    for (let free = ~(cols | left | right) & full; free; free &= free - 1) {
      const bit = free & -free;
      total += count(cols | bit, ((left | bit) << 1) & full, (right | bit) >> 1);
    }
    return total;
  };
  return count(0, 0, 0);
}

assert.deepEqual(solveNQueens(4), [['.Q..', '...Q', 'Q...', '..Q.'], ['..Q.', 'Q...', '...Q', '.Q..']]);
assert.equal(totalNQueens(8), 92);
assert.equal(solveNQueens(8).length, 92);
assert.equal(totalNQueens(12), 14200);
assert.equal(totalNQueens(3), 0);
console.log('all checks passed');
