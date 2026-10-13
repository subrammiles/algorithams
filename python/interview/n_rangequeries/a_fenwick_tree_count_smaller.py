"""Fenwick tree + Count of Smaller Numbers After Self.

Coordinate-compress values to ranks, scan right to left: answer = prefix_sum(rank - 1), then add 1.
O(n log n). (Alternative: merge-sort counting, also used for counting inversions.)
"""


class Fenwick:
    def __init__(self, n):
        self.tree = [0] * (n + 1)

    def add(self, i, delta):
        while i < len(self.tree):
            self.tree[i] += delta
            i += i & -i

    def prefix_sum(self, i):
        s = 0
        while i > 0:
            s += self.tree[i]
            i -= i & -i
        return s

    def range_sum(self, l, r):
        return self.prefix_sum(r) - self.prefix_sum(l - 1)


def count_smaller(nums):
    rank = {v: i + 1 for i, v in enumerate(sorted(set(nums)))}
    bit, result = Fenwick(len(rank)), []
    for x in reversed(nums):
        result.append(bit.prefix_sum(rank[x] - 1))
        bit.add(rank[x], 1)
    return result[::-1]


if __name__ == "__main__":
    f = Fenwick(5)
    for i, v in enumerate([1, 3, 5, 7, 9], start=1):
        f.add(i, v)
    assert f.range_sum(2, 4) == 15
    f.add(3, 10)
    assert f.range_sum(2, 4) == 25
    assert count_smaller([5, 2, 6, 1]) == [2, 1, 1, 0]
    assert count_smaller([-1, -1]) == [0, 0]
    print("all checks passed")
