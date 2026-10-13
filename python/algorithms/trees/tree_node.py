"""Shared TreeNode: build from / serialise to LeetCode-style level order (None = missing)."""
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


def to_level_order(root):
    out, queue = [], deque([root])
    while queue:
        n = queue.popleft()
        out.append(None if n is None else n.val)
        if n:
            queue.extend((n.left, n.right))
    while out and out[-1] is None:
        out.pop()
    return out


def find(root, val):
    if root is None or root.val == val:
        return root
    return find(root.left, val) or find(root.right, val)


def inorder(root):
    return inorder(root.left) + [root.val] + inorder(root.right) if root else []


if __name__ == "__main__":
    assert to_level_order(build(1, None, 2, 3)) == [1, None, 2, 3]
    print("all checks passed")
