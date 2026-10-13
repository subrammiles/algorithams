/*
 * Floyd: detect a cycle and its start; find the duplicate number with the same trick.
 */
const assert = require('node:assert/strict');
const { ListNode, build } = require('./list_node');

function detectCycle(head) {
  let slow = head;
  let fast = head;
  while (fast && fast.next) {
    slow = slow.next;
    fast = fast.next.next;
    if (slow === fast) {
      let p = head;
      while (p !== slow) {
        p = p.next;
        slow = slow.next;
      }
      return p;
    }
  }
  return null;
}

function findDuplicate(nums) {
  let slow = nums[0];
  let fast = nums[0];
  do {
    slow = nums[slow];
    fast = nums[nums[fast]];
  } while (slow !== fast);
  slow = nums[0];
  while (slow !== fast) {
    slow = nums[slow];
    fast = nums[fast];
  }
  return slow;
}

const head = build(3, 2, 0, -4);
head.next.next.next.next = head.next;
assert.equal(detectCycle(head).val, 2);
assert.equal(detectCycle(new ListNode(1, new ListNode(2))), null);
assert.equal(findDuplicate([1, 3, 4, 2, 2]), 2);
console.log('all checks passed');
