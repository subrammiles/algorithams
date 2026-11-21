/*
 * Fractional knapsack, assign mice to holes, policemen catch thieves (full story in F_ClassicGreedy.java).
 *   fractionalKnapsack     best value/weight ratio first, split the last item (0/1 knapsack needs DP!).
 *   assignMiceToHoles      sort both, pair in order, answer = max |mouse - hole|.
 *   policemenCatchThieves  two pointers over P and T positions.
 */
const assert = require('node:assert/strict');

function fractionalKnapsack(values, weights, capacity) {
  const items = values.map((v, i) => [v, weights[i]]).sort((a, b) => b[0] / b[1] - a[0] / a[1]);
  let total = 0;
  let left = capacity;
  for (const [v, w] of items) {
    if (left === 0) break;
    const take = Math.min(left, w);
    total += (v * take) / w;
    left -= take;
  }
  return total;
}

function assignMiceToHoles(mice, holes) {
  const m = [...mice].sort((a, b) => a - b);
  const h = [...holes].sort((a, b) => a - b);
  return Math.max(...m.map((x, i) => Math.abs(x - h[i])));
}

function policemenCatchThieves(line, k) {
  const police = [];
  const thieves = [];
  [...line].forEach((c, i) => (c === 'P' ? police : thieves).push(i));
  let caught = 0;
  let p = 0;
  let t = 0;
  while (p < police.length && t < thieves.length) {
    if (Math.abs(police[p] - thieves[t]) <= k) {
      caught++;
      p++;
      t++;
    } else if (thieves[t] < police[p]) {
      t++;
    } else {
      p++;
    }
  }
  return caught;
}

assert.equal(fractionalKnapsack([60, 100, 120], [10, 20, 30], 50), 240);
assert.equal(fractionalKnapsack([10, 20], [1, 2], 10), 30);

assert.equal(assignMiceToHoles([4, -4, 2], [4, 0, 5]), 4);
assert.equal(assignMiceToHoles([-10, -79, -79, 67, 93, -85, -28, -94], [-2, 9, 69, 25, -31, 23, 50, 78]), 102);

assert.equal(policemenCatchThieves('PTTPT', 1), 2);
assert.equal(policemenCatchThieves('TTPPTP', 2), 3);
assert.equal(policemenCatchThieves('PTPTTP', 3), 3);
console.log('all checks passed');
