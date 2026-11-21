"""Closest pair of points, divide and conquer, O(n log n). Full explanation in B_ClosestPair.java.

Sort by x; d = min of both halves; only the strip |x - mid_x| < d can hold a closer pair.
Merging the halves by y on the way up keeps the strip sorted, and each strip point needs to be
compared with at most 7 following points (packing argument) -> T(n) = 2T(n/2) + O(n).
"""
import math
import random


def _dist(a, b):
    return math.hypot(a[0] - b[0], a[1] - b[1])


def closest_pair(points):
    def solve(p):
        """p sorted by x -> (closest distance, p sorted by y)."""
        n = len(p)
        if n <= 3:
            best = min((_dist(p[i], p[j]) for i in range(n) for j in range(i + 1, n)), default=math.inf)
            return best, sorted(p, key=lambda q: q[1])
        mid = n // 2
        mid_x = p[mid][0]
        d_left, left = solve(p[:mid])
        d_right, right = solve(p[mid:])
        d = min(d_left, d_right)
        merged, i, j = [], 0, 0  # merge by y, as in merge sort
        while i < len(left) or j < len(right):
            if j >= len(right) or (i < len(left) and left[i][1] <= right[j][1]):
                merged.append(left[i])
                i += 1
            else:
                merged.append(right[j])
                j += 1
        strip = [q for q in merged if abs(q[0] - mid_x) < d]
        for a in range(len(strip)):
            b = a + 1
            while b < len(strip) and strip[b][1] - strip[a][1] < d:
                d = min(d, _dist(strip[a], strip[b]))
                b += 1
        return d, merged

    return solve(sorted(points))[0]


def brute_force(p):
    return min(_dist(p[i], p[j]) for i in range(len(p)) for j in range(i + 1, len(p)))


if __name__ == "__main__":
    pts = [(2, 3), (12, 30), (40, 50), (5, 1), (12, 10), (3, 4)]
    assert math.isclose(closest_pair(pts), math.sqrt(2))
    assert closest_pair([(1, 1), (5, 5), (1, 1)]) == 0
    assert closest_pair([(0, 0), (3, 4)]) == 5

    rng = random.Random(3)
    for _ in range(20):
        p = [(rng.random() * 1000, rng.random() * 1000) for _ in range(300)]
        assert abs(closest_pair(p) - brute_force(p)) < 1e-9
    print("all checks passed")
