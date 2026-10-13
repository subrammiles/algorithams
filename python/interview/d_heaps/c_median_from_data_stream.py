"""Median of a stream with two heaps.

low = max-heap of the smaller half (store negatives), high = min-heap of the larger half.
Invariant: len(low) == len(high) or len(low) == len(high) + 1.
add O(log n), median O(1). Sliding-window median needs deletions -> sorted containers / lazy deletion.
"""
import heapq


class MedianFinder:
    def __init__(self):
        self.low, self.high = [], []

    def add_num(self, x):
        heapq.heappush(self.low, -x)
        heapq.heappush(self.high, -heapq.heappop(self.low))
        if len(self.high) > len(self.low):
            heapq.heappush(self.low, -heapq.heappop(self.high))

    def find_median(self):
        if len(self.low) > len(self.high):
            return float(-self.low[0])
        return (-self.low[0] + self.high[0]) / 2


if __name__ == "__main__":
    m = MedianFinder()
    m.add_num(1)
    m.add_num(2)
    assert m.find_median() == 1.5
    m.add_num(3)
    assert m.find_median() == 2.0
    m.add_num(-5)
    m.add_num(10)
    assert m.find_median() == 2.0
    print("all checks passed")
