"""Minimum meeting rooms for [start, end) meetings.

Heap of end times: reuse the earliest-ending room if free, else open one. O(n log n).
Sweep line: sorted starts and ends with two pointers; the peak overlap is the answer.
"""
import heapq


def min_rooms_heap(meetings):
    ends = []
    for start, end in sorted(meetings):
        if ends and ends[0] <= start:
            heapq.heappop(ends)
        heapq.heappush(ends, end)
    return len(ends)


def min_rooms_sweep(meetings):
    starts = sorted(s for s, _ in meetings)
    ends = sorted(e for _, e in meetings)
    rooms = best = e = 0
    for s in starts:
        while ends[e] <= s:
            e += 1
            rooms -= 1
        rooms += 1
        best = max(best, rooms)
    return best


if __name__ == "__main__":
    m = [[0, 30], [5, 10], [15, 20]]
    assert min_rooms_heap(m) == min_rooms_sweep(m) == 2
    m2 = [[1, 5], [2, 6], [3, 7], [7, 8]]
    assert min_rooms_heap(m2) == min_rooms_sweep(m2) == 3
    print("all checks passed")
