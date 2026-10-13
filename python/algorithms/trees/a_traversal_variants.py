"""Traversal variants: zigzag level order, right side view (right-first DFS),
vertical order (sort by column, row, value), boundary traversal.
"""
from collections import defaultdict

from tree_node import build


def zigzag_level_order(root):
    levels, level = [], [root] if root else []
    while level:
        values = [n.val for n in level]
        levels.append(values[::-1] if len(levels) % 2 else values)
        level = [c for n in level for c in (n.left, n.right) if c]
    return levels


def right_side_view(root):
    view = []

    def go(n, depth):
        if n:
            if depth == len(view):
                view.append(n.val)
            go(n.right, depth + 1)
            go(n.left, depth + 1)

    go(root, 0)
    return view


def vertical_order(root):
    cells = defaultdict(list)

    def go(n, row, col):
        if n:
            cells[col].append((row, n.val))
            go(n.left, row + 1, col - 1)
            go(n.right, row + 1, col + 1)

    go(root, 0, 0)
    return [[v for _, v in sorted(cells[c])] for c in sorted(cells)]


def boundary(root):
    if not root:
        return []

    def is_leaf(n):
        return not n.left and not n.right

    out = [] if is_leaf(root) else [root.val]
    n = root.left
    while n:
        if not is_leaf(n):
            out.append(n.val)
        n = n.left or n.right

    def leaves(n):
        if n:
            if is_leaf(n):
                out.append(n.val)
            leaves(n.left)
            leaves(n.right)

    leaves(root)
    right, n = [], root.right
    while n:
        if not is_leaf(n):
            right.append(n.val)
        n = n.right or n.left
    return out + right[::-1]


if __name__ == "__main__":
    t = build(3, 9, 20, None, None, 15, 7)
    assert zigzag_level_order(t) == [[3], [20, 9], [15, 7]]
    assert right_side_view(build(1, 2, 3, None, 5, None, 4)) == [1, 3, 4]
    assert vertical_order(t) == [[9], [3, 15], [20], [7]]
    assert vertical_order(build(1, 2, 3, 4, 5, 6, 7)) == [[4], [2], [1, 5, 6], [3], [7]]
    assert boundary(build(1, 2, 3, 4, 5, 6, None, None, None, 7, 8, 9, 10)) == [1, 2, 4, 7, 8, 9, 10, 6, 3]
    print("all checks passed")
