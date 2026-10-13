"""Longest increasing subsequence.

O(n^2): dp[i] = 1 + max(dp[j]) for a[j] < a[i].
O(n log n): tails[k] = smallest tail of an increasing subsequence of length k+1; bisect_left + replace.
Russian doll envelopes: sort by width asc, height DESC, then LIS on heights.
"""
from bisect import bisect_left


def lis_quadratic(a):
    dp = [1] * len(a)
    for i in range(len(a)):
        for j in range(i):
            if a[j] < a[i]:
                dp[i] = max(dp[i], dp[j] + 1)
    return max(dp, default=0)


def lis_fast(a):
    tails = []
    for x in a:
        i = bisect_left(tails, x)
        if i == len(tails):
            tails.append(x)
        else:
            tails[i] = x
    return len(tails)


def max_envelopes(envelopes):
    ordered = sorted(envelopes, key=lambda e: (e[0], -e[1]))
    return lis_fast([h for _, h in ordered])


if __name__ == "__main__":
    a = [10, 9, 2, 5, 3, 7, 101, 18]
    assert lis_quadratic(a) == lis_fast(a) == 4
    assert lis_fast([7, 7, 7]) == 1
    assert max_envelopes([[5, 4], [6, 4], [6, 7], [2, 3]]) == 3
    print("all checks passed")
