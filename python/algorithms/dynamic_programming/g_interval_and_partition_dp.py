"""Interval / partition DP: matrix chain multiplication O(n^3), palindrome partitioning min cuts O(n^2),
egg drop via "floors checkable with m moves and k eggs": f(m,k) = f(m-1,k-1) + f(m-1,k) + 1.
"""


def matrix_chain_order(dims):
    n = len(dims) - 1
    cost = [[0] * (n + 1) for _ in range(n + 1)]
    for length in range(2, n + 1):
        for i in range(1, n - length + 2):
            j = i + length - 1
            cost[i][j] = min(cost[i][k] + cost[k + 1][j] + dims[i - 1] * dims[k] * dims[j] for k in range(i, j))
    return cost[1][n]


def min_cut_palindrome_partition(s):
    n = len(s)
    pal = [[False] * n for _ in range(n)]
    cuts = list(range(n))
    for j in range(n):
        for i in range(j + 1):
            if s[i] == s[j] and (j - i < 2 or pal[i + 1][j - 1]):
                pal[i][j] = True
                cuts[j] = 0 if i == 0 else min(cuts[j], cuts[i - 1] + 1)
    return cuts[-1]


def egg_drop(eggs, floors):
    covered, moves = [0] * (eggs + 1), 0
    while covered[eggs] < floors:
        moves += 1
        for k in range(eggs, 0, -1):
            covered[k] = covered[k - 1] + covered[k] + 1
    return moves


if __name__ == "__main__":
    assert matrix_chain_order([40, 20, 30, 10, 30]) == 26000
    assert matrix_chain_order([10, 20, 30, 40, 30]) == 30000
    assert min_cut_palindrome_partition("aab") == 1
    assert min_cut_palindrome_partition("a") == 0 and min_cut_palindrome_partition("ab") == 1
    assert egg_drop(2, 100) == 14 and egg_drop(1, 10) == 10 and egg_drop(3, 14) == 4
    print("all checks passed")
