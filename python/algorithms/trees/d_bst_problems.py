"""BST problems: recover a BST with two swapped nodes (inorder drops), inorder successor O(h),
trim to [low, high], build from preorder with an upper bound O(n).
"""
from tree_node import TreeNode, build, inorder, to_level_order


def recover_tree(root):
    state = {"prev": None, "first": None, "second": None}

    def walk(n):
        if not n:
            return
        walk(n.left)
        prev = state["prev"]
        if prev and prev.val > n.val:
            state["first"] = state["first"] or prev
            state["second"] = n
        state["prev"] = n
        walk(n.right)

    walk(root)
    state["first"].val, state["second"].val = state["second"].val, state["first"].val


def inorder_successor(root, x):
    candidate, n = None, root
    while n:
        if x < n.val:
            candidate, n = n.val, n.left
        else:
            n = n.right
    return candidate


def trim_bst(n, low, high):
    if not n:
        return None
    if n.val < low:
        return trim_bst(n.right, low, high)
    if n.val > high:
        return trim_bst(n.left, low, high)
    n.left, n.right = trim_bst(n.left, low, high), trim_bst(n.right, low, high)
    return n


def bst_from_preorder(preorder):
    i = 0

    def go(bound):
        nonlocal i
        if i == len(preorder) or preorder[i] > bound:
            return None
        n = TreeNode(preorder[i])
        i += 1
        n.left = go(n.val)
        n.right = go(bound)
        return n

    return go(float("inf"))


if __name__ == "__main__":
    a = build(1, 3, None, None, 2)
    recover_tree(a)
    assert to_level_order(a) == [3, 1, None, None, 2]
    b = build(3, 1, 4, None, None, 2)
    recover_tree(b)
    assert inorder(b) == [1, 2, 3, 4]
    bst = build(5, 3, 6, 2, 4, None, None, 1)
    assert inorder_successor(bst, 4) == 5 and inorder_successor(bst, 1) == 2 and inorder_successor(bst, 6) is None
    assert to_level_order(trim_bst(build(3, 0, 4, None, 2, None, None, 1), 1, 3)) == [3, 2, None, 1]
    assert to_level_order(bst_from_preorder([8, 5, 1, 7, 10, 12])) == [8, 5, 10, 1, 7, None, 12]
    print("all checks passed")
