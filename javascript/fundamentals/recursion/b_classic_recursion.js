/*
 * Classic recursion: Tower of Hanoi (2^n - 1 moves), Josephus, binary strings without "11",
 * phone letter combinations, generate parentheses.
 */
const assert = require('node:assert/strict');

function hanoi(n, from = 'A', to = 'C', via = 'B', moves = []) {
  if (n === 0) return moves;
  hanoi(n - 1, from, via, to, moves);
  moves.push(`${from}->${to}`);
  hanoi(n - 1, via, to, from, moves);
  return moves;
}

const josephusZero = (n, k) => (n === 1 ? 0 : (josephusZero(n - 1, k) + k) % n);
const josephus = (n, k) => josephusZero(n, k) + 1;

function binaryStrings(n, cur = '') {
  if (cur.length === n) return [cur];
  const out = binaryStrings(n, `${cur}0`);
  if (cur === '' || cur.endsWith('0')) out.push(...binaryStrings(n, `${cur}1`));
  return out;
}

const KEYPAD = { 2: 'abc', 3: 'def', 4: 'ghi', 5: 'jkl', 6: 'mno', 7: 'pqrs', 8: 'tuv', 9: 'wxyz' };

function letterCombinations(digits) {
  if (!digits) return [];
  const out = [];
  const go = (i, cur) => {
    if (i === digits.length) return out.push(cur);
    for (const ch of KEYPAD[digits[i]]) go(i + 1, cur + ch);
  };
  go(0, '');
  return out;
}

function generateParentheses(n) {
  const out = [];
  const go = (cur, open, close) => {
    if (cur.length === 2 * n) return out.push(cur);
    if (open < n) go(`${cur}(`, open + 1, close);
    if (close < open) go(`${cur})`, open, close + 1);
  };
  go('', 0, 0);
  return out;
}

assert.deepEqual(hanoi(2), ['A->B', 'A->C', 'B->C']);
assert.equal(hanoi(10).length, 1023);
assert.equal(josephus(7, 3), 4);
assert.equal(josephus(5, 2), 3);
assert.deepEqual(binaryStrings(3), ['000', '001', '010', '100', '101']);
assert.deepEqual(letterCombinations('23'), ['ad', 'ae', 'af', 'bd', 'be', 'bf', 'cd', 'ce', 'cf']);
assert.deepEqual(letterCombinations(''), []);
assert.deepEqual(generateParentheses(3), ['((()))', '(()())', '(())()', '()(())', '()()()']);
console.log('all checks passed');
