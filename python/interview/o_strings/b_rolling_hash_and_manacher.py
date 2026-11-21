"""Rabin-Karp, longest duplicate substring and Manacher (full explanations in the Java version).

rabin_karp             rolling hash; slide in O(1); VERIFY on a hash match (collisions).
longest_dup_substring  binary search on length (monotone) + rolling-hash check, O(n log n).
longest_palindrome     Manacher: '#'-separated string, mirror trick, right edge only grows -> O(n).
"""
MOD, BASE = 1_000_000_007, 131


def rabin_karp(text, pattern):
    n, m = len(text), len(pattern)
    if m == 0 or m > n:
        return []
    power = pow(BASE, m - 1, MOD)
    target = window = 0
    for i in range(m):
        target = (target * BASE + ord(pattern[i])) % MOD
        window = (window * BASE + ord(text[i])) % MOD
    out = []
    for i in range(n - m + 1):
        if window == target and text[i:i + m] == pattern:
            out.append(i)
        if i + m < n:
            window = ((window - ord(text[i]) * power) * BASE + ord(text[i + m])) % MOD
    return out


def _duplicate_of_length(s, length):
    power = pow(BASE, length - 1, MOD)
    seen = {}
    h = 0
    for i, c in enumerate(s):
        if i >= length:
            h = (h - ord(s[i - length]) * power) % MOD
        h = (h * BASE + ord(c)) % MOD
        if i >= length - 1:
            start = i - length + 1
            for other in seen.get(h, []):
                if s[other:other + length] == s[start:start + length]:
                    return start
            seen.setdefault(h, []).append(start)
    return -1


def longest_dup_substring(s):
    lo, hi, best = 1, len(s) - 1, ""
    while lo <= hi:
        mid = (lo + hi) // 2
        start = _duplicate_of_length(s, mid)
        if start >= 0:
            best = s[start:start + mid]
            lo = mid + 1
        else:
            hi = mid - 1
    return best


def longest_palindrome(s):
    if not s:
        return s
    t = "#" + "#".join(s) + "#"
    n = len(t)
    p = [0] * n
    center = right = best = 0
    for i in range(n):
        if i < right:
            p[i] = min(right - i, p[2 * center - i])
        while i - p[i] - 1 >= 0 and i + p[i] + 1 < n and t[i - p[i] - 1] == t[i + p[i] + 1]:
            p[i] += 1
        if i + p[i] > right:
            center, right = i, i + p[i]
        if p[i] > p[best]:
            best = i
    start = (best - p[best]) // 2
    return s[start:start + p[best]]


if __name__ == "__main__":
    assert rabin_karp("abracadabra", "abra") == [0, 7]
    assert rabin_karp("aaaa", "aa") == [0, 1, 2]
    assert rabin_karp("abc", "abcd") == []

    assert longest_dup_substring("banana") == "ana"
    assert longest_dup_substring("abcd") == ""
    assert longest_dup_substring("aaaaa") == "aaaa"

    assert len(longest_palindrome("babad")) == 3
    assert longest_palindrome("cbbd") == "bb"
    assert longest_palindrome("forgeeksskeegfor") == "geeksskeeg"
    assert longest_palindrome("a") == "a"
    print("all checks passed")
