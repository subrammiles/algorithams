/*
 * Interval / partition DP: matrix chain multiplication, palindrome partitioning min cuts, egg drop.
 */
const assert = require('node:assert/strict');

function matrixChainOrder(dims) {
  const n = dims.length - 1;
  const cost = Array.from({ length: n + 1 }, () => new Array(n + 1).fill(0));
  for (let len = 2; len <= n; len++) {
    for (let i = 1; i + len - 1 <= n; i++) {
      const j = i + len - 1;
      cost[i][j] = Infinity;
      for (let k = i; k < j; k++) cost[i][j] = Math.min(cost[i][j], cost[i][k] + cost[k + 1][j] + dims[i - 1] * dims[k] * dims[j]);
    }
  }
  return cost[1][n];
}

function minCutPalindromePartition(s) {
  const n = s.length;
  const pal = Array.from({ length: n }, () => new Array(n).fill(false));
  const cuts = Array.from({ length: n }, (_, j) => j);
  for (let j = 0; j < n; j++) {
    for (let i = 0; i <= j; i++) {
      if (s[i] === s[j] && (j - i < 2 || pal[i + 1][j - 1])) {
        pal[i][j] = true;
        cuts[j] = i === 0 ? 0 : Math.min(cuts[j], cuts[i - 1] + 1);
      }
    }
  }
  return cuts[n - 1];
}

function eggDrop(eggs, floors) {
  const covered = new Array(eggs + 1).fill(0);
  let moves = 0;
  while (covered[eggs] < floors) {
    moves++;
    for (let k = eggs; k >= 1; k--) covered[k] = covered[k - 1] + covered[k] + 1;
  }
  return moves;
}

assert.equal(matrixChainOrder([40, 20, 30, 10, 30]), 26000);
assert.equal(matrixChainOrder([10, 20, 30, 40, 30]), 30000);
assert.equal(minCutPalindromePartition('aab'), 1);
assert.equal(minCutPalindromePartition('a'), 0);
assert.equal(minCutPalindromePartition('ab'), 1);
assert.equal(eggDrop(2, 100), 14);
assert.equal(eggDrop(1, 10), 10);
assert.equal(eggDrop(3, 14), 4);
console.log('all checks passed');
