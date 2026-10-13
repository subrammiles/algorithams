"""String greedy: remove K digits (monotonic stack), reorganize string (max-heap, hold back the
last used char), largest number (sort by a+b vs b+a).
"""
import heapq
from collections import Counter
from functools import cmp_to_key


def remove_k_digits(num, k):
    stack = []
    for ch in num:
        while k and stack and stack[-1] > ch:
            stack.pop()
            k -= 1
        stack.append(ch)
    if k:
        stack = stack[:-k]  # still need removals: drop from the end
    return "".join(stack).lstrip("0") or "0"


def reorganize_string(s):
    heap = [(-n, ch) for ch, n in Counter(s).items()]
    heapq.heapify(heap)
    out, held = [], None
    while heap:
        n, ch = heapq.heappop(heap)
        out.append(ch)
        if held:
            heapq.heappush(heap, held)
        held = (n + 1, ch) if n + 1 < 0 else None
    return "".join(out) if len(out) == len(s) else ""


def largest_number(nums):
    parts = sorted(map(str, nums), key=cmp_to_key(lambda a, b: (b + a > a + b) - (b + a < a + b)))
    return "0" if parts[0] == "0" else "".join(parts)


if __name__ == "__main__":
    assert remove_k_digits("1432219", 3) == "1219"
    assert remove_k_digits("10200", 1) == "200" and remove_k_digits("10", 2) == "0"
    r = reorganize_string("aaabbc")
    assert sorted(r) == sorted("aaabbc") and all(a != b for a, b in zip(r, r[1:]))
    assert reorganize_string("aaab") == ""
    assert largest_number([3, 30, 34, 5, 9]) == "9534330" and largest_number([0, 0]) == "0"
    print("all checks passed")
