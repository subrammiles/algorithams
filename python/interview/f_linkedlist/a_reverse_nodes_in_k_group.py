"""Reverse a linked list k nodes at a time (a short final group stays as is).

Dummy head + group_prev pointer: find the kth node, reverse the group pointing its first node
at group_next, stitch group_prev to the new head. O(n) time, O(1) space.
"""


class ListNode:
    def __init__(self, val, next=None):
        self.val, self.next = val, next


def build(*values):
    dummy = tail = ListNode(0)
    for v in values:
        tail.next = tail = ListNode(v)
    return dummy.next


def to_list(head):
    out = []
    while head:
        out.append(head.val)
        head = head.next
    return out


def reverse(head):
    prev = None
    while head:
        head.next, prev, head = prev, head, head.next
    return prev


def reverse_k_group(head, k):
    dummy = ListNode(0, head)
    group_prev = dummy
    while True:
        kth = group_prev
        for _ in range(k):
            kth = kth.next
            if kth is None:
                return dummy.next
        group_next = kth.next
        prev, cur = group_next, group_prev.next
        while cur is not group_next:
            cur.next, prev, cur = prev, cur, cur.next
        old_first = group_prev.next
        group_prev.next = kth
        group_prev = old_first


if __name__ == "__main__":
    assert to_list(reverse(build(1, 2, 3))) == [3, 2, 1]
    assert to_list(reverse_k_group(build(1, 2, 3, 4, 5), 2)) == [2, 1, 4, 3, 5]
    assert to_list(reverse_k_group(build(1, 2, 3, 4, 5), 3)) == [3, 2, 1, 4, 5]
    print("all checks passed")
