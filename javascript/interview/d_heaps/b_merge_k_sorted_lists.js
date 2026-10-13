/*
 * Merge k sorted linked lists with a min-heap of current heads. O(N log k).
 */
const assert = require('node:assert/strict');
const { Heap } = require('../lib/heap');

const node = (val, next = null) => ({ val, next });
const build = (...values) => values.reduceRight((next, v) => node(v, next), null);
function toArray(head) {
  const out = [];
  for (let n = head; n; n = n.next) out.push(n.val);
  return out;
}

function mergeKLists(lists) {
  const heap = new Heap((a, b) => a.val - b.val);
  lists.filter(Boolean).forEach((head) => heap.push(head));
  const dummy = node(0);
  let tail = dummy;
  while (heap.size) {
    const smallest = heap.pop();
    tail = tail.next = smallest;
    if (smallest.next) heap.push(smallest.next);
  }
  return dummy.next;
}

assert.deepEqual(toArray(mergeKLists([build(1, 4, 5), build(1, 3, 4), build(2, 6)])), [1, 1, 2, 3, 4, 4, 5, 6]);
assert.deepEqual(toArray(mergeKLists([null])), []);
console.log('all checks passed');
