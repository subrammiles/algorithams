"""Shared linked list node and helpers."""


class ListNode:
    def __init__(self, val, next=None):
        self.val, self.next = val, next


def of(*values):
    head = None
    for v in reversed(values):
        head = ListNode(v, head)
    return head


def to_list(head):
    out = []
    while head:
        out.append(head.val)
        head = head.next
    return out


if __name__ == "__main__":
    assert to_list(of(1, 2, 3)) == [1, 2, 3]
    print("all checks passed")
