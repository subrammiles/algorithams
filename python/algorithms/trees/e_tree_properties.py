"""Tree properties: symmetric, subtree of another, flatten to a linked list in place
(reverse preorder with a prev pointer), count nodes of a complete tree in O(log^2 n).
"""
from tree_node import build


def is_symmetric(root):
    def mirror(a, b):
        if not a or not b:
            return a is b
        return a.val == b.val and mirror(a.left, b.right) and mirror(a.right, b.left)
    return not root or mirror(root.left, root.right)


def same_tree(a, b):
    if not a or not b:
        return a is b
    return a.val == b.val and same_tree(a.left, b.left) and same_tree(a.right, b.right)


def is_subtree(root, sub):
    if not root:
        return sub is None
    return same_tree(root, sub) or is_subtree(root.left, sub) or is_subtree(root.right, sub)


def flatten(root):
    prev = None

    def go(n):
        nonlocal prev
        if n:
            go(n.right)
            go(n.left)
            n.right, n.left, prev = prev, None, n

    go(root)


def count_complete_nodes(root):
    if not root:
        return 0
    left_depth = right_depth = 0
    n = root
    while n:
        left_depth, n = left_depth + 1, n.left
    n = root
    while n:
        right_depth, n = right_depth + 1, n.right
    if left_depth == right_depth:
        return (1 << left_depth) - 1  # perfect tree
    return 1 + count_complete_nodes(root.left) + count_complete_nodes(root.right)


if __name__ == "__main__":
    assert is_symmetric(build(1, 2, 2, 3, 4, 4, 3)) and not is_symmetric(build(1, 2, 2, None, 3, None, 3))
    assert is_subtree(build(3, 4, 5, 1, 2), build(4, 1, 2))
    assert not is_subtree(build(3, 4, 5, 1, 2, None, None, None, None, 0), build(4, 1, 2))
    t = build(1, 2, 5, 3, 4, None, 6)
    flatten(t)
    chain, n = [], t
    while n:
        assert n.left is None
        chain.append(n.val)
        n = n.right
    assert chain == [1, 2, 3, 4, 5, 6]
    assert count_complete_nodes(build(1, 2, 3, 4, 5, 6)) == 6 and count_complete_nodes(None) == 0
    print("all checks passed")
