"""Bounded blocking queue (producer-consumer) with one lock and two conditions.

Always wait in a while loop (spurious wake-ups, another thread may take the slot first).
Production code: queue.Queue. Note: the GIL stops parallel bytecode, not race conditions.
"""
import threading
from collections import deque


class BoundedBlockingQueue:
    def __init__(self, capacity):
        self.capacity = capacity
        self.items = deque()
        lock = threading.Lock()
        self.not_full = threading.Condition(lock)
        self.not_empty = threading.Condition(lock)

    def put(self, item):
        with self.not_full:
            while len(self.items) == self.capacity:
                self.not_full.wait()
            self.items.append(item)
            self.not_empty.notify()

    def take(self):
        with self.not_empty:
            while not self.items:
                self.not_empty.wait()
            item = self.items.popleft()
            self.not_full.notify()
            return item

    def size(self):
        with self.not_full:
            return len(self.items)


if __name__ == "__main__":
    q = BoundedBlockingQueue(5)
    producers, consumers, per_producer = 4, 4, 5_000
    totals = []
    totals_lock = threading.Lock()

    def produce():
        for i in range(1, per_producer + 1):
            q.put(i)

    def consume():
        s = sum(q.take() for _ in range(per_producer))
        with totals_lock:
            totals.append(s)

    threads = [threading.Thread(target=produce) for _ in range(producers)]
    threads += [threading.Thread(target=consume) for _ in range(consumers)]
    for t in threads:
        t.start()
    for t in threads:
        t.join(timeout=30)
    assert not any(t.is_alive() for t in threads), "deadlock"
    assert sum(totals) == producers * per_producer * (per_producer + 1) // 2
    assert q.size() == 0
    print("all checks passed")
