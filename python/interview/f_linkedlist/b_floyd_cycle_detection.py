"""Floyd's tortoise and hare: detect a cycle and find where it starts.

If slow and fast meet, reset one pointer to head and step both by 1: they meet at the start.
(head->start = a, start->meet = b, cycle = c: 2(a+b) = a+b+nc  =>  a = nc - b.)
Same idea: Find the Duplicate Number, treating i -> nums[i] as a linked list.
"""


class ListNode:
    def __init__(self, val, next=None):
        self.val, self.next = val, next


def detect_cycle(head):
    slow = fast = head
    while fast and fast.next:
        slow, fast = slow.next, fast.next.next
        if slow is fast:
            p = head
            while p is not slow:
                p, slow = p.next, slow.next
            return p
    return None


def find_duplicate(nums):
    slow = fast = nums[0]
    while True:
        slow, fast = nums[slow], nums[nums[fast]]
        if slow == fast:
            break
    slow = nums[0]
    while slow != fast:
        slow, fast = nums[slow], nums[fast]
    return slow


if __name__ == "__main__":
    nodes = [ListNode(v) for v in (3, 2, 0, -4)]
    for a, b in zip(nodes, nodes[1:]):
        a.next = b
    nodes[-1].next = nodes[1]
    assert detect_cycle(nodes[0]).val == 2
    assert detect_cycle(ListNode(1, ListNode(2))) is None
    assert find_duplicate([1, 3, 4, 2, 2]) == 2
    print("all checks passed")
