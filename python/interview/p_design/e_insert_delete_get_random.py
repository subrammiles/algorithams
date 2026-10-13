"""Set with O(1) insert, remove, get_random: list for random index + dict value -> index.
Remove: overwrite the slot with the last element, fix its index, pop the last slot.
"""
import random


class RandomizedSet:
    def __init__(self, seed=None):
        self.values, self.index = [], {}
        self.random = random.Random(seed)

    def insert(self, val):
        if val in self.index:
            return False
        self.index[val] = len(self.values)
        self.values.append(val)
        return True

    def remove(self, val):
        if val not in self.index:
            return False
        i, last = self.index.pop(val), self.values.pop()
        if i < len(self.values):
            self.values[i], self.index[last] = last, i
        return True

    def get_random(self):
        return self.random.choice(self.values)


if __name__ == "__main__":
    s = RandomizedSet(seed=7)
    assert s.insert(1) and not s.remove(2) and s.insert(2) and s.insert(3) and not s.insert(2)
    assert s.remove(1)
    seen = [s.get_random() for _ in range(1000)]
    assert 1 not in seen and seen.count(2) > 300 and seen.count(3) > 300
    print("all checks passed")
