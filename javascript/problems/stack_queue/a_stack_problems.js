/*
 * Stack problems: RPN (Math.trunc for division), basic calculator II, decode string,
 * asteroid collision, minimum remove to make parentheses valid.
 */
const assert = require('node:assert/strict');

function evalRPN(tokens) {
  const stack = [];
  const ops = { '+': (a, b) => a + b, '-': (a, b) => a - b, '*': (a, b) => a * b, '/': (a, b) => Math.trunc(a / b) };
  for (const t of tokens) {
    if (t in ops) {
      const b = stack.pop();
      const a = stack.pop();
      stack.push(ops[t](a, b));
    } else stack.push(Number(t));
  }
  return stack.pop();
}

function calculate(s) {
  const stack = [];
  let num = 0;
  let op = '+';
  for (let i = 0; i < s.length; i++) {
    const ch = s[i];
    if (ch >= '0' && ch <= '9') num = num * 10 + Number(ch);
    if ((ch !== ' ' && !(ch >= '0' && ch <= '9')) || i === s.length - 1) {
      if (op === '+') stack.push(num);
      else if (op === '-') stack.push(-num);
      else if (op === '*') stack.push(stack.pop() * num);
      else stack.push(Math.trunc(stack.pop() / num));
      op = ch;
      num = 0;
    }
  }
  return stack.reduce((a, b) => a + b, 0);
}

function decodeString(s) {
  const stack = [];
  let cur = '';
  let k = 0;
  for (const ch of s) {
    if (ch >= '0' && ch <= '9') k = k * 10 + Number(ch);
    else if (ch === '[') {
      stack.push([cur, k]);
      cur = '';
      k = 0;
    } else if (ch === ']') {
      const [prev, times] = stack.pop();
      cur = prev + cur.repeat(times);
    } else cur += ch;
  }
  return cur;
}

function asteroidCollision(asteroids) {
  const stack = [];
  for (const a of asteroids) {
    let alive = true;
    while (alive && a < 0 && stack.length && stack[stack.length - 1] > 0) {
      const top = stack[stack.length - 1];
      if (top < -a) stack.pop();
      else {
        if (top === -a) stack.pop();
        alive = false;
      }
    }
    if (alive) stack.push(a);
  }
  return stack;
}

function minRemoveToMakeValid(s) {
  const chars = [...s];
  const open = [];
  chars.forEach((ch, i) => {
    if (ch === '(') open.push(i);
    else if (ch === ')') {
      if (open.length) open.pop();
      else chars[i] = '';
    }
  });
  for (const i of open) chars[i] = '';
  return chars.join('');
}

assert.equal(evalRPN(['2', '1', '+', '3', '*']), 9);
assert.equal(evalRPN(['4', '13', '5', '/', '+']), 6);
assert.equal(evalRPN(['10', '6', '9', '3', '+', '-11', '*', '/', '*', '17', '+', '5', '+']), 22);
assert.equal(calculate('3+2*2'), 7);
assert.equal(calculate(' 3/2 '), 1);
assert.equal(calculate(' 3+5 / 2 '), 5);
assert.equal(calculate('14-3/2'), 13);
assert.equal(decodeString('3[a]2[bc]'), 'aaabcbc');
assert.equal(decodeString('3[a2[c]]'), 'accaccacc');
assert.equal(decodeString('2[abc]3[cd]ef'), 'abcabccdcdcdef');
assert.deepEqual(asteroidCollision([5, 10, -5]), [5, 10]);
assert.deepEqual(asteroidCollision([8, -8]), []);
assert.deepEqual(asteroidCollision([10, 2, -5]), [10]);
assert.deepEqual(asteroidCollision([-2, -1, 1, 2]), [-2, -1, 1, 2]);
assert.equal(minRemoveToMakeValid('lee(t(c)o)de)'), 'lee(t(c)o)de');
assert.equal(minRemoveToMakeValid('a)b(c)d'), 'ab(c)d');
assert.equal(minRemoveToMakeValid('))(('), '');
console.log('all checks passed');
