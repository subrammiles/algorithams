"""Two-string DP over prefixes: LCS (rolling rows) and regular expression matching ('.' and '*').

regex: if p[j-1] == '*': dp[i][j] = dp[i][j-2] or (match(s[i-1], p[j-2]) and dp[i-1][j])
       else:             dp[i][j] = match(s[i-1], p[j-1]) and dp[i-1][j-1]
"""


def lcs(s, t):
    prev = [0] * (len(t) + 1)
    for i in range(1, len(s) + 1):
        cur = [0] * (len(t) + 1)
        for j in range(1, len(t) + 1):
            cur[j] = prev[j - 1] + 1 if s[i - 1] == t[j - 1] else max(prev[j], cur[j - 1])
        prev = cur
    return prev[-1]


def is_match(s, p):
    m, n = len(s), len(p)
    dp = [[False] * (n + 1) for _ in range(m + 1)]
    dp[0][0] = True

    def matches(sc, pc):
        return pc == "." or pc == sc

    for i in range(m + 1):
        for j in range(1, n + 1):
            if p[j - 1] == "*":
                dp[i][j] = dp[i][j - 2] or (i > 0 and matches(s[i - 1], p[j - 2]) and dp[i - 1][j])
            else:
                dp[i][j] = i > 0 and matches(s[i - 1], p[j - 1]) and dp[i - 1][j - 1]
    return dp[m][n]


if __name__ == "__main__":
    assert lcs("abcde", "ace") == 3 and lcs("abc", "def") == 0
    assert is_match("aa", "a*") and is_match("ab", ".*") and is_match("aab", "c*a*b")
    assert not is_match("mississippi", "mis*is*p*.")
    print("all checks passed")
