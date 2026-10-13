"""Interval greedy: pick the interval that ENDS first.
activity selection | min arrows to burst balloons | partition labels | minimum railway platforms.
"""


def max_activities(start, end):
    count, last_end = 0, float("-inf")
    for s, e in sorted(zip(start, end), key=lambda p: p[1]):
        if s >= last_end:
            count, last_end = count + 1, e
    return count


def find_min_arrow_shots(points):
    arrows, arrow_at = 0, float("-inf")
    for s, e in sorted(points, key=lambda p: p[1]):
        if s > arrow_at:
            arrows, arrow_at = arrows + 1, e
    return arrows


def partition_labels(s):
    last = {ch: i for i, ch in enumerate(s)}
    sizes, start, end = [], 0, 0
    for i, ch in enumerate(s):
        end = max(end, last[ch])
        if i == end:
            sizes.append(end - start + 1)
            start = i + 1
    return sizes


def min_platforms(arrivals, departures):
    arr, dep = sorted(arrivals), sorted(departures)
    platforms = best = d = 0
    for a in arr:
        while dep[d] < a:
            d, platforms = d + 1, platforms - 1
        platforms += 1
        best = max(best, platforms)
    return best


if __name__ == "__main__":
    assert max_activities([1, 3, 0, 5, 8, 5], [2, 4, 6, 7, 9, 9]) == 4
    assert find_min_arrow_shots([[10, 16], [2, 8], [1, 6], [7, 12]]) == 2
    assert find_min_arrow_shots([[1, 2], [3, 4], [5, 6], [7, 8]]) == 4
    assert find_min_arrow_shots([[1, 2], [2, 3], [3, 4], [4, 5]]) == 2
    assert partition_labels("ababcbacadefegdehijhklij") == [9, 7, 8]
    assert min_platforms([900, 940, 950, 1100, 1500, 1800], [910, 1200, 1120, 1130, 1900, 2000]) == 3
    print("all checks passed")
