"""Largest rectangle in a histogram with an increasing stack, O(n).

When bar i is lower than the stack top, the top can't extend further right: pop it and its
width runs from the new top + 1 to i - 1. A trailing 0 flushes the stack.
Maximal rectangle in a 0/1 matrix: build a histogram per row and reuse this.
"""


def largest_rectangle(h):
    stack, best = [], 0
    for i, cur in enumerate(h + [0]):
        while stack and h[stack[-1]] > cur:
            height = h[stack.pop()]
            left = stack[-1] if stack else -1
            best = max(best, height * (i - left - 1))
        stack.append(i)
    return best


def maximal_rectangle(matrix):
    if not matrix:
        return 0
    heights, best = [0] * len(matrix[0]), 0
    for row in matrix:
        heights = [h + 1 if c == "1" else 0 for h, c in zip(heights, row)]
        best = max(best, largest_rectangle(heights))
    return best


if __name__ == "__main__":
    assert largest_rectangle([2, 1, 5, 6, 2, 3]) == 10
    assert largest_rectangle([2, 4]) == 4
    assert maximal_rectangle(["10100", "10111", "11111", "10010"]) == 6
    print("all checks passed")
