"""Heap problems: k closest points (max-heap of size k via negation), kth largest in a stream
(min-heap of size k), ugly numbers (three pointers, O(n)), last stone weight (max-heap).
"""
import heapq


def k_closest(points, k):
    heap = []
    for x, y in points:
        heapq.heappush(heap, (-(x * x + y * y), x, y))
        if len(heap) > k:
            heapq.heappop(heap)  # drops the farthest
    return sorted([x, y] for _, x, y in heap)


class KthLargest:
    def __init__(self, k, nums):
        self.k, self.heap = k, []
        for x in nums:
            self.add(x)

    def add(self, val):
        heapq.heappush(self.heap, val)
        if len(self.heap) > self.k:
            heapq.heappop(self.heap)
        return self.heap[0]


def nth_ugly_number(n):
    ugly, i2, i3, i5 = [1], 0, 0, 0
    while len(ugly) < n:
        nxt = min(ugly[i2] * 2, ugly[i3] * 3, ugly[i5] * 5)
        ugly.append(nxt)
        i2 += nxt == ugly[i2] * 2  # advance every pointer that produced it
        i3 += nxt == ugly[i3] * 3
        i5 += nxt == ugly[i5] * 5
    return ugly[n - 1]


def last_stone_weight(stones):
    heap = [-s for s in stones]
    heapq.heapify(heap)
    while len(heap) > 1:
        a, b = -heapq.heappop(heap), -heapq.heappop(heap)
        if a != b:
            heapq.heappush(heap, -(a - b))
    return -heap[0] if heap else 0


if __name__ == "__main__":
    assert k_closest([[1, 3], [-2, 2]], 1) == [[-2, 2]]
    assert k_closest([[3, 3], [5, -1], [-2, 4]], 2) == [[-2, 4], [3, 3]]
    kth = KthLargest(3, [4, 5, 8, 2])
    assert [kth.add(x) for x in (3, 5, 10, 9, 4)] == [4, 5, 5, 8, 8]
    assert nth_ugly_number(10) == 12 and nth_ugly_number(1) == 1
    assert last_stone_weight([2, 7, 4, 1, 8, 1]) == 1
    print("all checks passed")
