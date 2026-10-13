"""String DP: edit distance, longest palindromic subsequence, longest palindromic substring
(expand around 2n-1 centres), word break, distinct subsequences, interleaving string.
"""


def edit_distance(a, b):
    prev = list(range(len(b) + 1))
    for i in range(1, len(a) + 1):
        cur = [i] + [0] * len(b)
        for j in range(1, len(b) + 1):
            if a[i - 1] == b[j - 1]:
                cur[j] = prev[j - 1]
            else:
                cur[j] = 1 + min(prev[j - 1], prev[j], cur[j - 1])
        prev = cur
    return prev[-1]


def longest_palindromic_subsequence(s):
    n = len(s)
    dp = [[0] * n for _ in range(n)]
    for i in range(n - 1, -1, -1):
        dp[i][i] = 1
        for j in range(i + 1, n):
            dp[i][j] = 2 + dp[i + 1][j - 1] if s[i] == s[j] else max(dp[i + 1][j], dp[i][j - 1])
    return dp[0][n - 1]


def longest_palindromic_substring(s):
    best = ""
    for centre in range(2 * len(s) - 1):
        lo, hi = centre // 2, centre // 2 + centre % 2
        while lo >= 0 and hi < len(s) and s[lo] == s[hi]:
            lo, hi = lo - 1, hi + 1
        if hi - lo - 1 > len(best):
            best = s[lo + 1:hi]
    return best


def word_break(s, words):
    dictionary, max_len = set(words), max(map(len, words))
    ok = [True] + [False] * len(s)
    for i in range(1, len(s) + 1):
        ok[i] = any(ok[i - n] and s[i - n:i] in dictionary for n in range(1, min(i, max_len) + 1))
    return ok[-1]


def num_distinct(s, t):
    dp = [1] + [0] * len(t)
    for ch in s:
        for j in range(len(t), 0, -1):  # backwards: dp[j-1] still from the previous row
            if ch == t[j - 1]:
                dp[j] += dp[j - 1]
    return dp[-1]


def is_interleave(a, b, c):
    if len(a) + len(b) != len(c):
        return False
    dp = [False] * (len(b) + 1)
    for i in range(len(a) + 1):
        for j in range(len(b) + 1):
            if i == 0 and j == 0:
                dp[j] = True
            else:
                from_a = i > 0 and dp[j] and a[i - 1] == c[i + j - 1]
                from_b = j > 0 and dp[j - 1] and b[j - 1] == c[i + j - 1]
                dp[j] = from_a or from_b
    return dp[-1]


if __name__ == "__main__":
    assert edit_distance("horse", "ros") == 3 and edit_distance("intention", "execution") == 5
    assert longest_palindromic_subsequence("bbbab") == 4
    assert longest_palindromic_substring("babad") == "bab" and longest_palindromic_substring("cbbd") == "bb"
    assert word_break("leetcode", ["leet", "code"])
    assert not word_break("catsandog", ["cats", "dog", "sand", "and", "cat"])
    assert num_distinct("rabbbit", "rabbit") == 3
    assert is_interleave("aabcc", "dbbca", "aadbbcbcac") and not is_interleave("aabcc", "dbbca", "aadbbbaccc")
    print("all checks passed")
