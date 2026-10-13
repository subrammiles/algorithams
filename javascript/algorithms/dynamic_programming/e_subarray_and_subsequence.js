/*
 * "Ending at i" DP: Kadane, max product subarray, longest common substring,
 * max-sum increasing subsequence, longest bitonic subsequence.
 */
const assert = require('node:assert/strict');

function maxSubarray(a) {
  let best = a[0];
  let ending = a[0];
  for (const x of a.slice(1)) {
    ending = Math.max(x, ending + x);
    best = Math.max(best, ending);
  }
  return best;
}

function maxProduct(a) {
  let best = a[0];
  let hi = a[0];
  let lo = a[0];
  for (const x of a.slice(1)) {
    [hi, lo] = [Math.max(x, hi * x, lo * x), Math.min(x, hi * x, lo * x)];
    best = Math.max(best, hi);
  }
  return best;
}

function longestCommonSubstring(a, b) {
  let prev = new Array(b.length + 1).fill(0);
  let best = 0;
  for (let i = 1; i <= a.length; i++) {
    const cur = new Array(b.length + 1).fill(0);
    for (let j = 1; j <= b.length; j++) {
      if (a[i - 1] === b[j - 1]) best = Math.max(best, (cur[j] = prev[j - 1] + 1));
    }
    prev = cur;
  }
  return best;
}

function maxSumIncreasingSubsequence(a) {
  const total = [...a];
  for (let i = 0; i < a.length; i++) for (let j = 0; j < i; j++) if (a[j] < a[i]) total[i] = Math.max(total[i], total[j] + a[i]);
  return Math.max(...total);
}

function longestBitonic(a) {
  const n = a.length;
  const inc = new Array(n).fill(1);
  const dec = new Array(n).fill(1);
  for (let i = 0; i < n; i++) for (let j = 0; j < i; j++) if (a[j] < a[i]) inc[i] = Math.max(inc[i], inc[j] + 1);
  for (let i = n - 1; i >= 0; i--) for (let j = n - 1; j > i; j--) if (a[j] < a[i]) dec[i] = Math.max(dec[i], dec[j] + 1);
  return Math.max(...inc.map((x, i) => x + dec[i] - 1));
}

assert.equal(maxSubarray([-2, 1, -3, 4, -1, 2, 1, -5, 4]), 6);
assert.equal(maxProduct([2, 3, -2, 4]), 6);
assert.equal(maxProduct([-2, 0, -1]), 0);
assert.equal(maxProduct([-2, 3, -4]), 24);
assert.equal(longestCommonSubstring('abcdxyz', 'xyzabcd'), 4);
assert.equal(maxSumIncreasingSubsequence([1, 101, 2, 3, 100, 4, 5]), 106);
assert.equal(longestBitonic([1, 11, 2, 10, 4, 5, 2, 1]), 6);
console.log('all checks passed');
