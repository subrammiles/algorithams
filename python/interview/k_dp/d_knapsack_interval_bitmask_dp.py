"""Harder DP families.

0/1 knapsack (partition equal subset sum): iterate capacity DOWNWARDS so each item is used once.
Interval DP (burst balloons): k = LAST balloon burst in (l, r); fill by interval length. O(n^3).
Bitmask DP (TSP, n <= ~20): dp[mask][last]. O(2^n * n^2).
"""


def can_partition(nums):
    total = sum(nums)
    if total % 2:
        return False
    target = total // 2
    dp = [True] + [False] * target
    for x in nums:
        for c in range(target, x - 1, -1):
            dp[c] = dp[c] or dp[c - x]
    return dp[target]


def max_coins(nums):
    v = [1] + nums + [1]
    n = len(v)
    dp = [[0] * n for _ in range(n)]
    for length in range(2, n):
        for l in range(n - length):
            r = l + length
            dp[l][r] = max(dp[l][k] + dp[k][r] + v[l] * v[k] * v[r] for k in range(l + 1, r))
    return dp[0][n - 1]


def tsp(dist):
    n, inf = len(dist), float("inf")
    full = 1 << n
    dp = [[inf] * n for _ in range(full)]
    dp[1][0] = 0  # start at city 0
    for mask in range(1, full):
        for last in range(n):
            if dp[mask][last] == inf or not mask & (1 << last):
                continue
            for nxt in range(n):
                if mask & (1 << nxt):
                    continue
                nm = mask | (1 << nxt)
                dp[nm][nxt] = min(dp[nm][nxt], dp[mask][last] + dist[last][nxt])
    return min(dp[full - 1][last] + dist[last][0] for last in range(1, n))


if __name__ == "__main__":
    assert can_partition([1, 5, 11, 5]) and not can_partition([1, 2, 3, 5])
    assert max_coins([3, 1, 5, 8]) == 167
    assert tsp([[0, 10, 15, 20], [10, 0, 35, 25], [15, 35, 0, 30], [20, 25, 30, 0]]) == 80
    print("all checks passed")
