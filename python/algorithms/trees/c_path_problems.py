"""Path problems: path sum II (backtracking), path sum III (prefix sums on the current path),
sum of root-to-leaf numbers, all nodes at distance K (parent map + BFS).
"""
from collections import defaultdict

from tree_node import build, find


def path_sum(root, target):
    out = []

    def go(n, remaining, path):
        if not n:
            return
        path.append(n.val)
        if not n.left and not n.right and remaining == n.val:
            out.append(path[:])
        go(n.left, remaining - n.val, path)
        go(n.right, remaining - n.val, path)
        path.pop()

    go(root, target, [])
    return out


def path_sum_any_downward(root, target):
    prefix_count = defaultdict(int)
    prefix_count[0] = 1

    def go(n, prefix):
        if not n:
            return 0
        prefix += n.val
        count = prefix_count[prefix - target]
        prefix_count[prefix] += 1
        count += go(n.left, prefix) + go(n.right, prefix)
        prefix_count[prefix] -= 1  # leaving this path
        return count

    return go(root, 0)


def sum_numbers(n, value=0):
    if not n:
        return 0
    value = value * 10 + n.val
    if not n.left and not n.right:
        return value
    return sum_numbers(n.left, value) + sum_numbers(n.right, value)


def distance_k(root, target, k):
    parent = {}

    def record(n, p):
        if n:
            parent[n] = p
            record(n.left, n)
            record(n.right, n)

    record(root, None)
    frontier, seen = [target], {target}
    for _ in range(k):
        frontier = [nb for n in frontier for nb in (n.left, n.right, parent[n]) if nb and nb not in seen]
        seen.update(frontier)
    return sorted(n.val for n in frontier)


if __name__ == "__main__":
    t = build(5, 4, 8, 11, None, 13, 4, 7, 2, None, None, 5, 1)
    assert path_sum(t, 22) == [[5, 4, 11, 2], [5, 8, 4, 5]]
    assert path_sum_any_downward(build(10, 5, -3, 3, 2, None, 11, 3, -2, None, 1), 8) == 3
    assert sum_numbers(build(1, 2, 3)) == 25 and sum_numbers(build(4, 9, 0, 5, 1)) == 1026
    r = build(3, 5, 1, 6, 2, 0, 8, None, None, 7, 4)
    assert distance_k(r, find(r, 5), 2) == [1, 4, 7]
    print("all checks passed")
