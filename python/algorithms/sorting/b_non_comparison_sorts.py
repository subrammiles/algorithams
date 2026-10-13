"""Non-comparison sorts: counting sort O(n + k) (stable, handles negatives via an offset),
LSD radix sort O(d (n + base)), bucket sort for uniform values in [0, 1).
"""
import random


def counting_sort(a):
    if not a:
        return []
    lo = min(a)
    count = [0] * (max(a) - lo + 1)
    for x in a:
        count[x - lo] += 1
    for i in range(1, len(count)):
        count[i] += count[i - 1]
    out = [0] * len(a)
    for x in reversed(a):  # backwards keeps it stable
        count[x - lo] -= 1
        out[count[x - lo]] = x
    return out


def radix_sort(a):
    a, exp = list(a), 1
    while a and max(a) // exp:
        buckets = [[] for _ in range(10)]
        for x in a:
            buckets[x // exp % 10].append(x)  # appending keeps each pass stable
        a = [x for b in buckets for x in b]
        exp *= 10
    return a


def bucket_sort(a):
    n = len(a)
    buckets = [[] for _ in range(n)]
    for x in a:
        buckets[int(x * n)].append(x)
    return [x for b in buckets for x in sorted(b)]


if __name__ == "__main__":
    assert counting_sort([4, -2, 2, 8, 3, 3, -2, 1]) == [-2, -2, 1, 2, 3, 3, 4, 8] and counting_sort([]) == []
    assert radix_sort([170, 45, 75, 90, 802, 24, 2, 66]) == [2, 24, 45, 66, 75, 90, 170, 802]
    big = [random.Random(3).randint(0, 2**31 - 1) for _ in range(2000)]
    assert radix_sort(big) == sorted(big)
    d = [0.897, 0.565, 0.656, 0.1234, 0.665, 0.3434]
    assert bucket_sort(d) == sorted(d)
    print("all checks passed")
