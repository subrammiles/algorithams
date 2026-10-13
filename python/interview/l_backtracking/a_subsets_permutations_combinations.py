"""One backtracking template: choose -> recurse -> un-choose.
Duplicates: sort, skip nums[i] == nums[i-1] at the same depth. Output sizes: 2^n, n!, C(n, k).
"""


def subsets_with_dup(nums):
    a, out = sorted(nums), []

    def go(start, cur):
        out.append(cur[:])
        for i in range(start, len(a)):
            if i > start and a[i] == a[i - 1]:
                continue
            cur.append(a[i])
            go(i + 1, cur)
            cur.pop()

    go(0, [])
    return out


def permute_unique(nums):
    a, out, used = sorted(nums), [], [False] * len(nums)

    def go(cur):
        if len(cur) == len(a):
            out.append(cur[:])
            return
        for i in range(len(a)):
            if used[i] or (i > 0 and a[i] == a[i - 1] and not used[i - 1]):
                continue
            used[i] = True
            cur.append(a[i])
            go(cur)
            cur.pop()
            used[i] = False

    go([])
    return out


def combination_sum(candidates, target):
    a, out = sorted(candidates), []

    def go(start, remaining, cur):
        if remaining == 0:
            out.append(cur[:])
            return
        for i in range(start, len(a)):
            if a[i] > remaining:
                break  # sorted: prune
            cur.append(a[i])
            go(i, remaining - a[i], cur)  # i, not i+1: reuse allowed
            cur.pop()

    go(0, target, [])
    return out


if __name__ == "__main__":
    assert subsets_with_dup([1, 2, 2]) == [[], [1], [1, 2], [1, 2, 2], [2], [2, 2]]
    assert permute_unique([1, 1, 2]) == [[1, 1, 2], [1, 2, 1], [2, 1, 1]]
    assert combination_sum([2, 3, 6, 7], 7) == [[2, 2, 3], [7]]
    print("all checks passed")
