"""The three sliding-window templates (full explanations in the Java version).

A) longest valid:  grow right; while invalid shrink left; record after.
B) shortest valid: grow right; while valid record, then shrink left.
C) fixed size k:   add right, record once right >= k-1, remove right-k+1.
exactly(K) = at_most(K) - at_most(K-1).

Each index enters and leaves the window once -> O(n).
"""
from collections import Counter, defaultdict


def length_of_longest_substring(s):
    last = {}
    best = left = 0
    for right, c in enumerate(s):
        if c in last:
            left = max(left, last[c] + 1)  # last[c] may already be left of the window ("abba")
        last[c] = right
        best = max(best, right - left + 1)
    return best


def find_anagrams(s, p):
    k = len(p)
    need, window = Counter(p), Counter()
    out = []
    for right, c in enumerate(s):
        window[c] += 1
        if right >= k:
            window[s[right - k]] -= 1
            if window[s[right - k]] == 0:
                del window[s[right - k]]
        if right >= k - 1 and window == need:
            out.append(right - k + 1)
    return out


def longest_ones(nums, k):
    best = zeros = left = 0
    for right, x in enumerate(nums):
        zeros += x == 0
        while zeros > k:
            zeros -= nums[left] == 0
            left += 1
        best = max(best, right - left + 1)
    return best


def _at_most_k_distinct(nums, k):
    count = defaultdict(int)
    total = left = 0
    for right, x in enumerate(nums):
        count[x] += 1
        while len(count) > k:
            count[nums[left]] -= 1
            if count[nums[left]] == 0:
                del count[nums[left]]
            left += 1
        total += right - left + 1
    return total


def subarrays_with_k_distinct(nums, k):
    return _at_most_k_distinct(nums, k) - _at_most_k_distinct(nums, k - 1)


def min_sub_array_len(target, nums):
    best = float("inf")
    total = left = 0
    for right, x in enumerate(nums):
        total += x
        while total >= target:
            best = min(best, right - left + 1)
            total -= nums[left]
            left += 1
    return 0 if best == float("inf") else best


if __name__ == "__main__":
    assert length_of_longest_substring("abcabcbb") == 3
    assert length_of_longest_substring("bbbbb") == 1
    assert length_of_longest_substring("pwwkew") == 3
    assert length_of_longest_substring("abba") == 2
    assert length_of_longest_substring("") == 0

    assert find_anagrams("cbaebabacd", "abc") == [0, 6]
    assert find_anagrams("abab", "ab") == [0, 1, 2]

    assert longest_ones([1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0], 2) == 6
    assert longest_ones([0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1], 3) == 10

    assert subarrays_with_k_distinct([1, 2, 1, 2, 3], 2) == 7
    assert subarrays_with_k_distinct([1, 2, 1, 3, 4], 3) == 3

    assert min_sub_array_len(7, [2, 3, 1, 2, 4, 3]) == 2
    assert min_sub_array_len(4, [1, 4, 4]) == 1
    assert min_sub_array_len(11, [1] * 8) == 0
    print("all checks passed")
