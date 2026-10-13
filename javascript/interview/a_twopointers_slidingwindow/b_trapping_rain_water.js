/*
 * Trapping rain water: water at i = min(maxLeft, maxRight) - h[i].
 * Two pointers move the side with the smaller max. O(n) time, O(1) space.
 */
const assert = require('node:assert/strict');

function trapPrefix(h) {
  const n = h.length;
  if (n === 0) return 0;
  const left = [...h];
  const right = [...h];
  for (let i = 1; i < n; i++) left[i] = Math.max(left[i - 1], h[i]);
  for (let i = n - 2; i >= 0; i--) right[i] = Math.max(right[i + 1], h[i]);
  return h.reduce((water, x, i) => water + Math.min(left[i], right[i]) - x, 0);
}

function trapTwoPointers(h) {
  let lo = 0;
  let hi = h.length - 1;
  let leftMax = 0;
  let rightMax = 0;
  let water = 0;
  while (lo < hi) {
    if (h[lo] < h[hi]) {
      leftMax = Math.max(leftMax, h[lo]);
      water += leftMax - h[lo++];
    } else {
      rightMax = Math.max(rightMax, h[hi]);
      water += rightMax - h[hi--];
    }
  }
  return water;
}

const h = [0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1];
assert.equal(trapPrefix(h), 6);
assert.equal(trapTwoPointers(h), 6);
assert.equal(trapTwoPointers([4, 2, 0, 3, 2, 5]), 9);
assert.equal(trapTwoPointers([]), 0);
console.log('all checks passed');
