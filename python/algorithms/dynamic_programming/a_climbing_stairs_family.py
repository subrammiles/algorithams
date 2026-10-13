"""1D DP over "the last step": climbing stairs, min cost stairs, house robber II (circular:
max of excluding first or last), tribonacci, delete and earn (reduces to house robber over values).
"""


def climb_stairs(n):
    a, b = 1, 1
    for _ in range(n - 1):
        a, b = b, a + b
    return b


def min_cost_climbing_stairs(cost):
    prev2 = prev1 = 0
    for i in range(2, len(cost) + 1):
        prev2, prev1 = prev1, min(prev1 + cost[i - 1], prev2 + cost[i - 2])
    return prev1


def rob_linear(nums):
    prev2 = prev1 = 0
    for x in nums:
        prev2, prev1 = prev1, max(prev1, prev2 + x)
    return prev1


def rob_circular(nums):
    if len(nums) == 1:
        return nums[0]
    return max(rob_linear(nums[:-1]), rob_linear(nums[1:]))


def tribonacci(n):
    a, b, c = 0, 1, 1
    for _ in range(n):
        a, b, c = b, c, a + b + c
    return a


def delete_and_earn(nums):
    points = [0] * (max(nums) + 1)
    for x in nums:
        points[x] += x
    return rob_linear(points)


if __name__ == "__main__":
    assert climb_stairs(5) == 8 and climb_stairs(1) == 1
    assert min_cost_climbing_stairs([10, 15, 20]) == 15
    assert min_cost_climbing_stairs([1, 100, 1, 1, 1, 100, 1, 1, 100, 1]) == 6
    assert rob_circular([2, 3, 2]) == 3 and rob_circular([1, 2, 3, 1]) == 4 and rob_circular([1]) == 1
    assert tribonacci(25) == 1389537
    assert delete_and_earn([3, 4, 2]) == 6 and delete_and_earn([2, 2, 3, 3, 3, 4]) == 9
    print("all checks passed")
