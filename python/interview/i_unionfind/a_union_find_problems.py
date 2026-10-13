"""Union-find for connectivity that only grows: redundant connection, accounts merge.
Path compression + union by rank: ~O(1) amortised. Cannot handle deletions efficiently.
"""
from collections import defaultdict


class DSU:
    def __init__(self, n):
        self.parent, self.rank, self.components = list(range(n)), [0] * n, n

    def find(self, x):
        while self.parent[x] != x:
            self.parent[x] = self.parent[self.parent[x]]
            x = self.parent[x]
        return x

    def union(self, a, b):
        ra, rb = self.find(a), self.find(b)
        if ra == rb:
            return False
        if self.rank[ra] < self.rank[rb]:
            ra, rb = rb, ra
        self.parent[rb] = ra
        if self.rank[ra] == self.rank[rb]:
            self.rank[ra] += 1
        self.components -= 1
        return True


def find_redundant_connection(edges):
    dsu = DSU(len(edges) + 1)
    for u, v in edges:
        if not dsu.union(u, v):
            return [u, v]
    return []


def accounts_merge(accounts):
    email_id, email_name = {}, {}
    for name, *emails in accounts:
        for e in emails:
            email_id.setdefault(e, len(email_id))
            email_name[e] = name
    dsu = DSU(len(email_id))
    for _, first, *rest in accounts:
        for e in rest:
            dsu.union(email_id[first], email_id[e])
    groups = defaultdict(list)
    for e, i in email_id.items():
        groups[dsu.find(i)].append(e)
    merged = [[email_name[es[0]]] + sorted(es) for es in groups.values()]
    return sorted(merged, key=lambda row: row[1])


if __name__ == "__main__":
    assert find_redundant_connection([[1, 2], [1, 3], [2, 3]]) == [2, 3]
    d = DSU(5)
    d.union(0, 1)
    d.union(3, 4)
    assert d.components == 3
    accounts = [["John", "johnsmith@mail.com", "john_newyork@mail.com"],
                ["John", "johnsmith@mail.com", "john00@mail.com"],
                ["Mary", "mary@mail.com"],
                ["John", "johnnybravo@mail.com"]]
    assert accounts_merge(accounts) == [
        ["John", "john00@mail.com", "john_newyork@mail.com", "johnsmith@mail.com"],
        ["John", "johnnybravo@mail.com"],
        ["Mary", "mary@mail.com"]]
    print("all checks passed")
