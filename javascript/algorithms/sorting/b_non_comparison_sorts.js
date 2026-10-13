/*
 * Counting sort (stable, negatives via offset), LSD radix sort, bucket sort for [0, 1).
 */
const assert = require('node:assert/strict');

function countingSort(a) {
  if (!a.length) return [];
  const min = Math.min(...a);
  const count = new Array(Math.max(...a) - min + 1).fill(0);
  for (const x of a) count[x - min]++;
  for (let i = 1; i < count.length; i++) count[i] += count[i - 1];
  const out = new Array(a.length);
  for (let i = a.length - 1; i >= 0; i--) out[--count[a[i] - min]] = a[i]; // backwards = stable
  return out;
}

function radixSort(input) {
  let a = [...input];
  const max = Math.max(0, ...a);
  for (let exp = 1; Math.floor(max / exp) > 0; exp *= 10) {
    const buckets = Array.from({ length: 10 }, () => []);
    for (const x of a) buckets[Math.floor(x / exp) % 10].push(x);
    a = buckets.flat();
  }
  return a;
}

function bucketSort(a) {
  const buckets = Array.from({ length: a.length }, () => []);
  for (const x of a) buckets[Math.floor(x * a.length)].push(x);
  return buckets.flatMap((b) => b.sort((x, y) => x - y));
}

assert.deepEqual(countingSort([4, -2, 2, 8, 3, 3, -2, 1]), [-2, -2, 1, 2, 3, 3, 4, 8]);
assert.deepEqual(countingSort([]), []);
assert.deepEqual(radixSort([170, 45, 75, 90, 802, 24, 2, 66]), [2, 24, 45, 66, 75, 90, 170, 802]);
const big = Array.from({ length: 2000 }, () => Math.floor(Math.random() * 2 ** 31));
assert.deepEqual(radixSort(big), [...big].sort((x, y) => x - y));
const d = [0.897, 0.565, 0.656, 0.1234, 0.665, 0.3434];
assert.deepEqual(bucketSort(d), [...d].sort((x, y) => x - y));
console.log('all checks passed');
