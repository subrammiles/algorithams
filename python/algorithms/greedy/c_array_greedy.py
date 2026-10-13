"""Array greedy: jump game, assign cookies, boats to save people, two city scheduling.
Greedy coin change is only correct for canonical coin systems: {1,3,4} amount 6 -> greedy 3 coins, optimal 2.
"""


def can_jump(nums):
    furthest = 0
    for i, n in enumerate(nums):
        if i > furthest:
            return False
        furthest = max(furthest, i + n)
    return True


def find_content_children(greed, cookies):
    g, s = sorted(greed), sorted(cookies)
    child = 0
    for cookie in s:
        if child < len(g) and cookie >= g[child]:
            child += 1
    return child


def num_rescue_boats(people, limit):
    p = sorted(people)
    lo, hi, boats = 0, len(p) - 1, 0
    while lo <= hi:
        if p[lo] + p[hi] <= limit:
            lo += 1
        hi, boats = hi - 1, boats + 1
    return boats


def two_city_sched_cost(costs):
    ordered = sorted(costs, key=lambda c: c[0] - c[1])
    half = len(costs) // 2
    return sum(c[0] for c in ordered[:half]) + sum(c[1] for c in ordered[half:])


def greedy_coin_count(coins_descending, amount):
    count = 0
    for coin in coins_descending:
        count, amount = count + amount // coin, amount % coin
    return count if amount == 0 else -1


if __name__ == "__main__":
    assert can_jump([2, 3, 1, 1, 4]) and not can_jump([3, 2, 1, 0, 4])
    assert find_content_children([1, 2, 3], [1, 1]) == 1 and find_content_children([1, 2], [1, 2, 3]) == 2
    assert num_rescue_boats([3, 2, 2, 1], 3) == 3 and num_rescue_boats([3, 5, 3, 4], 5) == 4
    assert two_city_sched_cost([[10, 20], [30, 200], [400, 50], [30, 20]]) == 110
    assert greedy_coin_count([25, 10, 5, 1], 63) == 6
    assert greedy_coin_count([4, 3, 1], 6) == 3  # not optimal: 3 + 3 uses 2 coins
    print("all checks passed")
