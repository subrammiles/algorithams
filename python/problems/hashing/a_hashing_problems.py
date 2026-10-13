"""Hashing problems: two sum, group anagrams, longest consecutive sequence in O(n),
contains nearby duplicate, 4Sum II (meet in the middle, O(n^2)).
"""
from collections import Counter, defaultdict


def two_sum(nums, target):
    seen = {}
    for i, x in enumerate(nums):
        if target - x in seen:
            return [seen[target - x], i]
        seen[x] = i
    return []


def group_anagrams(words):
    groups = defaultdict(list)
    for w in words:
        groups["".join(sorted(w))].append(w)
    return sorted(sorted(g) for g in groups.values())


def longest_consecutive(nums):
    values, best = set(nums), 0
    for x in values:
        if x - 1 not in values:  # start of a run
            length = 1
            while x + length in values:
                length += 1
            best = max(best, length)
    return best


def contains_nearby_duplicate(nums, k):
    last = {}
    for i, x in enumerate(nums):
        if x in last and i - last[x] <= k:
            return True
        last[x] = i
    return False


def four_sum_count(a, b, c, d):
    sums = Counter(x + y for x in a for y in b)
    return sum(sums[-(x + y)] for x in c for y in d)


if __name__ == "__main__":
    assert two_sum([2, 7, 11, 15], 9) == [0, 1]
    assert group_anagrams(["eat", "tea", "tan", "ate", "nat", "bat"]) == [["ate", "eat", "tea"], ["bat"], ["nat", "tan"]]
    assert longest_consecutive([100, 4, 200, 1, 3, 2]) == 4 and longest_consecutive([0, 3, 7, 2, 5, 8, 4, 6, 0, 1]) == 9
    assert contains_nearby_duplicate([1, 2, 3, 1], 3) and not contains_nearby_duplicate([1, 2, 3, 1, 2, 3], 2)
    assert four_sum_count([1, 2], [-2, -1], [-1, 2], [0, 2]) == 2
    print("all checks passed")
