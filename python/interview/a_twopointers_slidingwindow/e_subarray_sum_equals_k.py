"""Count subarrays with sum k (negatives allowed -> sliding window does not work).

sum(i..j) = prefix[j] - prefix[i-1]: count earlier prefixes equal to (current - k).
Seed {0: 1} for subarrays starting at index 0. O(n) time and space.
Same trick: longest subarray with sum k, equal 0s and 1s, sum divisible by k.
"""
from collections import defaultdict


def subarray_sum(nums, k):
    seen = defaultdict(int)
    seen[0] = 1
    prefix = count = 0
    for x in nums:
        prefix += x
        count += seen[prefix - k]
        seen[prefix] += 1
    return count


if __name__ == "__main__":
    assert subarray_sum([1, 1, 1], 2) == 2
    assert subarray_sum([3, 4, 7, 2, -3, 1, 4, 2], 7) == 4
    assert subarray_sum([0, 0], 0) == 3
    print("all checks passed")
