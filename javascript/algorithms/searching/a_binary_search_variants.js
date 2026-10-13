/*
 * Binary search variants (first index where a condition turns true): lower/upper bound,
 * first/last position, peak element, integer sqrt, row-continuing matrix, ternary search.
 */
const assert = require('node:assert/strict');

function firstTrue(n, cond) {
  let lo = 0;
  let hi = n;
  while (lo < hi) {
    const mid = (lo + hi) >>> 1;
    if (cond(mid)) hi = mid;
    else lo = mid + 1;
  }
  return lo;
}

const lowerBound = (a, x) => firstTrue(a.length, (i) => a[i] >= x);
const upperBound = (a, x) => firstTrue(a.length, (i) => a[i] > x);

function searchRange(a, x) {
  const first = lowerBound(a, x);
  return first === a.length || a[first] !== x ? [-1, -1] : [first, upperBound(a, x) - 1];
}

function findPeakElement(a) {
  let lo = 0;
  let hi = a.length - 1;
  while (lo < hi) {
    const mid = (lo + hi) >>> 1;
    if (a[mid] < a[mid + 1]) lo = mid + 1;
    else hi = mid;
  }
  return lo;
}

const mySqrt = (n) => firstTrue(n + 1, (x) => x * x > n) - 1;

function searchMatrix(m, target) {
  const cols = m[0].length;
  const i = firstTrue(m.length * cols, (k) => m[Math.floor(k / cols)][k % cols] >= target);
  return i < m.length * cols && m[Math.floor(i / cols)][i % cols] === target;
}

function ternarySearchMax(a) {
  let lo = 0;
  let hi = a.length - 1;
  while (hi - lo > 2) {
    const m1 = lo + Math.floor((hi - lo) / 3);
    const m2 = hi - Math.floor((hi - lo) / 3);
    if (a[m1] < a[m2]) lo = m1 + 1;
    else hi = m2 - 1;
  }
  let best = lo;
  for (let i = lo; i <= hi; i++) if (a[i] > a[best]) best = i;
  return best;
}

const a = [1, 2, 2, 2, 3];
assert.equal(lowerBound(a, 2), 1);
assert.equal(upperBound(a, 2), 4);
assert.equal(lowerBound(a, 9), 5);
assert.deepEqual(searchRange([5, 7, 7, 8, 8, 10], 8), [3, 4]);
assert.deepEqual(searchRange([5, 7, 7, 8, 8, 10], 6), [-1, -1]);
const peaks = [1, 2, 1, 3, 5, 6, 4];
const p = findPeakElement(peaks);
assert.ok((p === 0 || peaks[p] > peaks[p - 1]) && (p === peaks.length - 1 || peaks[p] > peaks[p + 1]));
assert.equal(mySqrt(8), 2);
assert.equal(mySqrt(2147395599), 46339);
assert.equal(mySqrt(2 ** 31 - 1), 46340);
const m = [[1, 3, 5, 7], [10, 11, 16, 20], [23, 30, 34, 60]];
assert.ok(searchMatrix(m, 3));
assert.equal(searchMatrix(m, 13), false);
assert.equal(ternarySearchMax([1, 3, 7, 9, 8, 4, 2]), 3);
console.log('all checks passed');
