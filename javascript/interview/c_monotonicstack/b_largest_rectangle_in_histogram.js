/*
 * Largest rectangle in a histogram (increasing stack, O(n)) and maximal rectangle in a 0/1 matrix.
 */
const assert = require('node:assert/strict');

function largestRectangle(h) {
  const stack = [];
  let best = 0;
  for (let i = 0; i <= h.length; i++) {
    const cur = i === h.length ? 0 : h[i];
    while (stack.length && h[stack[stack.length - 1]] > cur) {
      const height = h[stack.pop()];
      const left = stack.length ? stack[stack.length - 1] : -1;
      best = Math.max(best, height * (i - left - 1));
    }
    stack.push(i);
  }
  return best;
}

function maximalRectangle(matrix) {
  if (!matrix.length) return 0;
  let heights = new Array(matrix[0].length).fill(0);
  let best = 0;
  for (const row of matrix) {
    heights = heights.map((h, c) => (row[c] === '1' ? h + 1 : 0));
    best = Math.max(best, largestRectangle(heights));
  }
  return best;
}

assert.equal(largestRectangle([2, 1, 5, 6, 2, 3]), 10);
assert.equal(largestRectangle([2, 4]), 4);
assert.equal(maximalRectangle(['10100', '10111', '11111', '10010']), 6);
console.log('all checks passed');
