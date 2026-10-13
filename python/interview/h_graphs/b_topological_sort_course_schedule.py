"""Course Schedule II: [a, b] means b before a. Return an order or [] on a cycle.

Kahn (BFS on in-degree 0) or DFS with 3 colours (grey -> grey edge = cycle). O(V + E).
"""
from collections import deque


def find_order_kahn(n, prerequisites):
    graph = [[] for _ in range(n)]
    indegree = [0] * n
    for a, b in prerequisites:
        graph[b].append(a)
        indegree[a] += 1
    queue = deque(i for i in range(n) if indegree[i] == 0)
    order = []
    while queue:
        u = queue.popleft()
        order.append(u)
        for v in graph[u]:
            indegree[v] -= 1
            if indegree[v] == 0:
                queue.append(v)
    return order if len(order) == n else []


def has_cycle_dfs(n, prerequisites):
    graph = [[] for _ in range(n)]
    for a, b in prerequisites:
        graph[b].append(a)
    color = [0] * n  # 0 white, 1 grey (on stack), 2 black

    def dfs(u):
        color[u] = 1
        for v in graph[u]:
            if color[v] == 1 or (color[v] == 0 and dfs(v)):
                return True
        color[u] = 2
        return False

    return any(color[i] == 0 and dfs(i) for i in range(n))


if __name__ == "__main__":
    pre = [[1, 0], [2, 0], [3, 1], [3, 2]]
    assert find_order_kahn(4, pre) == [0, 1, 2, 3]
    assert find_order_kahn(2, [[0, 1], [1, 0]]) == []
    assert not has_cycle_dfs(4, pre)
    assert has_cycle_dfs(3, [[1, 0], [2, 1], [0, 2]])
    print("all checks passed")
