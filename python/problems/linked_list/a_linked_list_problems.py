"""Linked list problems: merge two sorted, remove n-th from end (one pass), palindrome (reverse half),
add two numbers, intersection (switch heads), reorder list, sort list (merge sort).
"""
from list_node import ListNode, of, to_list


def merge_two_lists(a, b):
    dummy = tail = ListNode(0)
    while a and b:
        if a.val <= b.val:
            tail.next, a = a, a.next
        else:
            tail.next, b = b, b.next
        tail = tail.next
    tail.next = a or b
    return dummy.next


def remove_nth_from_end(head, n):
    dummy = ListNode(0, head)
    fast = slow = dummy
    for _ in range(n + 1):
        fast = fast.next
    while fast:
        fast, slow = fast.next, slow.next
    slow.next = slow.next.next
    return dummy.next


def reverse(head):
    prev = None
    while head:
        head.next, prev, head = prev, head, head.next
    return prev


def _middle(head):
    slow = fast = head
    while fast.next and fast.next.next:
        slow, fast = slow.next, fast.next.next
    return slow


def is_palindrome(head):
    if not head:
        return True
    mid = _middle(head)
    second = reverse(mid.next)
    p, q, ok = head, second, True
    while q:
        ok &= p.val == q.val
        p, q = p.next, q.next
    mid.next = reverse(second)  # restore the input
    return ok


def add_two_numbers(a, b):
    dummy = tail = ListNode(0)
    carry = 0
    while a or b or carry:
        total = carry + (a.val if a else 0) + (b.val if b else 0)
        tail.next = ListNode(total % 10)
        tail, carry = tail.next, total // 10
        a, b = a and a.next, b and b.next
    return dummy.next


def get_intersection_node(a, b):
    p, q = a, b
    while p is not q:
        p = p.next if p else b
        q = q.next if q else a
    return p


def reorder_list(head):
    if not head:
        return
    mid = _middle(head)
    second, mid.next = reverse(mid.next), None
    first = head
    while second:
        first.next, second.next, first, second = second, first.next, first.next, second.next


def sort_list(head):
    if not head or not head.next:
        return head
    mid = _middle(head)
    right, mid.next = mid.next, None
    return merge_two_lists(sort_list(head), sort_list(right))


if __name__ == "__main__":
    assert to_list(merge_two_lists(of(1, 2, 4), of(1, 3, 4))) == [1, 1, 2, 3, 4, 4]
    assert to_list(remove_nth_from_end(of(1, 2, 3, 4, 5), 2)) == [1, 2, 3, 5] and to_list(remove_nth_from_end(of(1), 1)) == []
    pal = of(1, 2, 2, 1)
    assert is_palindrome(pal) and to_list(pal) == [1, 2, 2, 1] and not is_palindrome(of(1, 2))
    assert to_list(add_two_numbers(of(2, 4, 3), of(5, 6, 4))) == [7, 0, 8]
    assert to_list(add_two_numbers(of(9, 9, 9, 9, 9, 9, 9), of(9, 9, 9, 9))) == [8, 9, 9, 9, 0, 0, 0, 1]
    shared = of(8, 4, 5)
    a, b = of(4, 1), of(5, 6, 1)
    a.next.next, b.next.next.next = shared, shared
    assert get_intersection_node(a, b) is shared and get_intersection_node(of(1), of(2)) is None
    r = of(1, 2, 3, 4, 5)
    reorder_list(r)
    assert to_list(r) == [1, 5, 2, 4, 3]
    assert to_list(sort_list(of(4, 2, 1, 3))) == [1, 2, 3, 4] and to_list(sort_list(of(-1, 5, 3, 4, 0))) == [-1, 0, 3, 4, 5]
    print("all checks passed")
