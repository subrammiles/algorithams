"""Grid DP with one row: unique paths (+ obstacles), minimum path sum,
maximal square (side = 1 + min(top, left, top-left)).
"""


def unique_paths(m, n):
    dp = [1] * n
    for _ in range(1, m):
        for c in range(1, n):
            dp[c] += dp[c - 1]
    return dp[-1]


def unique_paths_with_obstacles(grid):
    dp = [0] * len(grid[0])
    dp[0] = 0 if grid[0][0] else 1
    for row in grid:
        for c, cell in enumerate(row):
            if cell:
                dp[c] = 0
            elif c:
                dp[c] += dp[c - 1]
    return dp[-1]


def min_path_sum(grid):
    dp = [float("inf")] * len(grid[0])
    dp[0] = 0
    for row in grid:
        for c, v in enumerate(row):
            dp[c] = v + (dp[c] if c == 0 else min(dp[c], dp[c - 1]))
    return dp[-1]


def maximal_square(matrix):
    rows, cols = len(matrix), len(matrix[0])
    side = [[0] * (cols + 1) for _ in range(rows + 1)]
    best = 0
    for r in range(1, rows + 1):
        for c in range(1, cols + 1):
            if matrix[r - 1][c - 1] == "1":
                side[r][c] = 1 + min(side[r - 1][c - 1], side[r - 1][c], side[r][c - 1])
                best = max(best, side[r][c])
    return best * best


if __name__ == "__main__":
    assert unique_paths(3, 7) == 28
    assert unique_paths_with_obstacles([[0, 0, 0], [0, 1, 0], [0, 0, 0]]) == 2
    assert unique_paths_with_obstacles([[1]]) == 0
    assert min_path_sum([[1, 3, 1], [1, 5, 1], [4, 2, 1]]) == 7
    assert maximal_square(["10100", "10111", "11111", "10010"]) == 4 and maximal_square(["0"]) == 0
    print("all checks passed")
