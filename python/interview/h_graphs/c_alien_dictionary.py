"""Alien dictionary: derive letter order from words sorted in an unknown alphabet.

First differing letter of each adjacent pair gives an edge; then topological sort.
Traps: "abc" before "ab" is invalid; letters without edges must still be output.
"""
from collections import deque


def alien_order(words):
    graph = {c: set() for w in words for c in w}
    indegree = {c: 0 for c in graph}
    for a, b in zip(words, words[1:]):
        if len(a) > len(b) and a.startswith(b):
            return ""
        for x, y in zip(a, b):
            if x != y:
                if y not in graph[x]:
                    graph[x].add(y)
                    indegree[y] += 1
                break
    queue = deque(sorted(c for c in graph if indegree[c] == 0))
    order = []
    while queue:
        c = queue.popleft()
        order.append(c)
        for nxt in sorted(graph[c]):
            indegree[nxt] -= 1
            if indegree[nxt] == 0:
                queue.append(nxt)
    return "".join(order) if len(order) == len(graph) else ""


if __name__ == "__main__":
    assert alien_order(["wrt", "wrf", "er", "ett", "rftt"]) == "wertf"
    assert alien_order(["z", "x", "z"]) == ""
    assert alien_order(["abc", "ab"]) == ""
    print("all checks passed")
