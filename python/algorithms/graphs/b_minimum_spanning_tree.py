"""Min cost to connect all points (Manhattan): complete graph, so array-based Prim O(V^2) beats
Kruskal O(V^2 log V). Cut property: the cheapest edge across any cut is in some MST.
"""


def _dist(a, b):
    return abs(a[0] - b[0]) + abs(a[1] - b[1])


def min_cost_prim(points):
    n = len(points)
    best, in_tree, total = [float("inf")] * n, [False] * n, 0
    best[0] = 0
    for _ in range(n):
        u = min((v for v in range(n) if not in_tree[v]), key=best.__getitem__)
        in_tree[u] = True
        total += best[u]
        for v in range(n):
            if not in_tree[v]:
                best[v] = min(best[v], _dist(points[u], points[v]))
    return total


def min_cost_kruskal(points):
    n = len(points)
    edges = sorted((_dist(points[i], points[j]), i, j) for i in range(n) for j in range(i + 1, n))
    parent = list(range(n))

    def find(x):
        while parent[x] != x:
            parent[x] = parent[parent[x]]
            x = parent[x]
        return x

    total = used = 0
    for w, i, j in edges:
        a, b = find(i), find(j)
        if a != b:
            parent[a] = b
            total, used = total + w, used + 1
            if used == n - 1:
                break
    return total


if __name__ == "__main__":
    pts = [[0, 0], [2, 2], [3, 10], [5, 2], [7, 0]]
    assert min_cost_prim(pts) == min_cost_kruskal(pts) == 20
    pts2 = [[3, 12], [-2, 5], [-4, 1]]
    assert min_cost_prim(pts2) == min_cost_kruskal(pts2) == 18
    assert min_cost_prim([[1, 1]]) == 0
    print("all checks passed")
