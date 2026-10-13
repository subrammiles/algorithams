"""Trapping rain water: water above bar i = min(max_left, max_right) - height[i].

Prefix arrays: O(n) time, O(n) space. Two pointers: move the side with the smaller max -
its answer is already fixed. O(n) time, O(1) space.
Follow-ups: monotonic stack version; 2D version with a min-heap from the border.
"""


def trap_prefix(h):
    if not h:
        return 0
    n = len(h)
    left, right = [0] * n, [0] * n
    left[0], right[-1] = h[0], h[-1]
    for i in range(1, n):
        left[i] = max(left[i - 1], h[i])
    for i in range(n - 2, -1, -1):
        right[i] = max(right[i + 1], h[i])
    return sum(min(left[i], right[i]) - h[i] for i in range(n))


def trap_two_pointers(h):
    lo, hi, left_max, right_max, water = 0, len(h) - 1, 0, 0, 0
    while lo < hi:
        if h[lo] < h[hi]:
            left_max = max(left_max, h[lo])
            water += left_max - h[lo]
            lo += 1
        else:
            right_max = max(right_max, h[hi])
            water += right_max - h[hi]
            hi -= 1
    return water


if __name__ == "__main__":
    h = [0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1]
    assert trap_prefix(h) == trap_two_pointers(h) == 6
    assert trap_two_pointers([4, 2, 0, 3, 2, 5]) == 9
    assert trap_two_pointers([]) == 0
    print("all checks passed")
