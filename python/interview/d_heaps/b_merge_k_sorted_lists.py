"""Merge k sorted linked lists with a min-heap of current heads: O(N log k).

Push (value, list index, node) - the index breaks ties so nodes are never compared.
This is the core of external merge sort (sorting data bigger than RAM).
"""
import heapq


class ListNode:
    def __init__(self, val, next=None):
        self.val, self.next = val, next


def merge_k_lists(lists):
    heap = [(head.val, i, head) for i, head in enumerate(lists) if head]
    heapq.heapify(heap)
    dummy = tail = ListNode(0)
    while heap:
        _, i, node = heapq.heappop(heap)
        tail.next = tail = node
        if node.next:
            heapq.heappush(heap, (node.next.val, i, node.next))
    return dummy.next


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


if __name__ == "__main__":
    assert to_list(merge_k_lists([build(1, 4, 5), build(1, 3, 4), build(2, 6)])) == [1, 1, 2, 3, 4, 4, 5, 6]
    assert to_list(merge_k_lists([None])) == []
    print("all checks passed")
