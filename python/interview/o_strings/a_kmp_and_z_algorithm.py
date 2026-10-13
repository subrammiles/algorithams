"""Linear-time pattern matching.

KMP: lps[i] = longest proper prefix of p[:i+1] that is also a suffix; on mismatch jump to lps[j-1].
Z-algorithm: z[i] = longest substring starting at i matching a prefix; search on p + '$' + text.
Both O(n + m). Bonus: repeated substring pattern via lps.
"""


def lps(p):
    table, length = [0] * len(p), 0
    i = 1
    while i < len(p):
        if p[i] == p[length]:
            length += 1
            table[i] = length
            i += 1
        elif length:
            length = table[length - 1]
        else:
            i += 1
    return table


def kmp_search(text, p):
    table, matches, j = lps(p), [], 0
    for i, ch in enumerate(text):
        while j and ch != p[j]:
            j = table[j - 1]
        if ch == p[j]:
            j += 1
            if j == len(p):
                matches.append(i - j + 1)
                j = table[j - 1]
    return matches


def z_array(s):
    n = len(s)
    z, l, r = [0] * n, 0, 0
    for i in range(1, n):
        if i < r:
            z[i] = min(r - i, z[i - l])
        while i + z[i] < n and s[z[i]] == s[i + z[i]]:
            z[i] += 1
        if i + z[i] > r:
            l, r = i, i + z[i]
    return z


def z_search(text, p):
    z = z_array(p + "$" + text)
    return [i - len(p) - 1 for i in range(len(p) + 1, len(z)) if z[i] == len(p)]


def repeated_substring_pattern(s):
    longest = lps(s)[-1]
    return longest > 0 and len(s) % (len(s) - longest) == 0


if __name__ == "__main__":
    assert lps("aabaaab") == [0, 1, 0, 1, 2, 2, 3]
    assert kmp_search("ababcabcabababd", "ababd") == [10]
    assert kmp_search("aaaa", "aa") == [0, 1, 2]
    assert z_search("ababcabcabababd", "ababd") == [10]
    assert repeated_substring_pattern("abcabcabc") and not repeated_substring_pattern("aba")
    print("all checks passed")
