/*
 * Merge two sorted, remove n-th from end, palindrome, add two numbers, intersection,
 * reorder list, sort list (merge sort).
 */
const assert = require('node:assert/strict');
const { ListNode, of, toArray } = require('./list_node');

function mergeTwoLists(a, b) {
  const dummy = new ListNode(0);
  let tail = dummy;
  while (a && b) {
    if (a.val <= b.val) {
      tail.next = a;
      a = a.next;
    } else {
      tail.next = b;
      b = b.next;
    }
    tail = tail.next;
  }
  tail.next = a ?? b;
  return dummy.next;
}

function removeNthFromEnd(head, n) {
  const dummy = new ListNode(0, head);
  let fast = dummy;
  let slow = dummy;
  for (let i = 0; i <= n; i++) fast = fast.next;
  while (fast) {
    fast = fast.next;
    slow = slow.next;
  }
  slow.next = slow.next.next;
  return dummy.next;
}

function reverse(head) {
  let prev = null;
  while (head) [head.next, prev, head] = [prev, head, head.next];
  return prev;
}

function middle(head) {
  let slow = head;
  let fast = head;
  while (fast.next && fast.next.next) {
    slow = slow.next;
    fast = fast.next.next;
  }
  return slow;
}

function isPalindrome(head) {
  if (!head) return true;
  const mid = middle(head);
  const second = reverse(mid.next);
  let ok = true;
  for (let p = head, q = second; q; p = p.next, q = q.next) if (p.val !== q.val) ok = false;
  mid.next = reverse(second); // restore
  return ok;
}

function addTwoNumbers(a, b) {
  const dummy = new ListNode(0);
  let tail = dummy;
  let carry = 0;
  while (a || b || carry) {
    const sum = carry + (a?.val ?? 0) + (b?.val ?? 0);
    tail = tail.next = new ListNode(sum % 10);
    carry = Math.floor(sum / 10);
    a = a?.next;
    b = b?.next;
  }
  return dummy.next;
}

function getIntersectionNode(a, b) {
  let p = a;
  let q = b;
  while (p !== q) {
    p = p ? p.next : b;
    q = q ? q.next : a;
  }
  return p;
}

function reorderList(head) {
  if (!head) return;
  const mid = middle(head);
  let second = reverse(mid.next);
  mid.next = null;
  for (let first = head; second; ) {
    const [n1, n2] = [first.next, second.next];
    first.next = second;
    second.next = n1;
    first = n1;
    second = n2;
  }
}

function sortList(head) {
  if (!head || !head.next) return head;
  const mid = middle(head);
  const right = mid.next;
  mid.next = null;
  return mergeTwoLists(sortList(head), sortList(right));
}

assert.deepEqual(toArray(mergeTwoLists(of(1, 2, 4), of(1, 3, 4))), [1, 1, 2, 3, 4, 4]);
assert.deepEqual(toArray(removeNthFromEnd(of(1, 2, 3, 4, 5), 2)), [1, 2, 3, 5]);
assert.deepEqual(toArray(removeNthFromEnd(of(1), 1)), []);
const pal = of(1, 2, 2, 1);
assert.ok(isPalindrome(pal));
assert.deepEqual(toArray(pal), [1, 2, 2, 1]);
assert.equal(isPalindrome(of(1, 2)), false);
assert.deepEqual(toArray(addTwoNumbers(of(2, 4, 3), of(5, 6, 4))), [7, 0, 8]);
assert.deepEqual(toArray(addTwoNumbers(of(9, 9, 9, 9, 9, 9, 9), of(9, 9, 9, 9))), [8, 9, 9, 9, 0, 0, 0, 1]);
const shared = of(8, 4, 5);
const a = of(4, 1);
const b = of(5, 6, 1);
a.next.next = shared;
b.next.next.next = shared;
assert.equal(getIntersectionNode(a, b), shared);
assert.equal(getIntersectionNode(of(1), of(2)), null);
const r = of(1, 2, 3, 4, 5);
reorderList(r);
assert.deepEqual(toArray(r), [1, 5, 2, 4, 3]);
assert.deepEqual(toArray(sortList(of(4, 2, 1, 3))), [1, 2, 3, 4]);
assert.deepEqual(toArray(sortList(of(-1, 5, 3, 4, 0))), [-1, 0, 3, 4, 5]);
console.log('all checks passed');
