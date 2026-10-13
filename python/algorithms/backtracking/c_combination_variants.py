"""Combination variants: combination sum II (each once, skip duplicates), letter case permutation,
combinations C(n, k) with pruning, permutations with used[], subsets via bitmask.
"""


def combination_sum2(candidates, target):
    a, out = sorted(candidates), []

    def go(start, remaining, cur):
        if remaining == 0:
            out.append(cur[:])
            return
        for i in range(start, len(a)):
            if a[i] > remaining:
                break
            if i > start and a[i] == a[i - 1]:
                continue
            cur.append(a[i])
            go(i + 1, remaining - a[i], cur)
            cur.pop()

    go(0, target, [])
    return out


def letter_case_permutation(s):
    out = [""]
    for ch in s:
        out = [p + c for p in out for c in ({ch.lower(), ch.upper()} if ch.isalpha() else {ch})]
    return sorted(out)


def combine(n, k):
    out = []

    def go(start, cur):
        if len(cur) == k:
            out.append(cur[:])
            return
        for i in range(start, n - (k - len(cur)) + 2):  # prune: enough numbers must remain
            cur.append(i)
            go(i + 1, cur)
            cur.pop()

    go(1, [])
    return out


def permute(nums):
    out, used = [], [False] * len(nums)

    def go(cur):
        if len(cur) == len(nums):
            out.append(cur[:])
            return
        for i, x in enumerate(nums):
            if not used[i]:
                used[i] = True
                cur.append(x)
                go(cur)
                cur.pop()
                used[i] = False

    go([])
    return out


def subsets_bitmask(nums):
    return [[x for i, x in enumerate(nums) if mask >> i & 1] for mask in range(1 << len(nums))]


if __name__ == "__main__":
    assert combination_sum2([10, 1, 2, 7, 6, 1, 5], 8) == [[1, 1, 6], [1, 2, 5], [1, 7], [2, 6]]
    assert letter_case_permutation("a1b2") == ["A1B2", "A1b2", "a1B2", "a1b2"]
    assert combine(4, 2) == [[1, 2], [1, 3], [1, 4], [2, 3], [2, 4], [3, 4]]
    assert permute([1, 2, 3]) == [[1, 2, 3], [1, 3, 2], [2, 1, 3], [2, 3, 1], [3, 1, 2], [3, 2, 1]]
    assert subsets_bitmask([1, 2, 3]) == [[], [1], [2], [1, 2], [3], [1, 3], [2, 3], [1, 2, 3]]
    print("all checks passed")
