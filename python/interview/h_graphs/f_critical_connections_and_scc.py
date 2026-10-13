"""Bridges (Tarjan low-link) and strongly connected components (Kosaraju). Both O(V + E).

Bridge (u, v): low[v] > disc[u]. Articulation point: low[v] >= disc[u].
Kosaraju: DFS for finish order, reverse edges, DFS in decreasing finish order.
"""
import sys

sys.setrecursionlimit(10_000)


def critical_connections(n, edges):
    graph = [[] for _ in range(n)]
    for u, v in edges:
        graph[u].append(v)
        graph[v].append(u)
    disc, low, bridges, timer = [-1] * n, [0] * n, [], [0]

    def dfs(u, parent):
        disc[u] = low[u] = timer[0]
        timer[0] += 1
        for v in graph[u]:
            if v == parent:
                continue
            if disc[v] == -1:
                dfs(v, u)
                low[u] = min(low[u], low[v])
                if low[v] > disc[u]:
                    bridges.append(sorted((u, v)))
            else:
                low[u] = min(low[u], disc[v])  # back edge

    for i in range(n):
        if disc[i] == -1:
            dfs(i, -1)
    return bridges


def count_scc(n, edges):
    graph, rev = [[] for _ in range(n)], [[] for _ in range(n)]
    for u, v in edges:
        graph[u].append(v)
        rev[v].append(u)
    seen, order = [False] * n, []

    def fill(u):
        seen[u] = True
        for v in graph[u]:
            if not seen[v]:
                fill(v)
        order.append(u)

    def mark(u):
        seen[u] = True
        for v in rev[u]:
            if not seen[v]:
                mark(v)

    for i in range(n):
        if not seen[i]:
            fill(i)
    seen = [False] * n
    components = 0
    for u in reversed(order):
        if not seen[u]:
            components += 1
            mark(u)
    return components


if __name__ == "__main__":
    assert critical_connections(4, [[0, 1], [1, 2], [2, 0], [1, 3]]) == [[1, 3]]
    assert count_scc(5, [[1, 0], [0, 2], [2, 1], [0, 3], [3, 4]]) == 3
    print("all checks passed")
