/*
 * Reverse a linked list k nodes at a time. Dummy head + groupPrev pointer. O(n) time, O(1) space.
 */
const assert = require('node:assert/strict');
const { ListNode, build, toArray } = require('./list_node');

function reverse(head) {
  let prev = null;
  while (head) {
    const next = head.next;
    head.next = prev;
    prev = head;
    head = next;
  }
  return prev;
}

function reverseKGroup(head, k) {
  const dummy = new ListNode(0, head);
  let groupPrev = dummy;
  for (;;) {
    let kth = groupPrev;
    for (let i = 0; i < k && kth; i++) kth = kth.next;
    if (!kth) return dummy.next;
    const groupNext = kth.next;
    let prev = groupNext;
    let cur = groupPrev.next;
    while (cur !== groupNext) {
      const next = cur.next;
      cur.next = prev;
      prev = cur;
      cur = next;
    }
    const oldFirst = groupPrev.next;
    groupPrev.next = kth;
    groupPrev = oldFirst;
  }
}

assert.deepEqual(toArray(reverse(build(1, 2, 3))), [3, 2, 1]);
assert.deepEqual(toArray(reverseKGroup(build(1, 2, 3, 4, 5), 2)), [2, 1, 4, 3, 5]);
assert.deepEqual(toArray(reverseKGroup(build(1, 2, 3, 4, 5), 3)), [3, 2, 1, 4, 5]);
console.log('all checks passed');
