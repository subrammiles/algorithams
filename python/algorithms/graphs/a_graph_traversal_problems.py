"""Graph traversal problems: clone graph (map original -> copy, register before recursing),
valid tree (n-1 edges + connected), connected components, all source->target paths in a DAG.
"""


class Node:
    def __init__(self, val):
        self.val = val
        self.neighbors = []


def clone_graph(node, copies=None):
    if node is None:
        return None
    copies = {} if copies is None else copies
    if node in copies:
        return copies[node]
    copy = copies[node] = Node(node.val)
    copy.neighbors = [clone_graph(nb, copies) for nb in node.neighbors]
    return copy


def count_components(n, edges):
    graph = [[] for _ in range(n)]
    for u, v in edges:
        graph[u].append(v)
        graph[v].append(u)
    seen, components = set(), 0
    for s in range(n):
        if s in seen:
            continue
        components += 1
        stack = [s]
        seen.add(s)
        while stack:
            for v in graph[stack.pop()]:
                if v not in seen:
                    seen.add(v)
                    stack.append(v)
    return components


def valid_tree(n, edges):
    return len(edges) == n - 1 and count_components(n, edges) == 1


def all_paths_source_target(graph):
    target, paths = len(graph) - 1, []

    def go(u, path):
        if u == target:
            paths.append(path[:])
            return
        for v in graph[u]:
            path.append(v)
            go(v, path)
            path.pop()

    go(0, [0])
    return paths


if __name__ == "__main__":
    nodes = [Node(i) for i in (1, 2, 3, 4)]
    for i, n in enumerate(nodes):
        n.neighbors = [nodes[(i + 1) % 4], nodes[(i + 3) % 4]]
    copy = clone_graph(nodes[0])
    assert copy is not nodes[0] and copy.val == 1
    assert [nb.val for nb in copy.neighbors] == [2, 4]
    assert copy.neighbors[0].neighbors[0].neighbors[0].neighbors[0] is copy
    assert valid_tree(5, [[0, 1], [0, 2], [0, 3], [1, 4]])
    assert not valid_tree(5, [[0, 1], [1, 2], [2, 3], [1, 3], [1, 4]])
    assert count_components(5, [[0, 1], [1, 2], [3, 4]]) == 2
    assert all_paths_source_target([[1, 2], [3], [3], []]) == [[0, 1, 3], [0, 2, 3]]
    print("all checks passed")
