/*
 * Harder monotonic-stack problems (full explanations in the Java version).
 *   sumSubarrayMins          contribution technique: previous strictly smaller, next smaller-or-equal.
 *   removeDuplicateLetters   greedy increasing stack; pop a bigger top if it appears again later.
 *   find132pattern           right-to-left decreasing stack; popped values are candidate "2"s.
 */
const assert = require('node:assert/strict');

const MOD = 1_000_000_007;

function sumSubarrayMins(a) {
  const n = a.length;
  const left = new Array(n);
  const right = new Array(n);
  let stack = [];
  for (let i = 0; i < n; i++) {
    while (stack.length && a[stack.at(-1)] >= a[i]) stack.pop();
    left[i] = stack.length ? i - stack.at(-1) : i + 1;
    stack.push(i);
  }
  stack = [];
  for (let i = n - 1; i >= 0; i--) {
    while (stack.length && a[stack.at(-1)] > a[i]) stack.pop();
    right[i] = stack.length ? stack.at(-1) - i : n - i;
    stack.push(i);
  }
  let total = 0;
  // a[i] * left * right fits in 2^53 for a[i] <= 3e4 and n <= 3e4 (LeetCode limits)
  for (let i = 0; i < n; i++) total = (total + ((a[i] * left[i]) % MOD) * right[i]) % MOD;
  return total;
}

function removeDuplicateLetters(s) {
  const last = new Map();
  [...s].forEach((c, i) => last.set(c, i));
  const stack = [];
  const inStack = new Set();
  [...s].forEach((c, i) => {
    if (inStack.has(c)) return;
    while (stack.length && stack.at(-1) > c && last.get(stack.at(-1)) > i) inStack.delete(stack.pop());
    stack.push(c);
    inStack.add(c);
  });
  return stack.join('');
}

function find132pattern(a) {
  const stack = [];
  let third = -Infinity;
  for (let i = a.length - 1; i >= 0; i--) {
    if (a[i] < third) return true;
    while (stack.length && stack.at(-1) < a[i]) third = stack.pop();
    stack.push(a[i]);
  }
  return false;
}

assert.equal(sumSubarrayMins([3, 1, 2, 4]), 17);
assert.equal(sumSubarrayMins([11, 81, 94, 43, 3]), 444);
assert.equal(sumSubarrayMins([2, 2, 2]), 12);

assert.equal(removeDuplicateLetters('bcabc'), 'abc');
assert.equal(removeDuplicateLetters('cbacdcbc'), 'acdb');

assert.equal(find132pattern([3, 1, 4, 2]), true);
assert.equal(find132pattern([1, 2, 3, 4]), false);
assert.equal(find132pattern([-1, 3, 2, 0]), true);
console.log('all checks passed');
