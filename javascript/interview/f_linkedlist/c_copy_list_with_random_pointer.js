/*
 * Copy a list with random pointers: Map old -> new (O(n) space) or interleaving (O(1) extra).
 */
const assert = require('node:assert/strict');

const node = (val) => ({ val, next: null, random: null });

function copyWithMap(head) {
  const copy = new Map([[null, null]]);
  for (let n = head; n; n = n.next) copy.set(n, node(n.val));
  for (let n = head; n; n = n.next) {
    copy.get(n).next = copy.get(n.next);
    copy.get(n).random = copy.get(n.random);
  }
  return copy.get(head);
}

function copyInterleaved(head) {
  for (let n = head; n; n = n.next.next) {
    const c = node(n.val);
    c.next = n.next;
    n.next = c;
  }
  for (let n = head; n; n = n.next.next) if (n.random) n.next.random = n.random.next;
  const dummy = node(0);
  let tail = dummy;
  for (let n = head; n; n = n.next) {
    tail = tail.next = n.next;
    n.next = n.next.next; // restore original
  }
  return dummy.next;
}

function describe(head) {
  const index = new Map();
  let i = 0;
  for (let n = head; n; n = n.next) index.set(n, i++);
  const out = [];
  for (let n = head; n; n = n.next) out.push([n.val, n.random ? index.get(n.random) : null]);
  return out;
}

function sample() {
  const [a, b, c, d, e] = [7, 13, 11, 10, 1].map(node);
  [a.next, b.next, c.next, d.next] = [b, c, d, e];
  [b.random, c.random, d.random, e.random] = [a, e, c, a];
  return a;
}

const original = sample();
const expected = describe(original);
const copied = copyWithMap(original);
assert.deepEqual(describe(copied), expected);
assert.notEqual(copied, original);
assert.deepEqual(describe(copyInterleaved(original)), expected);
assert.deepEqual(describe(original), expected);
console.log('all checks passed');
