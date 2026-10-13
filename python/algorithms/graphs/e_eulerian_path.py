"""Eulerian path (every edge once) with Hierholzer: follow unused edges; when stuck, append the
vertex; reverse at the end. Reconstruct Itinerary from JFK, lexically smallest.
"""
import heapq
from collections import defaultdict


def find_itinerary(tickets):
    graph = defaultdict(list)
    for src, dst in tickets:
        heapq.heappush(graph[src], dst)
    route, stack = [], ["JFK"]
    while stack:
        if graph[stack[-1]]:
            stack.append(heapq.heappop(graph[stack[-1]]))
        else:
            route.append(stack.pop())
    return route[::-1]


if __name__ == "__main__":
    assert find_itinerary([["MUC", "LHR"], ["JFK", "MUC"], ["SFO", "SJC"], ["LHR", "SFO"]]) == ["JFK", "MUC", "LHR", "SFO", "SJC"]
    assert find_itinerary([["JFK", "SFO"], ["JFK", "ATL"], ["SFO", "ATL"], ["ATL", "JFK"], ["ATL", "SFO"]]) == ["JFK", "ATL", "JFK", "SFO", "ATL", "SFO"]
    assert find_itinerary([["JFK", "KUL"], ["JFK", "NRT"], ["NRT", "JFK"]]) == ["JFK", "NRT", "JFK", "KUL"]
    print("all checks passed")
