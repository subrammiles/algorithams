/*
 * Greedy classics: jump game II, gas station, task scheduler, candy.
 */
const assert = require('node:assert/strict');

function jump(nums) {
  let jumps = 0;
  let currentEnd = 0;
  let farthest = 0;
  for (let i = 0; i < nums.length - 1; i++) {
    farthest = Math.max(farthest, i + nums[i]);
    if (i === currentEnd) {
      jumps++;
      currentEnd = farthest;
    }
  }
  return jumps;
}

function canCompleteCircuit(gas, cost) {
  let total = 0;
  let tank = 0;
  let start = 0;
  for (let i = 0; i < gas.length; i++) {
    total += gas[i] - cost[i];
    tank += gas[i] - cost[i];
    if (tank < 0) {
      start = i + 1;
      tank = 0;
    }
  }
  return total >= 0 ? start : -1;
}

function leastInterval(tasks, n) {
  const freq = new Map();
  for (const t of tasks) freq.set(t, (freq.get(t) ?? 0) + 1);
  const top = Math.max(...freq.values());
  const countTop = [...freq.values()].filter((f) => f === top).length;
  return Math.max(tasks.length, (top - 1) * (n + 1) + countTop);
}

function candy(ratings) {
  const c = new Array(ratings.length).fill(1);
  for (let i = 1; i < ratings.length; i++) if (ratings[i] > ratings[i - 1]) c[i] = c[i - 1] + 1;
  for (let i = ratings.length - 2; i >= 0; i--) if (ratings[i] > ratings[i + 1]) c[i] = Math.max(c[i], c[i + 1] + 1);
  return c.reduce((a, b) => a + b, 0);
}

assert.equal(jump([2, 3, 1, 1, 4]), 2);
assert.equal(canCompleteCircuit([1, 2, 3, 4, 5], [3, 4, 5, 1, 2]), 3);
assert.equal(canCompleteCircuit([2, 3, 4], [3, 4, 3]), -1);
assert.equal(leastInterval([...'AAABBB'], 2), 8);
assert.equal(leastInterval([...'AAABBBCCDD'], 2), 10);
assert.equal(candy([1, 0, 2]), 5);
console.log('all checks passed');
