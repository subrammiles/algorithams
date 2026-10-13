/*
 * String greedy: remove K digits (monotonic stack), reorganize string (max-heap), largest number.
 */
const assert = require('node:assert/strict');
const { Heap } = require('../../lib/heap');

function removeKdigits(num, k) {
  const stack = [];
  for (const ch of num) {
    while (k > 0 && stack.length && stack[stack.length - 1] > ch) {
      stack.pop();
      k--;
    }
    stack.push(ch);
  }
  stack.length -= k; // any removals left come off the end
  return stack.join('').replace(/^0+/, '') || '0';
}

function reorganizeString(s) {
  const count = new Map();
  for (const ch of s) count.set(ch, (count.get(ch) ?? 0) + 1);
  const heap = new Heap((a, b) => b[1] - a[1] || (a[0] < b[0] ? -1 : 1));
  for (const entry of count) heap.push(entry);
  let out = '';
  let held = null;
  while (heap.size) {
    const top = heap.pop();
    out += top[0];
    if (held) heap.push(held);
    held = top[1] > 1 ? [top[0], top[1] - 1] : null;
  }
  return out.length === s.length ? out : '';
}

function largestNumber(nums) {
  const parts = nums.map(String).sort((a, b) => (b + a > a + b ? 1 : b + a < a + b ? -1 : 0)); // plain string order, not locale
  return parts[0] === '0' ? '0' : parts.join('');
}

assert.equal(removeKdigits('1432219', 3), '1219');
assert.equal(removeKdigits('10200', 1), '200');
assert.equal(removeKdigits('10', 2), '0');
const r = reorganizeString('aaabbc');
assert.equal([...r].sort().join(''), 'aaabbc');
assert.ok([...r].every((ch, i) => i === 0 || ch !== r[i - 1]));
assert.equal(reorganizeString('aaab'), '');
assert.equal(largestNumber([3, 30, 34, 5, 9]), '9534330');
assert.equal(largestNumber([0, 0]), '0');
console.log('all checks passed');
