"""Sweep line (full explanations in the Java version).

get_skyline             events (x, -h) start / (x, h) end, sorted; max-heap of heights with lazy
                        deletion; emit (x, max) whenever the max height changes. O(n log n).
interval_intersection   two pointers; advance whichever interval ends first.
employee_free_time      flatten, sort by start, merge; gaps are the free time.
car_pooling             difference array over time (sorted events).
"""
import heapq
from collections import defaultdict


def get_skyline(buildings):
    events = sorted([(l, -h) for l, _, h in buildings] + [(r, h) for _, r, h in buildings])
    heap = [0]  # max-heap via negation; 0 = ground
    to_remove = defaultdict(int)
    out, prev = [], 0
    for x, h in events:
        if h < 0:
            heapq.heappush(heap, h)
        else:
            to_remove[-h] += 1  # lazily delete: only pop it once it reaches the top
        while to_remove[heap[0]]:
            to_remove[heap[0]] -= 1
            heapq.heappop(heap)
        cur = -heap[0]
        if cur != prev:
            out.append([x, cur])
            prev = cur
    return out


def interval_intersection(a, b):
    out, i, j = [], 0, 0
    while i < len(a) and j < len(b):
        lo, hi = max(a[i][0], b[j][0]), min(a[i][1], b[j][1])
        if lo <= hi:
            out.append([lo, hi])
        if a[i][1] < b[j][1]:
            i += 1
        else:
            j += 1
    return out


def employee_free_time(schedules):
    intervals = sorted((iv for person in schedules for iv in person), key=lambda iv: iv[0])
    free, end = [], intervals[0][1]
    for start, finish in intervals:
        if start > end:
            free.append([end, start])
        end = max(end, finish)
    return free


def car_pooling(trips, capacity):
    delta = defaultdict(int)
    for passengers, start, finish in trips:
        delta[start] += passengers
        delta[finish] -= passengers
    load = 0
    for t in sorted(delta):
        load += delta[t]
        if load > capacity:
            return False
    return True


if __name__ == "__main__":
    city = [[2, 9, 10], [3, 7, 15], [5, 12, 12], [15, 20, 10], [19, 24, 8]]
    assert get_skyline(city) == [[2, 10], [3, 15], [7, 12], [12, 0], [15, 10], [20, 8], [24, 0]]
    assert get_skyline([[0, 2, 3], [2, 5, 3]]) == [[0, 3], [5, 0]]
    assert get_skyline([[1, 2, 1], [1, 2, 2], [1, 2, 3]]) == [[1, 3], [2, 0]]

    a = [[0, 2], [5, 10], [13, 23], [24, 25]]
    b = [[1, 5], [8, 12], [15, 24], [25, 26]]
    assert interval_intersection(a, b) == [[1, 2], [5, 5], [8, 10], [15, 23], [24, 24], [25, 25]]

    assert employee_free_time([[[1, 2], [5, 6]], [[1, 3]], [[4, 10]]]) == [[3, 4]]
    assert employee_free_time([[[1, 3], [6, 7]], [[2, 4]], [[2, 5], [9, 12]]]) == [[5, 6], [7, 9]]

    trips = [[2, 1, 5], [3, 3, 7]]
    assert not car_pooling(trips, 4)
    assert car_pooling(trips, 5)
    assert car_pooling([[3, 2, 7], [3, 7, 9]], 3)
    print("all checks passed")
