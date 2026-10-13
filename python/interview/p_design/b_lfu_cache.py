"""LFU cache, O(1): values, counts, buckets[count] = OrderedDict of keys (recency order), min_count.
Ties at the lowest count are broken by least recently used.
"""
from collections import OrderedDict, defaultdict


class LFUCache:
    def __init__(self, capacity):
        self.capacity = capacity
        self.values, self.counts = {}, {}
        self.buckets = defaultdict(OrderedDict)
        self.min_count = 0

    def _touch(self, key):
        c = self.counts[key]
        self.counts[key] = c + 1
        del self.buckets[c][key]
        if not self.buckets[c] and c == self.min_count:
            self.min_count += 1
        self.buckets[c + 1][key] = None

    def get(self, key):
        if key not in self.values:
            return -1
        self._touch(key)
        return self.values[key]

    def put(self, key, value):
        if self.capacity == 0:
            return
        if key in self.values:
            self.values[key] = value
            self._touch(key)
            return
        if len(self.values) == self.capacity:
            evict, _ = self.buckets[self.min_count].popitem(last=False)
            del self.values[evict], self.counts[evict]
        self.values[key], self.counts[key] = value, 1
        self.buckets[1][key] = None
        self.min_count = 1


if __name__ == "__main__":
    c = LFUCache(2)
    c.put(1, 1)
    c.put(2, 2)
    assert c.get(1) == 1
    c.put(3, 3)  # evicts 2
    assert c.get(2) == -1 and c.get(3) == 3
    c.put(4, 4)  # 1 and 3 tie at count 2 -> evict LRU = 1
    assert c.get(1) == -1 and c.get(3) == 3 and c.get(4) == 4
    print("all checks passed")
