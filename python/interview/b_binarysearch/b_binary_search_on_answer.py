"""Binary search on the answer: when feasible(x) is monotonic, search for the first True.

Koko eating bananas: minimum speed to finish within h hours.
Split array largest sum: minimise the largest part when splitting into m parts.
Time O(n log(range)).
"""


def min_eating_speed(piles, h):
    lo, hi = 1, max(piles)
    while lo < hi:
        mid = (lo + hi) // 2
        hours = sum((p + mid - 1) // mid for p in piles)  # ceil(p / mid)
        if hours <= h:
            hi = mid
        else:
            lo = mid + 1
    return lo


def split_array(nums, m):
    def parts_needed(limit):
        parts, running = 1, 0
        for x in nums:
            if running + x > limit:
                parts, running = parts + 1, 0
            running += x
        return parts

    lo, hi = max(nums), sum(nums)
    while lo < hi:
        mid = (lo + hi) // 2
        if parts_needed(mid) <= m:
            hi = mid
        else:
            lo = mid + 1
    return lo


if __name__ == "__main__":
    assert min_eating_speed([3, 6, 7, 11], 8) == 4
    assert min_eating_speed([30, 11, 23, 4, 20], 5) == 30
    assert split_array([7, 2, 5, 10, 8], 2) == 18
    assert split_array([1, 2, 3, 4, 5], 2) == 9
    print("all checks passed")
