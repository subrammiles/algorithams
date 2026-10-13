"""Intervals: sort by start, then sweep.

merge: extend the last merged interval while the next starts before it ends. O(n log n).
insert into sorted, non-overlapping list: before / overlapping / after phases. O(n).
min removals to make non-overlapping: sort by END, greedily keep earliest-ending.
"""


def merge(intervals):
    merged = []
    for start, end in sorted(intervals):
        if not merged or merged[-1][1] < start:
            merged.append([start, end])
        else:
            merged[-1][1] = max(merged[-1][1], end)
    return merged


def insert(intervals, new):
    out, i, n = [], 0, len(intervals)
    while i < n and intervals[i][1] < new[0]:
        out.append(intervals[i])
        i += 1
    start, end = new
    while i < n and intervals[i][0] <= end:
        start, end = min(start, intervals[i][0]), max(end, intervals[i][1])
        i += 1
    out.append([start, end])
    return out + intervals[i:]


def erase_overlap_intervals(intervals):
    removed, end = 0, float("-inf")
    for s, e in sorted(intervals, key=lambda x: x[1]):
        if s >= end:
            end = e
        else:
            removed += 1
    return removed


if __name__ == "__main__":
    assert merge([[1, 3], [8, 10], [2, 6], [15, 18]]) == [[1, 6], [8, 10], [15, 18]]
    assert merge([[1, 4], [4, 5]]) == [[1, 5]]
    assert insert([[1, 2], [3, 5], [6, 7], [8, 10], [12, 16]], [4, 8]) == [[1, 2], [3, 10], [12, 16]]
    assert erase_overlap_intervals([[1, 2], [2, 3], [3, 4], [1, 3]]) == 1
    print("all checks passed")
