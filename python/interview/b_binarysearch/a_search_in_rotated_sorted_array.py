"""Search in a rotated sorted array (distinct values), O(log n).

At each mid one half is sorted; check whether the target lies in it.
Follow-ups: duplicates degrade to O(n); find the rotation point by comparing a[mid] with a[hi].
"""


def search(a, target):
    lo, hi = 0, len(a) - 1
    while lo <= hi:
        mid = (lo + hi) // 2
        if a[mid] == target:
            return mid
        if a[lo] <= a[mid]:  # left half sorted
            if a[lo] <= target < a[mid]:
                hi = mid - 1
            else:
                lo = mid + 1
        else:  # right half sorted
            if a[mid] < target <= a[hi]:
                lo = mid + 1
            else:
                hi = mid - 1
    return -1


def find_min_index(a):
    lo, hi = 0, len(a) - 1
    while lo < hi:
        mid = (lo + hi) // 2
        if a[mid] > a[hi]:
            lo = mid + 1
        else:
            hi = mid
    return lo


if __name__ == "__main__":
    a = [4, 5, 6, 7, 0, 1, 2]
    assert search(a, 0) == 4
    assert search(a, 3) == -1
    assert search([1, 3], 3) == 1
    assert find_min_index(a) == 4
    print("all checks passed")
