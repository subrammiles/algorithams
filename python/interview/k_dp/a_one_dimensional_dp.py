"""1D DP: house robber, coin change (min coins + number of ways), decode ways.

Greedy fails for coin change: coins {1,3,4}, amount 6 -> greedy 4+1+1, optimal 3+3.
Number of ways: loop coins OUTER to count combinations, not permutations.
"""
from functools import lru_cache


def rob(nums):
    prev2 = prev1 = 0
    for x in nums:
        prev2, prev1 = prev1, max(prev1, prev2 + x)
    return prev1


def coin_change_min(coins, amount):
    dp = [0] + [amount + 1] * amount  # amount + 1 acts as infinity
    for a in range(1, amount + 1):
        for c in coins:
            if c <= a:
                dp[a] = min(dp[a], dp[a - c] + 1)
    return -1 if dp[amount] > amount else dp[amount]


def coin_change_min_top_down(coins, amount):
    """Same recurrence, memoised recursion - often the easier first step in an interview."""
    @lru_cache(maxsize=None)
    def best(a):
        if a == 0:
            return 0
        options = [best(a - c) + 1 for c in coins if c <= a]
        return min(options, default=float("inf"))

    result = best(amount)
    return -1 if result == float("inf") else result


def coin_change_ways(coins, amount):
    dp = [1] + [0] * amount
    for c in coins:
        for a in range(c, amount + 1):
            dp[a] += dp[a - c]
    return dp[amount]


def num_decodings(s):
    prev2, prev1 = 1, 0 if s[0] == "0" else 1
    for i in range(1, len(s)):
        cur = prev1 if s[i] != "0" else 0
        if 10 <= int(s[i - 1:i + 1]) <= 26:
            cur += prev2
        prev2, prev1 = prev1, cur
    return prev1


if __name__ == "__main__":
    assert rob([2, 7, 9, 3, 1]) == 12
    assert coin_change_min([1, 3, 4], 6) == 2 == coin_change_min_top_down([1, 3, 4], 6)
    assert coin_change_min([2], 3) == -1 == coin_change_min_top_down([2], 3)
    assert coin_change_ways([1, 2, 5], 5) == 4
    assert num_decodings("226") == 3 and num_decodings("06") == 0
    print("all checks passed")
