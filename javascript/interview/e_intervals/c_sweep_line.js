/*
 * Sweep line (full explanations in the Java version).
 *   getSkyline            events (x, -h) start / (x, h) end; max-heap + lazy deletion of heights.
 *   intervalIntersection  two pointers; advance whichever interval ends first.
 *   employeeFreeTime      flatten, sort, merge; gaps are the free time.
 *   carPooling            difference array over sorted event times.
 */
const assert = require('node:assert/strict');
const { Heap } = require('../../lib/heap');

function getSkyline(buildings) {
  const events = [];
  for (const [l, r, h] of buildings) events.push([l, -h], [r, h]);
  events.sort((p, q) => p[0] - q[0] || p[1] - q[1]);
  const heap = new Heap((a, b) => b - a);
  heap.push(0); // ground
  const toRemove = new Map();
  const out = [];
  let prev = 0;
  for (const [x, h] of events) {
    if (h < 0) heap.push(-h);
    else toRemove.set(h, (toRemove.get(h) ?? 0) + 1);
    while (toRemove.get(heap.peek())) {
      toRemove.set(heap.peek(), toRemove.get(heap.peek()) - 1);
      heap.pop();
    }
    const cur = heap.peek();
    if (cur !== prev) {
      out.push([x, cur]);
      prev = cur;
    }
  }
  return out;
}

function intervalIntersection(a, b) {
  const out = [];
  let i = 0;
  let j = 0;
  while (i < a.length && j < b.length) {
    const lo = Math.max(a[i][0], b[j][0]);
    const hi = Math.min(a[i][1], b[j][1]);
    if (lo <= hi) out.push([lo, hi]);
    if (a[i][1] < b[j][1]) i++;
    else j++;
  }
  return out;
}

function employeeFreeTime(schedules) {
  const all = schedules.flat().sort((p, q) => p[0] - q[0]);
  const free = [];
  let end = all[0][1];
  for (const [s, e] of all) {
    if (s > end) free.push([end, s]);
    end = Math.max(end, e);
  }
  return free;
}

function carPooling(trips, capacity) {
  const delta = new Map();
  for (const [p, from, to] of trips) {
    delta.set(from, (delta.get(from) ?? 0) + p);
    delta.set(to, (delta.get(to) ?? 0) - p);
  }
  let load = 0;
  for (const t of [...delta.keys()].sort((a, b) => a - b)) {
    load += delta.get(t);
    if (load > capacity) return false;
  }
  return true;
}

const city = [[2, 9, 10], [3, 7, 15], [5, 12, 12], [15, 20, 10], [19, 24, 8]];
assert.deepEqual(getSkyline(city), [[2, 10], [3, 15], [7, 12], [12, 0], [15, 10], [20, 8], [24, 0]]);
assert.deepEqual(getSkyline([[0, 2, 3], [2, 5, 3]]), [[0, 3], [5, 0]]);
assert.deepEqual(getSkyline([[1, 2, 1], [1, 2, 2], [1, 2, 3]]), [[1, 3], [2, 0]]);

const a = [[0, 2], [5, 10], [13, 23], [24, 25]];
const b = [[1, 5], [8, 12], [15, 24], [25, 26]];
assert.deepEqual(intervalIntersection(a, b), [[1, 2], [5, 5], [8, 10], [15, 23], [24, 24], [25, 25]]);

assert.deepEqual(employeeFreeTime([[[1, 2], [5, 6]], [[1, 3]], [[4, 10]]]), [[3, 4]]);
assert.deepEqual(employeeFreeTime([[[1, 3], [6, 7]], [[2, 4]], [[2, 5], [9, 12]]]), [[5, 6], [7, 9]]);

const trips = [[2, 1, 5], [3, 3, 7]];
assert.equal(carPooling(trips, 4), false);
assert.equal(carPooling(trips, 5), true);
assert.equal(carPooling([[3, 2, 7], [3, 7, 9]], 3), true);
console.log('all checks passed');
