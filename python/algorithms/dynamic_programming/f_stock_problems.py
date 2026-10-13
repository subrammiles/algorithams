"""Buy/sell stock: one state machine for all variants.
hold' = max(hold, cash - price); cash' = max(cash, hold + price - fee)
once | unlimited | k transactions (one pair per transaction) | cooldown (buy uses cash from 2 days ago) | fee.
"""


def max_profit_once(prices):
    best, low = 0, float("inf")
    for p in prices:
        low = min(low, p)
        best = max(best, p - low)
    return best


def max_profit_unlimited(prices, fee=0):
    hold, cash = float("-inf"), 0
    for p in prices:
        hold, cash = max(hold, cash - p), max(cash, hold + p - fee)
    return cash


def max_profit_k(k, prices):
    if k >= len(prices) // 2:
        return max_profit_unlimited(prices)
    hold, cash = [float("-inf")] * (k + 1), [0] * (k + 1)
    for p in prices:
        for t in range(k, 0, -1):
            cash[t] = max(cash[t], hold[t] + p)
            hold[t] = max(hold[t], cash[t - 1] - p)
    return cash[k]


def max_profit_cooldown(prices):
    hold, cash, cash_two_days_ago = float("-inf"), 0, 0
    for p in prices:
        hold, cash, cash_two_days_ago = max(hold, cash_two_days_ago - p), max(cash, hold + p), cash
    return cash


if __name__ == "__main__":
    prices = [7, 1, 5, 3, 6, 4]
    assert max_profit_once(prices) == 5 and max_profit_unlimited(prices) == 7
    assert max_profit_k(2, [3, 3, 5, 0, 0, 3, 1, 4]) == 6
    assert max_profit_k(2, [3, 2, 6, 5, 0, 3]) == 7 and max_profit_k(100, prices) == 7
    assert max_profit_cooldown([1, 2, 3, 0, 2]) == 3
    assert max_profit_unlimited([1, 3, 2, 8, 4, 9], fee=2) == 8
    assert max_profit_once([7, 6, 4, 3, 1]) == 0
    print("all checks passed")
