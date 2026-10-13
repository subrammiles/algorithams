/* Shared linked list helpers for the f_linkedlist problems. */
class ListNode {
  constructor(val, next = null) {
    this.val = val;
    this.next = next;
  }
}

const build = (...values) => values.reduceRight((next, v) => new ListNode(v, next), null);

function toArray(head) {
  const out = [];
  for (let n = head; n; n = n.next) out.push(n.val);
  return out;
}

module.exports = { ListNode, build, toArray };
