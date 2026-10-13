"""Binary search variants with one template: first index where a monotonic condition is true.
lower/upper bound (also in the bisect module), first/last position, peak element,
integer square root, search a row-continuing matrix, ternary search on a unimodal array.
"""
import bisect


def lower_bound(a, x):
    lo, hi = 0, len(a)
    while lo < hi:
        mid = (lo + hi) // 2
        if a[mid] >= x:
            hi = mid
        else:
            lo = mid + 1
    return lo


def upper_bound(a, x):
    lo, hi = 0, len(a)
    while lo < hi:
        mid = (lo + hi) // 2
        if a[mid] > x:
            hi = mid
        else:
            lo = mid + 1
    return lo


def search_range(a, x):
    first = lower_bound(a, x)
    if first == len(a) or a[first] != x:
        return [-1, -1]
    return [first, upper_bound(a, x) - 1]


def find_peak_element(a):
    lo, hi = 0, len(a) - 1
    while lo < hi:
        mid = (lo + hi) // 2
        if a[mid] < a[mid + 1]:
            lo = mid + 1
        else:
            hi = mid
    return lo


def my_sqrt(n):
    lo, hi = 0, n + 1  # first x with x*x > n, minus one
    while lo < hi:
        mid = (lo + hi) // 2
        if mid * mid > n:
            hi = mid
        else:
            lo = mid + 1
    return lo - 1


def search_matrix(m, target):
    rows, cols = len(m), len(m[0])
    lo, hi = 0, rows * cols - 1
    while lo <= hi:
        mid = (lo + hi) // 2
        v = m[mid // cols][mid % cols]
        if v == target:
            return True
        lo, hi = (mid + 1, hi) if v < target else (lo, mid - 1)
    return False


def ternary_search_max(a):
    lo, hi = 0, len(a) - 1
    while hi - lo > 2:
        m1, m2 = lo + (hi - lo) // 3, hi - (hi - lo) // 3
        if a[m1] < a[m2]:
            lo = m1 + 1
        else:
            hi = m2 - 1
    return max(range(lo, hi + 1), key=a.__getitem__)


if __name__ == "__main__":
    a = [1, 2, 2, 2, 3]
    assert lower_bound(a, 2) == 1 == bisect.bisect_left(a, 2)
    assert upper_bound(a, 2) == 4 == bisect.bisect_right(a, 2)
    assert lower_bound(a, 9) == 5
    assert search_range([5, 7, 7, 8, 8, 10], 8) == [3, 4] and search_range([5, 7, 7, 8, 8, 10], 6) == [-1, -1]
    peaks = [1, 2, 1, 3, 5, 6, 4]
    p = find_peak_element(peaks)
    assert (p == 0 or peaks[p] > peaks[p - 1]) and (p == len(peaks) - 1 or peaks[p] > peaks[p + 1])
    assert my_sqrt(8) == 2 and my_sqrt(2147395599) == 46339 and my_sqrt(2**31 - 1) == 46340
    m = [[1, 3, 5, 7], [10, 11, 16, 20], [23, 30, 34, 60]]
    assert search_matrix(m, 3) and not search_matrix(m, 13)
    assert ternary_search_max([1, 3, 7, 9, 8, 4, 2]) == 3
    print("all checks passed")
