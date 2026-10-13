"""Minimum window substring: smallest window of s containing every char of t (with counts).

Variable sliding window: expand right until valid (missing == 0), then shrink left while valid.
Time O(|s| + |t|).
Template: for right in s: add s[right]; while valid: record answer; remove s[left]; left += 1
"""
from collections import Counter


def min_window(s, t):
    need = Counter(t)
    missing = len(t)
    best_start, best_len = 0, float("inf")
    left = 0
    for right, ch in enumerate(s):
        if need[ch] > 0:
            missing -= 1
        need[ch] -= 1
        while missing == 0:
            if right - left + 1 < best_len:
                best_start, best_len = left, right - left + 1
            need[s[left]] += 1
            if need[s[left]] > 0:
                missing += 1
            left += 1
    return "" if best_len == float("inf") else s[best_start:best_start + best_len]


if __name__ == "__main__":
    assert min_window("ADOBECODEBANC", "ABC") == "BANC"
    assert min_window("a", "a") == "a"
    assert min_window("a", "aa") == ""
    print("all checks passed")
