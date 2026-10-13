"""Tarjan low-link: articulation points (root with >= 2 children, or low[child] >= disc[u])
and strongly connected components in ONE DFS (pop the stack when low[u] == disc[u]). O(V + E).
"""
import sys

sys.setrecursionlimit(10_000)


def articulation_points(n, edges):
    graph = [[] for _ in range(n)]
    for u, v in edges:
        graph[u].append(v)
        graph[v].append(u)
    disc, low, cut, timer = [-1] * n, [0] * n, set(), [0]

    def dfs(u, parent):
        disc[u] = low[u] = timer[0]
        timer[0] += 1
        children = 0
        for v in graph[u]:
            if v == parent:
                continue
            if disc[v] == -1:
                children += 1
                dfs(v, u)
                low[u] = min(low[u], low[v])
                if parent != -1 and low[v] >= disc[u]:
                    cut.add(u)
            else:
                low[u] = min(low[u], disc[v])
        if parent == -1 and children >= 2:
            cut.add(u)

    for i in range(n):
        if disc[i] == -1:
            dfs(i, -1)
    return sorted(cut)


def tarjan_scc(n, edges):
    graph = [[] for _ in range(n)]
    for u, v in edges:
        graph[u].append(v)
    disc, low, on_stack, stack, components, timer = [-1] * n, [0] * n, [False] * n, [], [], [0]

    def dfs(u):
        disc[u] = low[u] = timer[0]
        timer[0] += 1
        stack.append(u)
        on_stack[u] = True
        for v in graph[u]:
            if disc[v] == -1:
                dfs(v)
                low[u] = min(low[u], low[v])
            elif on_stack[v]:
                low[u] = min(low[u], disc[v])
        if low[u] == disc[u]:  # u roots a component
            component = []
            while True:
                v = stack.pop()
                on_stack[v] = False
                component.append(v)
                if v == u:
                    break
            components.append(sorted(component))

    for i in range(n):
        if disc[i] == -1:
            dfs(i)
    return sorted(components)


if __name__ == "__main__":
    edges = [[1, 0], [0, 2], [2, 1], [0, 3], [3, 4]]
    assert articulation_points(5, edges) == [0, 3]
    assert articulation_points(3, [[0, 1], [1, 2], [2, 0]]) == []
    assert tarjan_scc(5, edges) == [[0, 1, 2], [3], [4]]
    print("all checks passed")
