""""Ending at i" DP: Kadane, max product subarray (track max AND min), longest common substring,
max-sum increasing subsequence, longest bitonic subsequence (LIS + LDS - 1).
"""


def max_subarray(a):
    best = ending = a[0]
    for x in a[1:]:
        ending = max(x, ending + x)
        best = max(best, ending)
    return best


def max_product(a):
    best = hi = lo = a[0]
    for x in a[1:]:
        hi, lo = max(x, hi * x, lo * x), min(x, hi * x, lo * x)
        best = max(best, hi)
    return best


def longest_common_substring(a, b):
    prev, best = [0] * (len(b) + 1), 0
    for i in range(1, len(a) + 1):
        cur = [0] * (len(b) + 1)
        for j in range(1, len(b) + 1):
            if a[i - 1] == b[j - 1]:
                cur[j] = prev[j - 1] + 1
                best = max(best, cur[j])
        prev = cur
    return best


def max_sum_increasing_subsequence(a):
    total = list(a)
    for i in range(len(a)):
        for j in range(i):
            if a[j] < a[i]:
                total[i] = max(total[i], total[j] + a[i])
    return max(total)


def longest_bitonic(a):
    n = len(a)
    inc, dec = [1] * n, [1] * n
    for i in range(n):
        for j in range(i):
            if a[j] < a[i]:
                inc[i] = max(inc[i], inc[j] + 1)
    for i in range(n - 1, -1, -1):
        for j in range(n - 1, i, -1):
            if a[j] < a[i]:
                dec[i] = max(dec[i], dec[j] + 1)
    return max(x + y - 1 for x, y in zip(inc, dec))


if __name__ == "__main__":
    assert max_subarray([-2, 1, -3, 4, -1, 2, 1, -5, 4]) == 6
    assert max_product([2, 3, -2, 4]) == 6 and max_product([-2, 0, -1]) == 0 and max_product([-2, 3, -4]) == 24
    assert longest_common_substring("abcdxyz", "xyzabcd") == 4
    assert max_sum_increasing_subsequence([1, 101, 2, 3, 100, 4, 5]) == 106
    assert longest_bitonic([1, 11, 2, 10, 4, 5, 2, 1]) == 6
    print("all checks passed")
