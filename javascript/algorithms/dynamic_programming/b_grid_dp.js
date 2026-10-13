/*
 * Grid DP with a single row: unique paths (+ obstacles), minimum path sum, maximal square.
 */
const assert = require('node:assert/strict');

function uniquePaths(m, n) {
  const dp = new Array(n).fill(1);
  for (let r = 1; r < m; r++) for (let c = 1; c < n; c++) dp[c] += dp[c - 1];
  return dp[n - 1];
}

function uniquePathsWithObstacles(grid) {
  const dp = new Array(grid[0].length).fill(0);
  dp[0] = grid[0][0] ? 0 : 1;
  for (const row of grid) {
    row.forEach((cell, c) => {
      if (cell) dp[c] = 0;
      else if (c > 0) dp[c] += dp[c - 1];
    });
  }
  return dp[dp.length - 1];
}

function minPathSum(grid) {
  const dp = new Array(grid[0].length).fill(Infinity);
  dp[0] = 0;
  for (const row of grid) row.forEach((v, c) => (dp[c] = v + (c === 0 ? dp[c] : Math.min(dp[c], dp[c - 1]))));
  return dp[dp.length - 1];
}

function maximalSquare(matrix) {
  const rows = matrix.length;
  const cols = matrix[0].length;
  const side = Array.from({ length: rows + 1 }, () => new Array(cols + 1).fill(0));
  let best = 0;
  for (let r = 1; r <= rows; r++) {
    for (let c = 1; c <= cols; c++) {
      if (matrix[r - 1][c - 1] === '1') {
        side[r][c] = 1 + Math.min(side[r - 1][c - 1], side[r - 1][c], side[r][c - 1]);
        best = Math.max(best, side[r][c]);
      }
    }
  }
  return best * best;
}

assert.equal(uniquePaths(3, 7), 28);
assert.equal(uniquePathsWithObstacles([[0, 0, 0], [0, 1, 0], [0, 0, 0]]), 2);
assert.equal(uniquePathsWithObstacles([[1]]), 0);
assert.equal(minPathSum([[1, 3, 1], [1, 5, 1], [4, 2, 1]]), 7);
assert.equal(maximalSquare(['10100', '10111', '11111', '10010']), 4);
assert.equal(maximalSquare(['0']), 0);
console.log('all checks passed');
