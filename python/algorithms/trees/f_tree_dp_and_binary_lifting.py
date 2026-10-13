"""Tree DP and binary lifting: house robber III (pair of robbed / not robbed), binary tree cameras
(post-order states), k-th ancestor and LCA in O(log n) with up[j][v] = 2^j-th ancestor.
"""
from tree_node import build


def rob(root):
    def pair(n):  # (robbed, not robbed)
        if not n:
            return 0, 0
        l, r = pair(n.left), pair(n.right)
        return n.val + l[1] + r[1], max(l) + max(r)
    return max(pair(root))


def min_camera_cover(root):
    cameras = 0

    def cover(n):  # 0 not covered, 1 has camera, 2 covered
        nonlocal cameras
        if not n:
            return 2
        l, r = cover(n.left), cover(n.right)
        if l == 0 or r == 0:
            cameras += 1
            return 1
        return 2 if l == 1 or r == 1 else 0

    if cover(root) == 0:
        cameras += 1
    return cameras


class BinaryLifting:
    def __init__(self, parent):
        n = len(parent)
        self.log = max(1, (n - 1).bit_length())
        self.up = [parent[:]]
        self.depth = [0] * n
        children = [[] for _ in range(n)]
        root = parent.index(-1)
        for v, p in enumerate(parent):
            if p != -1:
                children[p].append(v)
        stack = [root]
        while stack:
            v = stack.pop()
            for c in children[v]:
                self.depth[c] = self.depth[v] + 1
                stack.append(c)
        for j in range(1, self.log + 1):
            prev = self.up[j - 1]
            self.up.append([-1 if prev[v] == -1 else prev[prev[v]] for v in range(n)])

    def kth_ancestor(self, v, k):
        for j in range(self.log + 1):
            if v != -1 and k >> j & 1:
                v = self.up[j][v]
        return -1 if k >= 1 << (self.log + 1) else v

    def lca(self, a, b):
        if self.depth[a] < self.depth[b]:
            a, b = b, a
        a = self.kth_ancestor(a, self.depth[a] - self.depth[b])
        if a == b:
            return a
        for j in range(self.log, -1, -1):
            if self.up[j][a] != self.up[j][b]:
                a, b = self.up[j][a], self.up[j][b]
        return self.up[0][a]


if __name__ == "__main__":
    assert rob(build(3, 2, 3, None, 3, None, 1)) == 7 and rob(build(3, 4, 5, 1, 3, None, 1)) == 9
    assert min_camera_cover(build(0, 0, None, 0, 0)) == 1
    assert min_camera_cover(build(0, 0, None, 0, None, 0, None, None, 0)) == 2
    bl = BinaryLifting([-1, 0, 0, 1, 1, 2, 2])
    assert bl.kth_ancestor(3, 1) == 1 and bl.kth_ancestor(5, 2) == 0 and bl.kth_ancestor(6, 3) == -1
    assert bl.lca(3, 4) == 1 and bl.lca(3, 5) == 0 and bl.lca(5, 2) == 2 and bl.lca(4, 4) == 4
    print("all checks passed")
