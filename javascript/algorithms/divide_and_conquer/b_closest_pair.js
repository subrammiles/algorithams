/*
 * Closest pair of points, divide and conquer, O(n log n). Full explanation in B_ClosestPair.java.
 * Sort by x; d = min of both halves; check only the strip |x - midX| < d, kept sorted by y by
 * merging on the way up; each strip point is compared with at most 7 following points.
 */
const assert = require('node:assert/strict');

const dist = (a, b) => Math.hypot(a[0] - b[0], a[1] - b[1]);

function closestPair(points) {
  // p sorted by x -> [closest distance, p sorted by y]
  const solve = (p) => {
    const n = p.length;
    if (n <= 3) {
      let best = Infinity;
      for (let i = 0; i < n; i++) for (let j = i + 1; j < n; j++) best = Math.min(best, dist(p[i], p[j]));
      return [best, [...p].sort((a, b) => a[1] - b[1])];
    }
    const mid = n >> 1;
    const midX = p[mid][0];
    const [dl, left] = solve(p.slice(0, mid));
    const [dr, right] = solve(p.slice(mid));
    let d = Math.min(dl, dr);
    const merged = [];
    let i = 0;
    let j = 0;
    while (i < left.length || j < right.length) {
      if (j >= right.length || (i < left.length && left[i][1] <= right[j][1])) merged.push(left[i++]);
      else merged.push(right[j++]);
    }
    const strip = merged.filter((q) => Math.abs(q[0] - midX) < d);
    for (let a = 0; a < strip.length; a++) {
      for (let b = a + 1; b < strip.length && strip[b][1] - strip[a][1] < d; b++) d = Math.min(d, dist(strip[a], strip[b]));
    }
    return [d, merged];
  };
  return solve([...points].sort((a, b) => a[0] - b[0]))[0];
}

function bruteForce(p) {
  let best = Infinity;
  for (let i = 0; i < p.length; i++) for (let j = i + 1; j < p.length; j++) best = Math.min(best, dist(p[i], p[j]));
  return best;
}

const pts = [[2, 3], [12, 30], [40, 50], [5, 1], [12, 10], [3, 4]];
assert.ok(Math.abs(closestPair(pts) - Math.SQRT2) < 1e-9);
assert.equal(closestPair([[1, 1], [5, 5], [1, 1]]), 0);
assert.equal(closestPair([[0, 0], [3, 4]]), 5);

let seed = 3;
const rand = () => (seed = (seed * 16807) % 2147483647) / 2147483647;
for (let trial = 0; trial < 20; trial++) {
  const p = Array.from({ length: 300 }, () => [rand() * 1000, rand() * 1000]);
  assert.ok(Math.abs(closestPair(p) - bruteForce(p)) < 1e-9);
}
console.log('all checks passed');
