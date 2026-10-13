/*
 * Sliding window maximum with a monotonic deque of indices. O(n).
 * A plain array + head pointer acts as the deque (shift() would be O(n)).
 */
const assert = require('node:assert/strict');

function maxSlidingWindow(nums, k) {
  const dq = [];
  let head = 0;
  const result = [];
  nums.forEach((x, i) => {
    if (head < dq.length && dq[head] <= i - k) head++;
    while (dq.length > head && nums[dq[dq.length - 1]] <= x) dq.pop();
    dq.push(i);
    if (i >= k - 1) result.push(nums[dq[head]]);
  });
  return result;
}

assert.deepEqual(maxSlidingWindow([1, 3, -1, -3, 5, 3, 6, 7], 3), [3, 3, 5, 5, 6, 7]);
assert.deepEqual(maxSlidingWindow([4, 2], 1), [4, 2]);
console.log('all checks passed');
