// Package dynamic_programming: the classic DP problem families (see the Java README for the map).
package dynamic_programming

func ClimbStairs(n int) int {
	a, b := 1, 1
	for i := 2; i <= n; i++ {
		a, b = b, a+b
	}
	return b
}

func MinCostClimbingStairs(cost []int) int {
	prev2, prev1 := 0, 0
	for i := 2; i <= len(cost); i++ {
		prev2, prev1 = prev1, min(prev1+cost[i-1], prev2+cost[i-2])
	}
	return prev1
}

func robLinear(nums []int) int {
	prev2, prev1 := 0, 0
	for _, x := range nums {
		prev2, prev1 = prev1, max(prev1, prev2+x)
	}
	return prev1
}

// RobCircular: first and last houses are neighbours, so skip one of them.
func RobCircular(nums []int) int {
	if len(nums) == 1 {
		return nums[0]
	}
	return max(robLinear(nums[:len(nums)-1]), robLinear(nums[1:]))
}

func Tribonacci(n int) int {
	a, b, c := 0, 1, 1
	for i := 0; i < n; i++ {
		a, b, c = b, c, a+b+c
	}
	return a
}

// DeleteAndEarn reduces to House Robber over values.
func DeleteAndEarn(nums []int) int {
	maxV := 0
	for _, x := range nums {
		maxV = max(maxV, x)
	}
	points := make([]int, maxV+1)
	for _, x := range nums {
		points[x] += x
	}
	return robLinear(points)
}
