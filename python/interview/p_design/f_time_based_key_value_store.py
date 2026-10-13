"""Time-based key-value store: get(key, t) returns the value with the largest timestamp <= t.
Timestamps arrive increasing, so per key keep parallel sorted lists and use bisect. O(log n) get.
(This is a tiny version of MVCC / snapshot reads.)
"""
import bisect
from collections import defaultdict


class TimeMap:
    def __init__(self):
        self.times = defaultdict(list)
        self.values = defaultdict(list)

    def set(self, key, value, timestamp):
        self.times[key].append(timestamp)
        self.values[key].append(value)

    def get(self, key, timestamp):
        i = bisect.bisect_right(self.times[key], timestamp)
        return self.values[key][i - 1] if i else ""


if __name__ == "__main__":
    kv = TimeMap()
    kv.set("foo", "bar", 1)
    assert kv.get("foo", 1) == "bar" and kv.get("foo", 3) == "bar"
    kv.set("foo", "bar2", 4)
    assert kv.get("foo", 4) == "bar2"
    assert kv.get("foo", 0) == "" and kv.get("nope", 5) == ""
    print("all checks passed")
