"""Median of two sorted arrays in O(log(min(m, n))).

Binary search a cut i in the smaller array; cut j in the other array is forced so the left side
holds half the elements. Correct when A[i-1] <= B[j] and B[j-1] <= A[i].
"""


def find_median(a, b):
    if len(a) > len(b):
        a, b = b, a
    m, n = len(a), len(b)
    half = (m + n + 1) // 2
    lo, hi = 0, m
    inf = float("inf")
    while lo <= hi:
        i = (lo + hi) // 2
        j = half - i
        a_left = a[i - 1] if i > 0 else -inf
        a_right = a[i] if i < m else inf
        b_left = b[j - 1] if j > 0 else -inf
        b_right = b[j] if j < n else inf
        if a_left <= b_right and b_left <= a_right:
            left_max = max(a_left, b_left)
            if (m + n) % 2:
                return float(left_max)
            return (left_max + min(a_right, b_right)) / 2
        if a_left > b_right:
            hi = i - 1
        else:
            lo = i + 1
    raise ValueError("inputs are not sorted")


if __name__ == "__main__":
    assert find_median([1, 3], [2]) == 2.0
    assert find_median([1, 2], [3, 4]) == 2.5
    assert find_median([], [1]) == 1.0
    print("all checks passed")
