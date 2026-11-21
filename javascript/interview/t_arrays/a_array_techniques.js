/*
 * In-place array techniques (full explanations in the Java version).
 * productExceptSelf (prefix x suffix, no division), firstMissingPositive (cyclic sort),
 * findDuplicates (sign marking), sortColors (Dutch flag), nextPermutation, rotate (3 reversals),
 * corpFlightBookings (difference array), majorityElementII (Boyer-Moore, two candidates + verify).
 */
const assert = require('node:assert/strict');

function productExceptSelf(nums) {
  const n = nums.length;
  const out = new Array(n);
  let left = 1;
  for (let i = 0; i < n; i++) {
    out[i] = left;
    left *= nums[i];
  }
  let right = 1;
  for (let i = n - 1; i >= 0; i--) {
    out[i] *= right;
    right *= nums[i];
  }
  return out.map((x) => x + 0); // turn -0 into 0 for clean output
}

function firstMissingPositive(input) {
  const nums = [...input];
  const n = nums.length;
  for (let i = 0; i < n; i++) {
    while (nums[i] >= 1 && nums[i] <= n && nums[nums[i] - 1] !== nums[i]) {
      const j = nums[i] - 1;
      [nums[i], nums[j]] = [nums[j], nums[i]];
    }
  }
  for (let i = 0; i < n; i++) if (nums[i] !== i + 1) return i + 1;
  return n + 1;
}

function findDuplicates(input) {
  const nums = [...input];
  const out = [];
  for (let i = 0; i < nums.length; i++) {
    const v = Math.abs(nums[i]);
    if (nums[v - 1] < 0) out.push(v);
    else nums[v - 1] = -nums[v - 1];
  }
  return out;
}

function sortColors(a) {
  let low = 0;
  let mid = 0;
  let high = a.length - 1;
  while (mid <= high) {
    if (a[mid] === 0) {
      [a[low], a[mid]] = [a[mid], a[low]];
      low++;
      mid++;
    } else if (a[mid] === 1) {
      mid++;
    } else {
      [a[mid], a[high]] = [a[high], a[mid]];
      high--;
    }
  }
}

function reverse(a, i, j) {
  for (; i < j; i++, j--) [a[i], a[j]] = [a[j], a[i]];
}

function nextPermutation(a) {
  let i = a.length - 2;
  while (i >= 0 && a[i] >= a[i + 1]) i--;
  if (i >= 0) {
    let j = a.length - 1;
    while (a[j] <= a[i]) j--;
    [a[i], a[j]] = [a[j], a[i]];
  }
  reverse(a, i + 1, a.length - 1);
}

function rotate(a, k) {
  const n = a.length;
  if (n === 0) return;
  k %= n;
  reverse(a, 0, n - 1);
  reverse(a, 0, k - 1);
  reverse(a, k, n - 1);
}

function corpFlightBookings(bookings, n) {
  const diff = new Array(n + 1).fill(0);
  for (const [first, last, seats] of bookings) {
    diff[first - 1] += seats;
    diff[last] -= seats;
  }
  const out = [];
  let running = 0;
  for (let i = 0; i < n; i++) out.push((running += diff[i]));
  return out;
}

function majorityElementII(nums) {
  let [c1, c2, n1, n2] = [0, 1, 0, 0];
  for (const x of nums) {
    if (x === c1) n1++;
    else if (x === c2) n2++;
    else if (n1 === 0) [c1, n1] = [x, 1];
    else if (n2 === 0) [c2, n2] = [x, 1];
    else {
      n1--;
      n2--;
    }
  }
  const count = (c) => nums.filter((x) => x === c).length;
  return [c1, c2].filter((c) => count(c) > Math.floor(nums.length / 3)).sort((a, b) => a - b);
}

assert.deepEqual(productExceptSelf([1, 2, 3, 4]), [24, 12, 8, 6]);
assert.deepEqual(productExceptSelf([-1, 1, 0, -3, 3]), [0, 0, 9, 0, 0]);

assert.equal(firstMissingPositive([3, 4, -1, 1]), 2);
assert.equal(firstMissingPositive([7, 8, 9, 11, 12]), 1);
assert.equal(firstMissingPositive([1, 2, 0]), 3);
assert.equal(firstMissingPositive([1, 1, 2, 2]), 3);

assert.deepEqual(findDuplicates([4, 3, 2, 7, 8, 2, 3, 1]), [2, 3]);

const colors = [2, 0, 2, 1, 1, 0];
sortColors(colors);
assert.deepEqual(colors, [0, 0, 1, 1, 2, 2]);

for (const [perm, next] of [
  [[1, 2, 3], [1, 3, 2]],
  [[3, 2, 1], [1, 2, 3]],
  [[1, 1, 5], [1, 5, 1]],
  [[1, 3, 2], [2, 1, 3]],
]) {
  nextPermutation(perm);
  assert.deepEqual(perm, next);
}

const r = [1, 2, 3, 4, 5, 6, 7];
rotate(r, 3);
assert.deepEqual(r, [5, 6, 7, 1, 2, 3, 4]);
const r2 = [-1, -100, 3, 99];
rotate(r2, 6);
assert.deepEqual(r2, [3, 99, -1, -100]);

assert.deepEqual(corpFlightBookings([[1, 2, 10], [2, 3, 20], [2, 5, 25]], 5), [10, 55, 45, 25, 25]);

assert.deepEqual(majorityElementII([3, 2, 3]), [3]);
assert.deepEqual(majorityElementII([1, 2]), [1, 2]);
assert.deepEqual(majorityElementII([1, 1, 1, 3, 3, 2, 2, 2]), [1, 2]);
console.log('all checks passed');
