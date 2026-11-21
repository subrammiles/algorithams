"""Digit DP, longest valid parentheses, k-equal-subsets bitmask DP (full explanations in Java).

count_special_numbers       digit DP over (pos, used-digit mask, tight, started); memoise the
                            non-tight, started states.
longest_valid_parentheses   stack of indices with the last unmatched ')' as the base.
can_partition_k_subsets     dp[mask] = fill level of the current bucket, -1 if unreachable.
"""
from functools import lru_cache


def count_special_numbers(n):
    digits = list(map(int, str(n)))

    @lru_cache(maxsize=None)
    def go(pos, mask, tight, started):
        if pos == len(digits):
            return 1 if started else 0
        limit = digits[pos] if tight else 9
        total = 0
        for d in range(limit + 1):
            next_tight = tight and d == limit
            if not started and d == 0:
                total += go(pos + 1, mask, next_tight, False)
            elif not mask & (1 << d):
                total += go(pos + 1, mask | (1 << d), next_tight, True)
        return total

    return go(0, 0, True, False)


def longest_valid_parentheses(s):
    stack, best = [-1], 0
    for i, c in enumerate(s):
        if c == "(":
            stack.append(i)
        else:
            stack.pop()
            if not stack:
                stack.append(i)
            else:
                best = max(best, i - stack[-1])
    return best


def can_partition_k_subsets(nums, k):
    total = sum(nums)
    if total % k:
        return False
    target, n = total // k, len(nums)
    dp = [-1] * (1 << n)
    dp[0] = 0
    for mask in range(1 << n):
        if dp[mask] < 0:
            continue
        for i in range(n):
            nxt = mask | (1 << i)
            if nxt != mask and dp[nxt] < 0 and dp[mask] + nums[i] <= target:
                dp[nxt] = (dp[mask] + nums[i]) % target
    return dp[-1] == 0


if __name__ == "__main__":
    assert count_special_numbers(20) == 19
    assert count_special_numbers(5) == 5
    assert count_special_numbers(135) == 110

    assert longest_valid_parentheses("(()") == 2
    assert longest_valid_parentheses(")()())") == 4
    assert longest_valid_parentheses("()(())") == 6
    assert longest_valid_parentheses("") == 0

    assert can_partition_k_subsets([4, 3, 2, 3, 5, 2, 1], 4)
    assert not can_partition_k_subsets([1, 2, 3, 4], 3)
    print("all checks passed")
