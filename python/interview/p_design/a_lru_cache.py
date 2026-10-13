"""LRU cache with O(1) get/put: dict key -> node + doubly linked list ordered by recency.

Pythonic alternative: collections.OrderedDict with move_to_end / popitem(last=False),
or functools.lru_cache for memoising functions.
Follow-ups: thread safety (lock / striping), TTL expiry, distributed cache (consistent hashing).
"""
from collections import OrderedDict


class _Node:
    __slots__ = ("key", "value", "prev", "next")

    def __init__(self, key=0, value=0):
        self.key, self.value = key, value
        self.prev = self.next = None


class LRUCache:
    def __init__(self, capacity):
        self.capacity = capacity
        self.map = {}
        self.head, self.tail = _Node(), _Node()  # sentinels
        self.head.next, self.tail.prev = self.tail, self.head

    def _unlink(self, n):
        n.prev.next, n.next.prev = n.next, n.prev

    def _add_front(self, n):
        n.prev, n.next = self.head, self.head.next
        self.head.next.prev = n
        self.head.next = n

    def get(self, key):
        n = self.map.get(key)
        if n is None:
            return -1
        self._unlink(n)
        self._add_front(n)
        return n.value

    def put(self, key, value):
        if key in self.map:
            n = self.map[key]
            n.value = value
            self._unlink(n)
            self._add_front(n)
            return
        if len(self.map) == self.capacity:
            lru = self.tail.prev
            self._unlink(lru)
            del self.map[lru.key]
        n = _Node(key, value)
        self.map[key] = n
        self._add_front(n)


class OrderedDictLRU:
    def __init__(self, capacity):
        self.capacity, self.data = capacity, OrderedDict()

    def get(self, key):
        if key not in self.data:
            return -1
        self.data.move_to_end(key)
        return self.data[key]

    def put(self, key, value):
        self.data[key] = value
        self.data.move_to_end(key)
        if len(self.data) > self.capacity:
            self.data.popitem(last=False)


if __name__ == "__main__":
    for cls in (LRUCache, OrderedDictLRU):
        c = cls(2)
        c.put(1, 1)
        c.put(2, 2)
        assert c.get(1) == 1
        c.put(3, 3)  # evicts 2
        assert c.get(2) == -1
        c.put(4, 4)  # evicts 1
        assert c.get(1) == -1 and c.get(3) == 3 and c.get(4) == 4
    print("all checks passed")
