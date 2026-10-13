/*
 * Interval greedy (pick the earliest end): activity selection, min arrows, partition labels,
 * minimum railway platforms.
 */
const assert = require('node:assert/strict');

function maxActivities(start, end) {
  const order = start.map((s, i) => [s, end[i]]).sort((a, b) => a[1] - b[1]);
  let count = 0;
  let lastEnd = -Infinity;
  for (const [s, e] of order) {
    if (s >= lastEnd) {
      count++;
      lastEnd = e;
    }
  }
  return count;
}

function findMinArrowShots(points) {
  let arrows = 0;
  let arrowAt = -Infinity;
  for (const [s, e] of [...points].sort((a, b) => a[1] - b[1])) {
    if (s > arrowAt) {
      arrows++;
      arrowAt = e;
    }
  }
  return arrows;
}

function partitionLabels(s) {
  const last = {};
  [...s].forEach((ch, i) => (last[ch] = i));
  const sizes = [];
  let start = 0;
  let end = 0;
  [...s].forEach((ch, i) => {
    end = Math.max(end, last[ch]);
    if (i === end) {
      sizes.push(end - start + 1);
      start = i + 1;
    }
  });
  return sizes;
}

function minPlatforms(arrivals, departures) {
  const arr = [...arrivals].sort((a, b) => a - b);
  const dep = [...departures].sort((a, b) => a - b);
  let platforms = 0;
  let best = 0;
  let d = 0;
  for (const a of arr) {
    while (dep[d] < a) {
      d++;
      platforms--;
    }
    best = Math.max(best, ++platforms);
  }
  return best;
}

assert.equal(maxActivities([1, 3, 0, 5, 8, 5], [2, 4, 6, 7, 9, 9]), 4);
assert.equal(findMinArrowShots([[10, 16], [2, 8], [1, 6], [7, 12]]), 2);
assert.equal(findMinArrowShots([[1, 2], [3, 4], [5, 6], [7, 8]]), 4);
assert.equal(findMinArrowShots([[1, 2], [2, 3], [3, 4], [4, 5]]), 2);
assert.deepEqual(partitionLabels('ababcbacadefegdehijhklij'), [9, 7, 8]);
assert.equal(minPlatforms([900, 940, 950, 1100, 1500, 1800], [910, 1200, 1120, 1130, 1900, 2000]), 3);
console.log('all checks passed');
