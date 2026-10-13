/*
 * Recursion basics: base case + recursive case. Naive fibonacci O(2^n), memoised O(n),
 * fast power O(log n). BigInt for big factorials / fibonacci (Number loses precision past 2^53).
 */
const assert = require('node:assert/strict');

const factorial = (n) => (n <= 1n ? 1n : n * factorial(n - 1n));
const fibNaive = (n) => (n < 2 ? n : fibNaive(n - 1) + fibNaive(n - 2));

function fibMemo(n, memo = new Map()) {
  if (n < 2) return BigInt(n);
  if (!memo.has(n)) memo.set(n, fibMemo(n - 1, memo) + fibMemo(n - 2, memo));
  return memo.get(n);
}

function fastPower(x, n) {
  if (n < 0) return 1 / fastPower(x, -n);
  if (n === 0) return 1;
  const half = fastPower(x, Math.floor(n / 2));
  return n % 2 === 0 ? half * half : half * half * x;
}

const sumOfDigits = (n) => (n < 10 ? n : (n % 10) + sumOfDigits(Math.floor(n / 10)));
const reverse = (s) => (s === '' ? s : reverse(s.slice(1)) + s[0]);
const isPalindrome = (s, lo = 0, hi = s.length - 1) => lo >= hi || (s[lo] === s[hi] && isPalindrome(s, lo + 1, hi - 1));

assert.equal(factorial(10n), 3628800n);
assert.equal(fibNaive(20), 6765);
assert.equal(fibMemo(50), 12586269025n);
assert.equal(fastPower(2, 10), 1024);
assert.equal(fastPower(2, -2), 0.25);
assert.equal(sumOfDigits(9875), 29);
assert.equal(reverse('hello'), 'olleh');
assert.ok(isPalindrome('racecar'));
assert.equal(isPalindrome('abca'), false);
console.log('all checks passed');
