"""Multi-source BFS: enqueue every source at distance 0, BFS once.
01 matrix | walls and gates | shortest bridge (mark island 1, BFS until island 2).
"""
from collections import deque

DIRS = ((1, 0), (-1, 0), (0, 1), (0, -1))
INF = 2147483647


def update_matrix(mat):
    rows, cols = len(mat), len(mat[0])
    dist = [[0 if v == 0 else -1 for v in row] for row in mat]
    queue = deque((r, c) for r in range(rows) for c in range(cols) if mat[r][c] == 0)
    while queue:
        r, c = queue.popleft()
        for dr, dc in DIRS:
            nr, nc = r + dr, c + dc
            if 0 <= nr < rows and 0 <= nc < cols and dist[nr][nc] == -1:
                dist[nr][nc] = dist[r][c] + 1
                queue.append((nr, nc))
    return dist


def walls_and_gates(rooms):
    queue = deque((r, c) for r, row in enumerate(rooms) for c, v in enumerate(row) if v == 0)
    while queue:
        r, c = queue.popleft()
        for dr, dc in DIRS:
            nr, nc = r + dr, c + dc
            if 0 <= nr < len(rooms) and 0 <= nc < len(rooms[0]) and rooms[nr][nc] == INF:
                rooms[nr][nc] = rooms[r][c] + 1
                queue.append((nr, nc))


def shortest_bridge(grid):
    n = len(grid)
    g = [row[:] for row in grid]
    start = next((r, c) for r in range(n) for c in range(n) if g[r][c] == 1)
    queue, stack = deque(), [start]
    g[start[0]][start[1]] = 2
    while stack:  # mark the first island
        r, c = stack.pop()
        queue.append((r, c))
        for dr, dc in DIRS:
            nr, nc = r + dr, c + dc
            if 0 <= nr < n and 0 <= nc < n and g[nr][nc] == 1:
                g[nr][nc] = 2
                stack.append((nr, nc))
    steps = 0
    while queue:
        for _ in range(len(queue)):
            r, c = queue.popleft()
            for dr, dc in DIRS:
                nr, nc = r + dr, c + dc
                if 0 <= nr < n and 0 <= nc < n and g[nr][nc] != 2:
                    if g[nr][nc] == 1:
                        return steps
                    g[nr][nc] = 2
                    queue.append((nr, nc))
        steps += 1
    return -1


if __name__ == "__main__":
    assert update_matrix([[0, 0, 0], [0, 1, 0], [1, 1, 1]]) == [[0, 0, 0], [0, 1, 0], [1, 2, 1]]
    rooms = [[INF, -1, 0, INF], [INF, INF, INF, -1], [INF, -1, INF, -1], [0, -1, INF, INF]]
    walls_and_gates(rooms)
    assert rooms == [[3, -1, 0, 1], [2, 2, 1, -1], [1, -1, 2, -1], [0, -1, 3, 4]]
    assert shortest_bridge([[0, 1], [1, 0]]) == 1
    assert shortest_bridge([[0, 1, 0], [0, 0, 0], [0, 0, 1]]) == 2
    assert shortest_bridge([[1, 1, 1, 1, 1], [1, 0, 0, 0, 1], [1, 0, 1, 0, 1], [1, 0, 0, 0, 1], [1, 1, 1, 1, 1]]) == 1
    print("all checks passed")
