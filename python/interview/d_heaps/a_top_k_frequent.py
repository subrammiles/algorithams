"""Top k frequent elements.

Min-heap of size k: O(n log k), good for streams. Bucket sort by frequency: O(n).
Quickselect for kth largest: average O(n) (random pivot avoids O(n^2) on sorted input).
At scale: Count-Min Sketch + heap, or per-shard top-k merged (map-reduce).
"""
import heapq
import random
from collections import Counter


def top_k_heap(nums, k):
    heap = []
    for value, freq in Counter(nums).items():
        heapq.heappush(heap, (freq, value))
        if len(heap) > k:
            heapq.heappop(heap)
    return [v for _, v in sorted(heap, reverse=True)]


def top_k_bucket(nums, k):
    buckets = [[] for _ in range(len(nums) + 1)]
    for value, freq in Counter(nums).items():
        buckets[freq].append(value)
    result = []
    for freq in range(len(nums), 0, -1):
        result.extend(buckets[freq])
        if len(result) >= k:
            return result[:k]
    return result


def kth_largest_quickselect(nums, k):
    a = list(nums)
    target = len(a) - k
    lo, hi = 0, len(a) - 1
    rnd = random.Random(42)
    while True:
        p = rnd.randint(lo, hi)
        a[p], a[hi] = a[hi], a[p]
        store = lo
        for i in range(lo, hi):
            if a[i] < a[hi]:
                a[i], a[store] = a[store], a[i]
                store += 1
        a[store], a[hi] = a[hi], a[store]
        if store == target:
            return a[store]
        if store < target:
            lo = store + 1
        else:
            hi = store - 1


if __name__ == "__main__":
    nums = [1, 1, 1, 2, 2, 3]
    assert top_k_heap(nums, 2) == [1, 2]
    assert top_k_bucket(nums, 2) == [1, 2]
    assert kth_largest_quickselect([3, 2, 3, 1, 2, 4, 5, 5, 6], 4) == 4
    print("all checks passed")
