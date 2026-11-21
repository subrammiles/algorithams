"""In-place array techniques (full explanations in the Java version).

product_except_self   prefix pass then suffix pass, no division (zeros!). O(n), O(1) extra.
first_missing_positive cyclic sort: put v at index v-1; first slot that is wrong gives the answer.
find_duplicates       flip the sign of nums[v-1] as a "seen v" bit.
sort_colors           Dutch national flag; don't advance mid after swapping with high.
next_permutation      pivot = first a[i] < a[i+1] from the right; swap with rightmost bigger; reverse suffix.
rotate                three reversals, k %= n.
corp_flight_bookings  difference array: diff[l] += v, diff[r+1] -= v, then prefix sum.
majority_element_ii   Boyer-Moore with two candidates, then verify counts (> n/3).
"""


def product_except_self(nums):
    n = len(nums)
    out = [1] * n
    left = 1
    for i in range(n):
        out[i] = left
        left *= nums[i]
    right = 1
    for i in range(n - 1, -1, -1):
        out[i] *= right
        right *= nums[i]
    return out


def first_missing_positive(nums):
    nums = list(nums)
    n = len(nums)
    for i in range(n):
        while 1 <= nums[i] <= n and nums[nums[i] - 1] != nums[i]:
            j = nums[i] - 1
            nums[i], nums[j] = nums[j], nums[i]
    for i, v in enumerate(nums):
        if v != i + 1:
            return i + 1
    return n + 1


def find_duplicates(nums):
    nums = list(nums)
    out = []
    for i in range(len(nums)):
        v = abs(nums[i])
        if nums[v - 1] < 0:
            out.append(v)
        else:
            nums[v - 1] = -nums[v - 1]
    return out


def sort_colors(a):
    low = mid = 0
    high = len(a) - 1
    while mid <= high:
        if a[mid] == 0:
            a[low], a[mid] = a[mid], a[low]
            low += 1
            mid += 1
        elif a[mid] == 1:
            mid += 1
        else:
            a[mid], a[high] = a[high], a[mid]
            high -= 1


def next_permutation(a):
    i = len(a) - 2
    while i >= 0 and a[i] >= a[i + 1]:
        i -= 1
    if i >= 0:
        j = len(a) - 1
        while a[j] <= a[i]:
            j -= 1
        a[i], a[j] = a[j], a[i]
    a[i + 1:] = reversed(a[i + 1:])


def rotate(a, k):
    n = len(a)
    if n == 0:
        return

    def rev(i, j):
        while i < j:
            a[i], a[j] = a[j], a[i]
            i += 1
            j -= 1

    k %= n
    rev(0, n - 1)
    rev(0, k - 1)
    rev(k, n - 1)


def corp_flight_bookings(bookings, n):
    diff = [0] * (n + 1)
    for first, last, seats in bookings:
        diff[first - 1] += seats
        diff[last] -= seats
    out, running = [], 0
    for i in range(n):
        running += diff[i]
        out.append(running)
    return out


def majority_element_ii(nums):
    c1, c2, n1, n2 = 0, 1, 0, 0
    for x in nums:
        if x == c1:
            n1 += 1
        elif x == c2:
            n2 += 1
        elif n1 == 0:
            c1, n1 = x, 1
        elif n2 == 0:
            c2, n2 = x, 1
        else:
            n1 -= 1
            n2 -= 1
    return sorted(c for c in (c1, c2) if nums.count(c) > len(nums) // 3)


if __name__ == "__main__":
    assert product_except_self([1, 2, 3, 4]) == [24, 12, 8, 6]
    assert product_except_self([-1, 1, 0, -3, 3]) == [0, 0, 9, 0, 0]

    assert first_missing_positive([3, 4, -1, 1]) == 2
    assert first_missing_positive([7, 8, 9, 11, 12]) == 1
    assert first_missing_positive([1, 2, 0]) == 3
    assert first_missing_positive([1, 1, 2, 2]) == 3

    assert find_duplicates([4, 3, 2, 7, 8, 2, 3, 1]) == [2, 3]

    colors = [2, 0, 2, 1, 1, 0]
    sort_colors(colors)
    assert colors == [0, 0, 1, 1, 2, 2]

    for perm, nxt in (([1, 2, 3], [1, 3, 2]), ([3, 2, 1], [1, 2, 3]), ([1, 1, 5], [1, 5, 1]), ([1, 3, 2], [2, 1, 3])):
        next_permutation(perm)
        assert perm == nxt, perm

    r = [1, 2, 3, 4, 5, 6, 7]
    rotate(r, 3)
    assert r == [5, 6, 7, 1, 2, 3, 4]
    r2 = [-1, -100, 3, 99]
    rotate(r2, 6)
    assert r2 == [3, 99, -1, -100]

    assert corp_flight_bookings([[1, 2, 10], [2, 3, 20], [2, 5, 25]], 5) == [10, 55, 45, 25, 25]

    assert majority_element_ii([3, 2, 3]) == [3]
    assert majority_element_ii([1, 2]) == [1, 2]
    assert majority_element_ii([1, 1, 1, 3, 3, 2, 2, 2]) == [1, 2]
    print("all checks passed")
