"""Max flow (Edmonds-Karp: BFS augmenting paths in the residual graph, O(V E^2)) and bipartite
matching (Kuhn's augmenting paths, O(V E)). Max-flow = min-cut.
"""
from collections import deque


def max_flow(capacity, source, sink):
    n = len(capacity)
    residual = [row[:] for row in capacity]
    flow = 0
    while True:
        parent = [-1] * n
        parent[source] = source
        queue = deque([source])
        while queue and parent[sink] == -1:
            u = queue.popleft()
            for v in range(n):
                if parent[v] == -1 and residual[u][v] > 0:
                    parent[v] = u
                    queue.append(v)
        if parent[sink] == -1:
            return flow
        bottleneck, v = float("inf"), sink
        while v != source:
            bottleneck = min(bottleneck, residual[parent[v]][v])
            v = parent[v]
        v = sink
        while v != source:
            residual[parent[v]][v] -= bottleneck
            residual[v][parent[v]] += bottleneck  # reverse edge allows undoing
            v = parent[v]
        flow += bottleneck


def max_bipartite_matching(adj, right_count):
    match_of_right = [-1] * right_count

    def try_augment(left, seen):
        for right in adj[left]:
            if right in seen:
                continue
            seen.add(right)
            if match_of_right[right] == -1 or try_augment(match_of_right[right], seen):
                match_of_right[right] = left
                return True
        return False

    return sum(try_augment(left, set()) for left in range(len(adj)))


if __name__ == "__main__":
    cap = [[0, 16, 13, 0, 0, 0], [0, 0, 10, 12, 0, 0], [0, 4, 0, 0, 14, 0], [0, 0, 9, 0, 0, 20], [0, 0, 0, 7, 0, 4], [0] * 6]
    assert max_flow(cap, 0, 5) == 23
    assert max_bipartite_matching([[0, 1], [0], [0]], 2) == 2
    assert max_bipartite_matching([[0, 1], [0, 2], [1]], 3) == 3
    print("all checks passed")
