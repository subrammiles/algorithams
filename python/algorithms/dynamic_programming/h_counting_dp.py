"""Counting DP: unique BSTs (Catalan), palindromic substrings (expand around centres),
dice rolls to a target (mod 1e9+7).
"""
MOD = 1_000_000_007


def num_trees(n):
    g = [1] + [0] * n
    for nodes in range(1, n + 1):
        g[nodes] = sum(g[root - 1] * g[nodes - root] for root in range(1, nodes + 1))
    return g[n]


def count_palindromic_substrings(s):
    count = 0
    for centre in range(2 * len(s) - 1):
        lo, hi = centre // 2, centre // 2 + centre % 2
        while lo >= 0 and hi < len(s) and s[lo] == s[hi]:
            count += 1
            lo, hi = lo - 1, hi + 1
    return count


def num_rolls_to_target(dice, faces, target):
    ways = [1] + [0] * target
    for _ in range(dice):
        ways = [0] + [sum(ways[t - f] for f in range(1, min(faces, t) + 1)) % MOD for t in range(1, target + 1)]
    return ways[target]


if __name__ == "__main__":
    assert num_trees(3) == 5 and num_trees(5) == 42
    assert count_palindromic_substrings("aaa") == 6 and count_palindromic_substrings("abc") == 3
    assert num_rolls_to_target(2, 6, 7) == 6
    assert num_rolls_to_target(30, 30, 500) == 222616187
    print("all checks passed")
