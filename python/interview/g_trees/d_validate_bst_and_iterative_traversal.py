"""Validate a BST with (low, high) bounds - comparing only with children is the classic bug.
Iterative inorder with a stack; Morris inorder with O(1) extra space (temporary threads).
"""
from tree_node import build


def is_valid_bst(n, low=None, high=None):
    if n is None:
        return True
    if (low is not None and n.val <= low) or (high is not None and n.val >= high):
        return False
    return is_valid_bst(n.left, low, n.val) and is_valid_bst(n.right, n.val, high)


def inorder_iterative(root):
    out, stack, cur = [], [], root
    while cur or stack:
        while cur:
            stack.append(cur)
            cur = cur.left
        cur = stack.pop()
        out.append(cur.val)
        cur = cur.right
    return out


def inorder_morris(root):
    out, cur = [], root
    while cur:
        if cur.left is None:
            out.append(cur.val)
            cur = cur.right
        else:
            pred = cur.left
            while pred.right and pred.right is not cur:
                pred = pred.right
            if pred.right is None:  # first visit: create the thread
                pred.right = cur
                cur = cur.left
            else:  # second visit: remove it
                pred.right = None
                out.append(cur.val)
                cur = cur.right
    return out


if __name__ == "__main__":
    assert is_valid_bst(build(2, 1, 3))
    assert not is_valid_bst(build(5, 4, 6, None, None, 3, 7))
    t = build(4, 2, 6, 1, 3, 5, 7)
    assert inorder_iterative(t) == list(range(1, 8))
    assert inorder_morris(t) == list(range(1, 8))
    assert inorder_iterative(t) == list(range(1, 8))  # Morris restored the tree
    print("all checks passed")
