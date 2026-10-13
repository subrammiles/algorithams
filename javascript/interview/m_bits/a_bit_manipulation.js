/*
 * Bit tricks. JS bitwise operators work on 32-bit signed ints; >>> 0 converts to unsigned.
 */
const assert = require('node:assert/strict');

const isPowerOfTwo = (x) => x > 0 && (x & (x - 1)) === 0;
const singleNumber = (nums) => nums.reduce((a, b) => a ^ b, 0);

function singleNumberThrice(nums) {
  let result = 0;
  for (let bit = 0; bit < 32; bit++) {
    const count = nums.reduce((s, n) => s + ((n >> bit) & 1), 0);
    if (count % 3) result |= 1 << bit; // |= keeps it a signed 32-bit int
  }
  return result;
}

function twoSingleNumbers(nums) {
  const xor = singleNumber(nums);
  const diff = xor & -xor;
  const a = singleNumber(nums.filter((n) => n & diff));
  return [a, xor ^ a].sort((x, y) => x - y);
}

function countBits(n) {
  const bits = new Array(n + 1).fill(0);
  for (let i = 1; i <= n; i++) bits[i] = bits[i & (i - 1)] + 1;
  return bits;
}

function reverseBits(x) {
  let r = 0;
  for (let i = 0; i < 32; i++) {
    r = (r << 1) | (x & 1);
    x >>>= 1;
  }
  return r >>> 0;
}

function add(a, b) {
  while (b !== 0) {
    const carry = (a & b) << 1;
    a ^= b;
    b = carry;
  }
  return a;
}

assert.ok(isPowerOfTwo(64));
assert.equal(isPowerOfTwo(6), false);
assert.equal(singleNumber([4, 1, 2, 1, 2]), 4);
assert.equal(singleNumberThrice([0, 1, 0, 1, 0, 1, -99]), -99);
assert.deepEqual(twoSingleNumbers([1, 2, 1, 3, 2, 5]), [3, 5]);
assert.deepEqual(countBits(5), [0, 1, 1, 2, 1, 2]);
assert.equal(reverseBits(0b00000010100101000001111010011100), 0b00111001011110000010100101000000);
assert.equal(add(15, -7), 8);
console.log('all checks passed');
