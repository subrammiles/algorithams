"""Shortest paths - pick by graph type:
unweighted BFS | 0/1 weights 0-1 BFS | non-negative Dijkstra | negative / <= K edges Bellman-Ford
| all pairs Floyd-Warshall | DAG topo order + relax.

Network delay time: Dijkstra with lazy deletion.
Cheapest flights within K stops: Bellman-Ford limited to k+1 rounds (copy distances per round).
"""
import heapq
from collections import defaultdict


def network_delay_time(times, n, source):
    graph = defaultdict(list)
    for u, v, w in times:
        graph[u].append((v, w))
    dist = {}
    heap = [(0, source)]
    while heap:
        d, u = heapq.heappop(heap)
        if u in dist:
            continue  # stale entry
        dist[u] = d
        for v, w in graph[u]:
            if v not in dist:
                heapq.heappush(heap, (d + w, v))
    return max(dist.values()) if len(dist) == n else -1


def find_cheapest_price(n, flights, src, dst, k):
    cost = [float("inf")] * n
    cost[src] = 0
    for _ in range(k + 1):
        nxt = cost[:]
        for u, v, w in flights:
            if cost[u] + w < nxt[v]:
                nxt[v] = cost[u] + w
        cost = nxt
    return -1 if cost[dst] == float("inf") else cost[dst]


if __name__ == "__main__":
    assert network_delay_time([[2, 1, 1], [2, 3, 1], [3, 4, 1]], 4, 2) == 2
    assert network_delay_time([[1, 2, 1]], 2, 2) == -1
    flights = [[0, 1, 100], [1, 2, 100], [2, 0, 100], [1, 3, 600], [2, 3, 200]]
    assert find_cheapest_price(4, flights, 0, 3, 1) == 700
    assert find_cheapest_price(4, flights, 0, 3, 2) == 400
    print("all checks passed")
