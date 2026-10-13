"""3Sum: all unique triplets that sum to 0.

Sort, fix i, two-pointer search on the rest; skip equal neighbours to avoid duplicates.
Time O(n^2), extra space O(1). Follow-ups: kSum by recursion, 3Sum closest.
"""


def three_sum(nums):
    a = sorted(nums)
    result = []
    for i in range(len(a) - 2):
        if i > 0 and a[i] == a[i - 1]:
            continue
        if a[i] > 0:
            break  # nothing to the right can bring the sum back to 0
        lo, hi = i + 1, len(a) - 1
        while lo < hi:
            s = a[i] + a[lo] + a[hi]
            if s < 0:
                lo += 1
            elif s > 0:
                hi -= 1
            else:
                result.append([a[i], a[lo], a[hi]])
                while lo < hi and a[lo] == a[lo + 1]:
                    lo += 1
                while lo < hi and a[hi] == a[hi - 1]:
                    hi -= 1
                lo, hi = lo + 1, hi - 1
    return result


if __name__ == "__main__":
    assert three_sum([-1, 0, 1, 2, -1, -4]) == [[-1, -1, 2], [-1, 0, 1]]
    assert three_sum([0, 0, 0, 0]) == [[0, 0, 0]]
    assert three_sum([1, 2, 3]) == []
    print("all checks passed")
