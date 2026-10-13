"""Construct trees: preorder + inorder, inorder + postorder (value -> index map, O(n)),
sorted array -> balanced BST, BST iterator (explicit stack, amortised O(1) next).
"""
from tree_node import TreeNode, build, inorder, to_level_order


def build_from_pre_in(preorder, inorder_vals):
    index = {v: i for i, v in enumerate(inorder_vals)}
    pre = iter(preorder)

    def go(lo, hi):
        if lo > hi:
            return None
        root = TreeNode(next(pre))
        mid = index[root.val]
        root.left = go(lo, mid - 1)
        root.right = go(mid + 1, hi)
        return root

    return go(0, len(inorder_vals) - 1)


def build_from_in_post(inorder_vals, postorder):
    index = {v: i for i, v in enumerate(inorder_vals)}
    post = list(postorder)

    def go(lo, hi):
        if lo > hi:
            return None
        root = TreeNode(post.pop())
        mid = index[root.val]
        root.right = go(mid + 1, hi)  # right subtree sits just before the root in postorder
        root.left = go(lo, mid - 1)
        return root

    return go(0, len(inorder_vals) - 1)


def sorted_array_to_bst(a, lo=0, hi=None):
    hi = len(a) - 1 if hi is None else hi
    if lo > hi:
        return None
    mid = (lo + hi) // 2
    return TreeNode(a[mid], sorted_array_to_bst(a, lo, mid - 1), sorted_array_to_bst(a, mid + 1, hi))


class BSTIterator:
    def __init__(self, root):
        self.stack = []
        self._push_left(root)

    def _push_left(self, n):
        while n:
            self.stack.append(n)
            n = n.left

    def has_next(self):
        return bool(self.stack)

    def next(self):
        n = self.stack.pop()
        self._push_left(n.right)
        return n.val


def height(n):
    return 1 + max(height(n.left), height(n.right)) if n else 0


if __name__ == "__main__":
    expected = [3, 9, 20, None, None, 15, 7]
    assert to_level_order(build_from_pre_in([3, 9, 20, 15, 7], [9, 3, 15, 20, 7])) == expected
    assert to_level_order(build_from_in_post([9, 3, 15, 20, 7], [9, 15, 7, 20, 3])) == expected
    bst = sorted_array_to_bst([-10, -3, 0, 5, 9])
    assert inorder(bst) == [-10, -3, 0, 5, 9] and height(bst) == 3
    it = BSTIterator(build(7, 3, 15, None, None, 9, 20))
    out = []
    while it.has_next():
        out.append(it.next())
    assert out == [3, 7, 9, 15, 20]
    print("all checks passed")
