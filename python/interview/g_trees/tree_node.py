"""Shared TreeNode + helpers for the tree problems (build from LeetCode-style level order)."""
from collections import deque


class TreeNode:
    def __init__(self, val, left=None, right=None):
        self.val, self.left, self.right = val, left, right


def build(*level_order):
    if not level_order or level_order[0] is None:
        return None
    root = TreeNode(level_order[0])
    queue, i = deque([root]), 1
    while i < len(level_order):
        node = queue.popleft()
        for side in ("left", "right"):
            if i < len(level_order) and level_order[i] is not None:
                child = TreeNode(level_order[i])
                setattr(node, side, child)
                queue.append(child)
            i += 1
    return root


def find(root, val):
    if root is None or root.val == val:
        return root
    return find(root.left, val) or find(root.right, val)


if __name__ == "__main__":
    t = build(1, 2, 3, None, 4)
    assert t.left.right.val == 4 and find(t, 3).val == 3
    print("all checks passed")
