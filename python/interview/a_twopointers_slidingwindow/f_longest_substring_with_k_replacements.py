"""Longest repeating character replacement (at most k replacements).

Window is valid while length - count(most frequent char) <= k.
max_freq never needs to decrease: a stale value can't produce a longer *wrong* answer. O(n).
"""


def character_replacement(s, k):
    count = [0] * 26
    left = max_freq = best = 0
    for right, ch in enumerate(s):
        count[ord(ch) - 65] += 1
        max_freq = max(max_freq, count[ord(ch) - 65])
        while right - left + 1 - max_freq > k:
            count[ord(s[left]) - 65] -= 1
            left += 1
        best = max(best, right - left + 1)
    return best


if __name__ == "__main__":
    assert character_replacement("ABAB", 2) == 4
    assert character_replacement("AABABBA", 1) == 4
    print("all checks passed")
