/*
 * Minimum meeting rooms: heap of end times, or sweep line over sorted starts/ends. O(n log n).
 */
const assert = require('node:assert/strict');
const { Heap } = require('../../lib/heap');

function minRoomsHeap(meetings) {
  const ends = new Heap();
  for (const [s, e] of [...meetings].sort((a, b) => a[0] - b[0])) {
    if (ends.size && ends.peek() <= s) ends.pop();
    ends.push(e);
  }
  return ends.size;
}

function minRoomsSweep(meetings) {
  const starts = meetings.map((m) => m[0]).sort((a, b) => a - b);
  const ends = meetings.map((m) => m[1]).sort((a, b) => a - b);
  let rooms = 0;
  let best = 0;
  let e = 0;
  for (const s of starts) {
    while (ends[e] <= s) {
      e++;
      rooms--;
    }
    best = Math.max(best, ++rooms);
  }
  return best;
}

const m = [[0, 30], [5, 10], [15, 20]];
assert.equal(minRoomsHeap(m), 2);
assert.equal(minRoomsSweep(m), 2);
const m2 = [[1, 5], [2, 6], [3, 7], [7, 8]];
assert.equal(minRoomsHeap(m2), 3);
assert.equal(minRoomsSweep(m2), 3);
console.log('all checks passed');
