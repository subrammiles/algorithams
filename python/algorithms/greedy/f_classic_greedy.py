"""Fractional knapsack, assign mice to holes, policemen catch thieves (full story in F_ClassicGreedy.java).

fractional_knapsack      best value/weight ratio first, split the last item. (0/1 knapsack needs DP!)
assign_mice_to_holes     sort both, pair in order, answer = max |mouse - hole|.
policemen_catch_thieves  two pointers over P and T positions; pair if within k, else advance the left one.
"""


def fractional_knapsack(values, weights, capacity):
    total, left = 0.0, capacity
    for v, w in sorted(zip(values, weights), key=lambda vw: vw[0] / vw[1], reverse=True):
        if left == 0:
            break
        take = min(left, w)
        total += v * take / w
        left -= take
    return total


def assign_mice_to_holes(mice, holes):
    return max(abs(m - h) for m, h in zip(sorted(mice), sorted(holes)))


def policemen_catch_thieves(line, k):
    police = [i for i, c in enumerate(line) if c == "P"]
    thieves = [i for i, c in enumerate(line) if c == "T"]
    caught = p = t = 0
    while p < len(police) and t < len(thieves):
        if abs(police[p] - thieves[t]) <= k:
            caught, p, t = caught + 1, p + 1, t + 1
        elif thieves[t] < police[p]:
            t += 1
        else:
            p += 1
    return caught


if __name__ == "__main__":
    assert fractional_knapsack([60, 100, 120], [10, 20, 30], 50) == 240
    assert fractional_knapsack([10, 20], [1, 2], 10) == 30

    assert assign_mice_to_holes([4, -4, 2], [4, 0, 5]) == 4
    assert assign_mice_to_holes([-10, -79, -79, 67, 93, -85, -28, -94], [-2, 9, 69, 25, -31, 23, 50, 78]) == 102

    assert policemen_catch_thieves("PTTPT", 1) == 2
    assert policemen_catch_thieves("TTPPTP", 2) == 3
    assert policemen_catch_thieves("PTPTTP", 3) == 3
    print("all checks passed")
