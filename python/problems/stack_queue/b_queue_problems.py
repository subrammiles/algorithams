"""Queue problems: stack using one queue (rotate after push), moving average (queue + running sum),
recent counter (evict pings older than 3000 ms).
"""
from collections import deque


class StackUsingQueue:
    def __init__(self):
        self.q = deque()

    def push(self, x):
        self.q.append(x)
        for _ in range(len(self.q) - 1):
            self.q.append(self.q.popleft())

    def pop(self):
        return self.q.popleft()

    def top(self):
        return self.q[0]

    def empty(self):
        return not self.q


class MovingAverage:
    def __init__(self, size):
        self.size, self.window, self.total = size, deque(), 0

    def next(self, val):
        self.window.append(val)
        self.total += val
        if len(self.window) > self.size:
            self.total -= self.window.popleft()
        return self.total / len(self.window)


class RecentCounter:
    def __init__(self):
        self.pings = deque()

    def ping(self, t):
        self.pings.append(t)
        while self.pings[0] < t - 3000:
            self.pings.popleft()
        return len(self.pings)


if __name__ == "__main__":
    s = StackUsingQueue()
    for x in (1, 2, 3):
        s.push(x)
    assert s.top() == 3 and s.pop() == 3 and s.pop() == 2 and not s.empty()
    m = MovingAverage(3)
    assert m.next(1) == 1 and m.next(10) == 5.5 and abs(m.next(3) - 14 / 3) < 1e-9 and m.next(5) == 6
    rc = RecentCounter()
    assert [rc.ping(t) for t in (1, 100, 3001, 3002)] == [1, 2, 3, 3]
    print("all checks passed")
