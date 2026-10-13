"""Deep copy of a list whose nodes also have a random pointer.

1) dict old -> new, two passes: O(n) time, O(n) space.
2) interleave A -> A' -> B -> B'..., set A'.random = A.random.next, then unweave: O(1) extra space.
"""


class Node:
    def __init__(self, val):
        self.val = val
        self.next = self.random = None


def copy_with_map(head):
    copy = {None: None}
    n = head
    while n:
        copy[n] = Node(n.val)
        n = n.next
    n = head
    while n:
        copy[n].next, copy[n].random = copy[n.next], copy[n.random]
        n = n.next
    return copy[head]


def copy_interleaved(head):
    n = head
    while n:
        c = Node(n.val)
        c.next, n.next = n.next, c
        n = c.next
    n = head
    while n:
        if n.random:
            n.next.random = n.random.next
        n = n.next.next
    dummy = tail = Node(0)
    n = head
    while n:
        tail.next = tail = n.next
        n.next = n.next.next  # restore the original list
        n = n.next
    return dummy.next


def describe(head):
    index, n, i = {}, head, 0
    while n:
        index[n], n, i = i, n.next, i + 1
    out, n = [], head
    while n:
        out.append((n.val, index.get(n.random)))
        n = n.next
    return out


def sample():
    a, b, c, d, e = (Node(v) for v in (7, 13, 11, 10, 1))
    a.next, b.next, c.next, d.next = b, c, d, e
    b.random, c.random, d.random, e.random = a, e, c, a
    return a


if __name__ == "__main__":
    original = sample()
    expected = describe(original)
    copied = copy_with_map(original)
    assert describe(copied) == expected and copied is not original
    assert describe(copy_interleaved(original)) == expected
    assert describe(original) == expected  # original untouched
    print("all checks passed")
