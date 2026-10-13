/*
 * Monotonic stack: next greater (circular), daily temperatures, stock span. O(n) each.
 */
const assert = require('node:assert/strict');

function nextGreaterCircular(nums) {
  const n = nums.length;
  const result = new Array(n).fill(-1);
  const stack = [];
  for (let i = 0; i < 2 * n; i++) {
    const x = nums[i % n];
    while (stack.length && nums[stack[stack.length - 1]] < x) result[stack.pop()] = x;
    if (i < n) stack.push(i);
  }
  return result;
}

function dailyTemperatures(t) {
  const wait = new Array(t.length).fill(0);
  const stack = [];
  t.forEach((temp, i) => {
    while (stack.length && t[stack[stack.length - 1]] < temp) {
      const j = stack.pop();
      wait[j] = i - j;
    }
    stack.push(i);
  });
  return wait;
}

function stockSpan(prices) {
  const stack = [];
  return prices.map((p, i) => {
    while (stack.length && prices[stack[stack.length - 1]] <= p) stack.pop();
    const span = stack.length ? i - stack[stack.length - 1] : i + 1;
    stack.push(i);
    return span;
  });
}

assert.deepEqual(nextGreaterCircular([1, 2, 1]), [2, -1, 2]);
assert.deepEqual(dailyTemperatures([73, 74, 75, 71, 69, 72, 76, 73]), [1, 1, 4, 2, 1, 1, 0, 0]);
assert.deepEqual(stockSpan([100, 80, 60, 70, 60, 75, 85]), [1, 1, 1, 2, 1, 4, 6]);
console.log('all checks passed');
