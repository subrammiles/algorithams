"""Evaluate Division with weighted union-find: weight[x] = x / parent(x).
find compresses paths and multiplies weights; a / b = weight[a] / weight[b] when roots match.
"""


def calc_equation(equations, values, queries):
    parent, weight = {}, {}

    def find(x):
        if parent[x] != x:
            root = find(parent[x])
            weight[x] *= weight[parent[x]]  # x/p * p/root
            parent[x] = root
        return parent[x]

    for (a, b), value in zip(equations, values):
        for x in (a, b):
            if x not in parent:
                parent[x], weight[x] = x, 1.0
        ra, rb = find(a), find(b)
        if ra != rb:
            parent[ra] = rb
            weight[ra] = value * weight[b] / weight[a]

    out = []
    for a, b in queries:
        if a not in parent or b not in parent or find(a) != find(b):
            out.append(-1.0)
        else:
            out.append(weight[a] / weight[b])
    return out


if __name__ == "__main__":
    got = calc_equation([["a", "b"], ["b", "c"]], [2.0, 3.0], [["a", "c"], ["b", "a"], ["a", "e"], ["a", "a"], ["x", "x"]])
    assert all(abs(x - y) < 1e-9 for x, y in zip(got, [6.0, 0.5, -1.0, 1.0, -1.0]))
    chain = calc_equation([["a", "b"], ["c", "d"], ["b", "c"]], [1.5, 2.5, 5.0], [["a", "d"], ["d", "a"]])
    assert abs(chain[0] - 18.75) < 1e-9 and abs(chain[1] - 1 / 18.75) < 1e-9
    print("all checks passed")
