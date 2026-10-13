/*
 * Evaluate Division with weighted union-find: weight(x) = x / parent(x).
 */
const assert = require('node:assert/strict');

function calcEquation(equations, values, queries) {
  const parent = new Map();
  const weight = new Map();
  const find = (x) => {
    const p = parent.get(x);
    if (p !== x) {
      const root = find(p);
      weight.set(x, weight.get(x) * weight.get(p));
      parent.set(x, root);
    }
    return parent.get(x);
  };
  equations.forEach(([a, b], i) => {
    for (const x of [a, b]) {
      if (!parent.has(x)) {
        parent.set(x, x);
        weight.set(x, 1);
      }
    }
    const ra = find(a);
    const rb = find(b);
    if (ra !== rb) {
      parent.set(ra, rb);
      weight.set(ra, (values[i] * weight.get(b)) / weight.get(a));
    }
  });
  return queries.map(([a, b]) => (!parent.has(a) || !parent.has(b) || find(a) !== find(b) ? -1 : weight.get(a) / weight.get(b)));
}

const close = (x, y) => Math.abs(x - y) < 1e-9;
const got = calcEquation([['a', 'b'], ['b', 'c']], [2, 3], [['a', 'c'], ['b', 'a'], ['a', 'e'], ['a', 'a'], ['x', 'x']]);
assert.ok([6, 0.5, -1, 1, -1].every((v, i) => close(v, got[i])), String(got));
const chain = calcEquation([['a', 'b'], ['c', 'd'], ['b', 'c']], [1.5, 2.5, 5], [['a', 'd'], ['d', 'a']]);
assert.ok(close(chain[0], 18.75) && close(chain[1], 1 / 18.75));
console.log('all checks passed');
