"""Sliding window maximum with a monotonic deque of indices (values decreasing).

Pop smaller values from the back, pop expired indices from the front; front is the max.
Each index enters and leaves once: O(n). (Heap: O(n log n); naive: O(nk).)
"""
from collections import deque


def max_sliding_window(nums, k):
    dq, result = deque(), []
    for i, x in enumerate(nums):
        if dq and dq[0] <= i - k:
            dq.popleft()
        while dq and nums[dq[-1]] <= x:
            dq.pop()
        dq.append(i)
        if i >= k - 1:
            result.append(nums[dq[0]])
    return result


if __name__ == "__main__":
    assert max_sliding_window([1, 3, -1, -3, 5, 3, 6, 7], 3) == [3, 3, 5, 5, 6, 7]
    assert max_sliding_window([4, 2], 1) == [4, 2]
    print("all checks passed")
