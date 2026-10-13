"""Grid graphs: islands (flood fill) and rotting oranges (multi-source BFS). O(rows * cols).

Use an explicit stack/queue: recursive DFS on a 1000x1000 grid overflows the recursion limit.
"""
from collections import deque

DIRS = ((1, 0), (-1, 0), (0, 1), (0, -1))


def num_islands(grid):
    rows, cols = len(grid), len(grid[0])
    seen, count = set(), 0
    for r in range(rows):
        for c in range(cols):
            if grid[r][c] != "1" or (r, c) in seen:
                continue
            count += 1
            stack = [(r, c)]
            seen.add((r, c))
            while stack:
                cr, cc = stack.pop()
                for dr, dc in DIRS:
                    nr, nc = cr + dr, cc + dc
                    if 0 <= nr < rows and 0 <= nc < cols and grid[nr][nc] == "1" and (nr, nc) not in seen:
                        seen.add((nr, nc))
                        stack.append((nr, nc))
    return count


def oranges_rotting(grid):
    g = [row[:] for row in grid]
    rows, cols = len(g), len(g[0])
    queue = deque((r, c) for r in range(rows) for c in range(cols) if g[r][c] == 2)
    fresh = sum(row.count(1) for row in g)
    minutes = 0
    while queue and fresh:
        minutes += 1
        for _ in range(len(queue)):
            r, c = queue.popleft()
            for dr, dc in DIRS:
                nr, nc = r + dr, c + dc
                if 0 <= nr < rows and 0 <= nc < cols and g[nr][nc] == 1:
                    g[nr][nc] = 2
                    fresh -= 1
                    queue.append((nr, nc))
    return minutes if fresh == 0 else -1


if __name__ == "__main__":
    assert num_islands(["11000", "11000", "00100", "00011"]) == 3
    assert oranges_rotting([[2, 1, 1], [1, 1, 0], [0, 1, 1]]) == 4
    assert oranges_rotting([[2, 1, 1], [0, 1, 1], [1, 0, 1]]) == -1
    print("all checks passed")
