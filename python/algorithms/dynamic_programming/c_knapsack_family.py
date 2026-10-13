"""Knapsack family. 0/1: iterate capacity DOWN (each item once). Unbounded: iterate UP.
Rod cutting = unbounded knapsack. Target sum -> count subsets with sum (total + target) / 2.
Min subset difference -> largest reachable subset sum <= total / 2. O(n * W) pseudo-polynomial.
"""


def knapsack01_table(wt, val, capacity):
    n = len(wt)
    dp = [[0] * (capacity + 1) for _ in range(n + 1)]
    for i in range(1, n + 1):
        for w in range(capacity + 1):
            dp[i][w] = dp[i - 1][w]
            if wt[i - 1] <= w:
                dp[i][w] = max(dp[i][w], dp[i - 1][w - wt[i - 1]] + val[i - 1])
    return dp[n][capacity]


def knapsack01(wt, val, capacity):
    dp = [0] * (capacity + 1)
    for w_i, v_i in zip(wt, val):
        for w in range(capacity, w_i - 1, -1):
            dp[w] = max(dp[w], dp[w - w_i] + v_i)
    return dp[capacity]


def unbounded_knapsack(wt, val, capacity):
    dp = [0] * (capacity + 1)
    for w_i, v_i in zip(wt, val):
        for w in range(w_i, capacity + 1):
            dp[w] = max(dp[w], dp[w - w_i] + v_i)
    return dp[capacity]


def rod_cutting(prices, length):
    dp = [0] * (length + 1)
    for n in range(1, length + 1):
        dp[n] = max(prices[piece - 1] + dp[n - piece] for piece in range(1, n + 1))
    return dp[length]


def target_sum_ways(nums, target):
    total = sum(nums)
    if abs(target) > total or (total + target) % 2:
        return 0
    goal = (total + target) // 2
    ways = [1] + [0] * goal
    for x in nums:
        for s in range(goal, x - 1, -1):
            ways[s] += ways[s - x]
    return ways[goal]


def min_subset_sum_difference(nums):
    total = sum(nums)
    reachable = {0}
    for x in nums:
        reachable |= {s + x for s in reachable if s + x <= total // 2}
    return total - 2 * max(reachable)


if __name__ == "__main__":
    wt, val = [1, 3, 4, 5], [1, 4, 5, 7]
    assert knapsack01_table(wt, val, 7) == knapsack01(wt, val, 7) == 9
    assert unbounded_knapsack([1, 3, 4], [15, 50, 60], 8) == 130
    assert rod_cutting([1, 5, 8, 9, 10, 17, 17, 20], 8) == 22
    assert target_sum_ways([1, 1, 1, 1, 1], 3) == 5 and target_sum_ways([1], 2) == 0
    assert min_subset_sum_difference([1, 6, 11, 5]) == 1
    print("all checks passed")
