"""Divide and conquer: split, solve halves, combine. Two halves + linear combine -> O(n log n).

count inversions (merge sort) | max subarray (left / right / crossing) | majority element.
"""


def count_inversions(nums):
    def sort_count(a):
        if len(a) <= 1:
            return a, 0
        mid = len(a) // 2
        left, x = sort_count(a[:mid])
        right, y = sort_count(a[mid:])
        merged, count, i, j = [], x + y, 0, 0
        while i < len(left) and j < len(right):
            if left[i] <= right[j]:
                merged.append(left[i])
                i += 1
            else:
                count += len(left) - i  # all remaining left elements are bigger
                merged.append(right[j])
                j += 1
        return merged + left[i:] + right[j:], count

    return sort_count(list(nums))[1]


def max_subarray(a, lo=0, hi=None):
    hi = len(a) - 1 if hi is None else hi
    if lo == hi:
        return a[lo]
    mid = (lo + hi) // 2
    best_left = best_right = float("-inf")
    s = 0
    for i in range(mid, lo - 1, -1):
        s += a[i]
        best_left = max(best_left, s)
    s = 0
    for i in range(mid + 1, hi + 1):
        s += a[i]
        best_right = max(best_right, s)
    return max(best_left + best_right, max_subarray(a, lo, mid), max_subarray(a, mid + 1, hi))


def majority_element(a, lo=0, hi=None):
    hi = len(a) - 1 if hi is None else hi
    if lo == hi:
        return a[lo]
    mid = (lo + hi) // 2
    left, right = majority_element(a, lo, mid), majority_element(a, mid + 1, hi)
    if left == right:
        return left
    window = a[lo:hi + 1]
    return left if window.count(left) > window.count(right) else right


if __name__ == "__main__":
    assert count_inversions([2, 4, 1, 3, 5]) == 3
    assert count_inversions([5, 4, 3, 2, 1]) == 10 and count_inversions([1, 2, 3]) == 0
    assert max_subarray([-2, 1, -3, 4, -1, 2, 1, -5, 4]) == 6 and max_subarray([-3, -1, -2]) == -1
    assert majority_element([2, 2, 1, 1, 1, 2, 2]) == 2
    print("all checks passed")
