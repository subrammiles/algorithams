"""Monotonic stack: next/previous greater element in O(n).

Keep indices whose answer is unknown; pop them when a value resolves them.
Circular next greater: iterate 2n indices with i % n. Stock span = previous greater.
"""


def next_greater_circular(nums):
    n = len(nums)
    result, stack = [-1] * n, []
    for i in range(2 * n):
        x = nums[i % n]
        while stack and nums[stack[-1]] < x:
            result[stack.pop()] = x
        if i < n:
            stack.append(i)
    return result


def daily_temperatures(t):
    wait, stack = [0] * len(t), []
    for i, temp in enumerate(t):
        while stack and t[stack[-1]] < temp:
            j = stack.pop()
            wait[j] = i - j
        stack.append(i)
    return wait


def stock_span(prices):
    span, stack = [], []
    for i, p in enumerate(prices):
        while stack and prices[stack[-1]] <= p:
            stack.pop()
        span.append(i + 1 if not stack else i - stack[-1])
        stack.append(i)
    return span


if __name__ == "__main__":
    assert next_greater_circular([1, 2, 1]) == [2, -1, 2]
    assert daily_temperatures([73, 74, 75, 71, 69, 72, 76, 73]) == [1, 1, 4, 2, 1, 1, 0, 0]
    assert stock_span([100, 80, 60, 70, 60, 75, 85]) == [1, 1, 1, 2, 1, 4, 6]
    print("all checks passed")
