/*
 * Infix -> postfix (shunting-yard) and postfix evaluation. Full story in C_InfixToPostfix.java.
 * operand -> output; '(' push; ')' pop until '('; operator: pop while the top binds at least as
 * tightly (higher precedence, or equal and the new operator is left-associative), then push.
 */
const assert = require('node:assert/strict');

const PRECEDENCE = { '^': 3, '*': 2, '/': 2, '+': 1, '-': 1 };

function infixToPostfix(infix) {
  let out = '';
  const ops = [];
  for (const c of infix) {
    if (/[a-z0-9]/i.test(c)) {
      out += c;
    } else if (c === '(') {
      ops.push(c);
    } else if (c === ')') {
      while (ops.at(-1) !== '(') out += ops.pop();
      ops.pop();
    } else {
      while (
        ops.length &&
        ops.at(-1) in PRECEDENCE &&
        (PRECEDENCE[ops.at(-1)] > PRECEDENCE[c] || (PRECEDENCE[ops.at(-1)] === PRECEDENCE[c] && c !== '^'))
      ) {
        out += ops.pop();
      }
      ops.push(c);
    }
  }
  while (ops.length) out += ops.pop();
  return out;
}

// single-digit operands, integer arithmetic
function evaluatePostfix(postfix) {
  const stack = [];
  for (const c of postfix) {
    if (/\d/.test(c)) {
      stack.push(Number(c));
      continue;
    }
    const b = stack.pop();
    const a = stack.pop();
    const apply = { '+': a + b, '-': a - b, '*': a * b, '/': Math.trunc(a / b), '^': a ** b };
    stack.push(apply[c]);
  }
  return stack.pop();
}

assert.equal(infixToPostfix('a+b*c'), 'abc*+');
assert.equal(infixToPostfix('(a+b)*c'), 'ab+c*');
assert.equal(infixToPostfix('a-b-c'), 'ab-c-');
assert.equal(infixToPostfix('a^b^c'), 'abc^^');
assert.equal(infixToPostfix('a+b*(c^d-e)^(f+g*h)-i'), 'abcd^e-fgh*+^*+i-');

assert.equal(evaluatePostfix('231*+9-'), -4);
assert.equal(evaluatePostfix(infixToPostfix('(1+2)*(3+4)-2^3')), 13);
assert.equal(evaluatePostfix(infixToPostfix('2^3^2')), 512);
console.log('all checks passed');
