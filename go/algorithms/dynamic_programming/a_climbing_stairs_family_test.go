package dynamic_programming

import "testing"

func TestClimbingStairsFamily(t *testing.T) {
	if ClimbStairs(5) != 8 || ClimbStairs(1) != 1 {
		t.Error("stairs")
	}
	if MinCostClimbingStairs([]int{10, 15, 20}) != 15 || MinCostClimbingStairs([]int{1, 100, 1, 1, 1, 100, 1, 1, 100, 1}) != 6 {
		t.Error("min cost")
	}
	if RobCircular([]int{2, 3, 2}) != 3 || RobCircular([]int{1, 2, 3, 1}) != 4 || RobCircular([]int{1}) != 1 {
		t.Error("rob circular")
	}
	if Tribonacci(25) != 1389537 || DeleteAndEarn([]int{3, 4, 2}) != 6 || DeleteAndEarn([]int{2, 2, 3, 3, 3, 4}) != 9 {
		t.Error("tribonacci / delete and earn")
	}
}
