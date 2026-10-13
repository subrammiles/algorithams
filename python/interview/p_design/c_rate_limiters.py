"""Rate limiters (time passed in explicitly so tests are deterministic).

Token bucket: refill at a steady rate up to capacity; allows bursts. O(1) memory per user.
Sliding window log: exact; O(limit) memory per user.
Mention: fixed window counter (2x burst at edges), sliding window counter (approximation),
Redis + Lua for a distributed limiter, HTTP 429 + Retry-After.
"""
import threading
from collections import defaultdict, deque


class TokenBucket:
    def __init__(self, capacity, refill_per_second, now_ms):
        self.capacity = capacity
        self.refill_per_ms = refill_per_second / 1000
        self.tokens = float(capacity)
        self.last = now_ms
        self.lock = threading.Lock()

    def allow(self, now_ms):
        with self.lock:
            self.tokens = min(self.capacity, self.tokens + (now_ms - self.last) * self.refill_per_ms)
            self.last = now_ms
            if self.tokens >= 1:
                self.tokens -= 1
                return True
            return False


class SlidingWindowLog:
    def __init__(self, limit, window_ms):
        self.limit, self.window_ms = limit, window_ms
        self.logs = defaultdict(deque)
        self.lock = threading.Lock()

    def allow(self, user, now_ms):
        with self.lock:
            log = self.logs[user]
            while log and log[0] <= now_ms - self.window_ms:
                log.popleft()
            if len(log) < self.limit:
                log.append(now_ms)
                return True
            return False


if __name__ == "__main__":
    b = TokenBucket(3, 1, 0)
    assert b.allow(0) and b.allow(0) and b.allow(0)
    assert not b.allow(0) and not b.allow(500)
    assert b.allow(1000)

    w = SlidingWindowLog(2, 1000)
    assert w.allow("u1", 0) and w.allow("u1", 100)
    assert not w.allow("u1", 900)
    assert w.allow("u2", 900)
    assert w.allow("u1", 1000)
    print("all checks passed")
