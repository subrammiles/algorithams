"""Shortest-path variants: 0-1 BFS (deque; 0-edges to the front), max-probability path
(Dijkstra on products), swim in rising water (minimax Dijkstra), A* with a Manhattan heuristic.
"""
import heapq
from collections import deque

DIRS = ((1, 0), (-1, 0), (0, 1), (0, -1))


def minimum_obstacles(grid):
    rows, cols = len(grid), len(grid[0])
    dist = [[float("inf")] * cols for _ in range(rows)]
    dist[0][0] = 0
    dq = deque([(0, 0)])
    while dq:
        r, c = dq.popleft()
        for dr, dc in DIRS:
            nr, nc = r + dr, c + dc
            if 0 <= nr < rows and 0 <= nc < cols and dist[r][c] + grid[nr][nc] < dist[nr][nc]:
                dist[nr][nc] = dist[r][c] + grid[nr][nc]
                (dq.appendleft if grid[nr][nc] == 0 else dq.append)((nr, nc))
    return dist[-1][-1]


def max_probability(n, edges, prob, start, end):
    graph = [[] for _ in range(n)]
    for (u, v), p in zip(edges, prob):
        graph[u].append((v, p))
        graph[v].append((u, p))
    best = [0.0] * n
    best[start] = 1.0
    heap = [(-1.0, start)]  # negate for a max-heap
    while heap:
        p, u = heapq.heappop(heap)
        p = -p
        if u == end:
            return p
        if p < best[u]:
            continue
        for v, w in graph[u]:
            if p * w > best[v]:
                best[v] = p * w
                heapq.heappush(heap, (-best[v], v))
    return 0.0


def swim_in_water(grid):
    n = len(grid)
    seen = {(0, 0)}
    heap = [(grid[0][0], 0, 0)]
    while heap:
        t, r, c = heapq.heappop(heap)
        if (r, c) == (n - 1, n - 1):
            return t
        for dr, dc in DIRS:
            nr, nc = r + dr, c + dc
            if 0 <= nr < n and 0 <= nc < n and (nr, nc) not in seen:
                seen.add((nr, nc))
                heapq.heappush(heap, (max(t, grid[nr][nc]), nr, nc))
    return -1


def a_star(grid):
    """Fewest steps from top-left to bottom-right; '#' is a wall."""
    rows, cols = len(grid), len(grid[0])
    goal = (rows - 1, cols - 1)
    if grid[0][0] == "#":
        return -1

    def h(r, c):
        return goal[0] - r + goal[1] - c

    g = {(0, 0): 0}
    open_heap = [(h(0, 0), 0, 0)]
    while open_heap:
        f, r, c = heapq.heappop(open_heap)
        if (r, c) == goal:
            return g[(r, c)]
        if f > g[(r, c)] + h(r, c):
            continue  # stale
        for dr, dc in DIRS:
            nr, nc = r + dr, c + dc
            if 0 <= nr < rows and 0 <= nc < cols and grid[nr][nc] != "#" and g[(r, c)] + 1 < g.get((nr, nc), float("inf")):
                g[(nr, nc)] = g[(r, c)] + 1
                heapq.heappush(open_heap, (g[(nr, nc)] + h(nr, nc), nr, nc))
    return -1


if __name__ == "__main__":
    assert minimum_obstacles([[0, 1, 1], [1, 1, 0], [1, 1, 0]]) == 2
    assert minimum_obstacles([[0, 1, 0, 0, 0], [0, 1, 0, 1, 0], [0, 0, 0, 1, 0]]) == 0
    edges = [[0, 1], [1, 2], [0, 2]]
    assert abs(max_probability(3, edges, [0.5, 0.5, 0.2], 0, 2) - 0.25) < 1e-9
    assert abs(max_probability(3, edges, [0.5, 0.5, 0.3], 0, 2) - 0.3) < 1e-9
    assert swim_in_water([[0, 2], [1, 3]]) == 3
    assert swim_in_water([[0, 1, 2, 3, 4], [24, 23, 22, 21, 5], [12, 13, 14, 15, 16], [11, 17, 18, 19, 20], [10, 9, 8, 7, 6]]) == 16
    assert a_star([".....", ".###.", "...#.", "##.#.", "....."]) == 8
    assert a_star([".....", "####.", ".....", ".####", "....."]) == 16
    assert a_star([".#", "#."]) == -1
    print("all checks passed")
