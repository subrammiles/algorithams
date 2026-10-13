/*
 * Intervals: merge (sort by start), insert (three phases), min removals (sort by end, greedy).
 */
const assert = require('node:assert/strict');

function merge(intervals) {
  const merged = [];
  for (const [s, e] of [...intervals].sort((a, b) => a[0] - b[0])) {
    const last = merged[merged.length - 1];
    if (!last || last[1] < s) merged.push([s, e]);
    else last[1] = Math.max(last[1], e);
  }
  return merged;
}

function insert(intervals, [start, end]) {
  const out = [];
  let i = 0;
  while (i < intervals.length && intervals[i][1] < start) out.push(intervals[i++]);
  while (i < intervals.length && intervals[i][0] <= end) {
    start = Math.min(start, intervals[i][0]);
    end = Math.max(end, intervals[i++][1]);
  }
  return [...out, [start, end], ...intervals.slice(i)];
}

function eraseOverlapIntervals(intervals) {
  let removed = 0;
  let end = -Infinity;
  for (const [s, e] of [...intervals].sort((a, b) => a[1] - b[1])) {
    if (s >= end) end = e;
    else removed++;
  }
  return removed;
}

assert.deepEqual(merge([[1, 3], [8, 10], [2, 6], [15, 18]]), [[1, 6], [8, 10], [15, 18]]);
assert.deepEqual(merge([[1, 4], [4, 5]]), [[1, 5]]);
assert.deepEqual(insert([[1, 2], [3, 5], [6, 7], [8, 10], [12, 16]], [4, 8]), [[1, 2], [3, 10], [12, 16]]);
assert.equal(eraseOverlapIntervals([[1, 2], [2, 3], [3, 4], [1, 3]]), 1);
console.log('all checks passed');
