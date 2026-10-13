"""Consistent hashing ring with virtual nodes.

hash(key) % N remaps almost every key when N changes; on a ring only ~1/N of keys move.
bisect on the sorted ring positions gives the first node clockwise in O(log(nodes * replicas)).
"""
import bisect
import hashlib


def _hash(s):
    return int.from_bytes(hashlib.md5(s.encode()).digest()[:4], "little")


class ConsistentHashRing:
    def __init__(self, virtual_nodes=100):
        self.virtual_nodes = virtual_nodes
        self.positions, self.owner = [], {}

    def add_node(self, node):
        for i in range(self.virtual_nodes):
            h = _hash(f"{node}#{i}")
            bisect.insort(self.positions, h)
            self.owner[h] = node

    def remove_node(self, node):
        for i in range(self.virtual_nodes):
            h = _hash(f"{node}#{i}")
            self.positions.remove(h)
            del self.owner[h]

    def node_for(self, key):
        i = bisect.bisect_left(self.positions, _hash(key)) % len(self.positions)  # wrap around
        return self.owner[self.positions[i]]


if __name__ == "__main__":
    ring = ConsistentHashRing()
    for n in ("cache-A", "cache-B", "cache-C"):
        ring.add_node(n)
    keys = [f"user:{i}" for i in range(10_000)]
    before = {k: ring.node_for(k) for k in keys}
    load = {}
    for node in before.values():
        load[node] = load.get(node, 0) + 1
    print("load with 3 nodes:", load)
    assert all(2_000 < c < 5_000 for c in load.values())
    ring.add_node("cache-D")
    moved = [k for k in keys if ring.node_for(k) != before[k]]
    print(f"moved after adding a 4th node: {len(moved)} of {len(keys)}")
    assert all(ring.node_for(k) == "cache-D" for k in moved)
    assert len(moved) < len(keys) // 2
    print("all checks passed")
