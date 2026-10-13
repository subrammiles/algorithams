"""Max path sum (any node to any node). Pattern: "return one thing, record another".

DFS returns the best DOWNWARD path from a node; it records the best path that BENDS at the node.
Negative branches are dropped with max(0, gain). O(n). Same pattern: diameter of a tree.
"""
from tree_node import build


def max_path_sum(root):
    best = float("-inf")

    def gain(n):
        nonlocal best
        if n is None:
            return 0
        left, right = max(0, gain(n.left)), max(0, gain(n.right))
        best = max(best, n.val + left + right)
        return n.val + max(left, right)

    gain(root)
    return best


def diameter(root):
    best = 0

    def depth(n):
        nonlocal best
        if n is None:
            return 0
        l, r = depth(n.left), depth(n.right)
        best = max(best, l + r)
        return 1 + max(l, r)

    depth(root)
    return best


if __name__ == "__main__":
    assert max_path_sum(build(-10, 9, 20, None, None, 15, 7)) == 42
    assert max_path_sum(build(-3)) == -3
    assert diameter(build(1, 2, 3, 4, 5)) == 3
    print("all checks passed")
