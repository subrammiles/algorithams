"""Greedy classics - be ready to justify the greedy choice (exchange argument).

Jump game II: BFS by levels. Gas station: restart after a negative tank.
Task scheduler: max(len, (max_freq - 1) * (n + 1) + count_of_max). Candy: two passes.
"""
from collections import Counter


def jump(nums):
    jumps = current_end = farthest = 0
    for i in range(len(nums) - 1):
        farthest = max(farthest, i + nums[i])
        if i == current_end:
            jumps, current_end = jumps + 1, farthest
    return jumps


def can_complete_circuit(gas, cost):
    total = tank = start = 0
    for i, (g, c) in enumerate(zip(gas, cost)):
        total += g - c
        tank += g - c
        if tank < 0:
            start, tank = i + 1, 0
    return start if total >= 0 else -1


def least_interval(tasks, n):
    freq = Counter(tasks).values()
    top = max(freq)
    return max(len(tasks), (top - 1) * (n + 1) + sum(f == top for f in freq))


def candy(ratings):
    c = [1] * len(ratings)
    for i in range(1, len(ratings)):
        if ratings[i] > ratings[i - 1]:
            c[i] = c[i - 1] + 1
    for i in range(len(ratings) - 2, -1, -1):
        if ratings[i] > ratings[i + 1]:
            c[i] = max(c[i], c[i + 1] + 1)
    return sum(c)


if __name__ == "__main__":
    assert jump([2, 3, 1, 1, 4]) == 2
    assert can_complete_circuit([1, 2, 3, 4, 5], [3, 4, 5, 1, 2]) == 3
    assert can_complete_circuit([2, 3, 4], [3, 4, 3]) == -1
    assert least_interval("AAABBB", 2) == 8 and least_interval("AAABBBCCDD", 2) == 10
    assert candy([1, 0, 2]) == 5
    print("all checks passed")
