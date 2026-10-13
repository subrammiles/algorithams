package greedy

import "testing"

func TestArrayGreedy(t *testing.T) {
	if !CanJump([]int{2, 3, 1, 1, 4}) || CanJump([]int{3, 2, 1, 0, 4}) {
		t.Error("jump")
	}
	if FindContentChildren([]int{1, 2, 3}, []int{1, 1}) != 1 || FindContentChildren([]int{1, 2}, []int{1, 2, 3}) != 2 {
		t.Error("cookies")
	}
	if NumRescueBoats([]int{3, 2, 2, 1}, 3) != 3 || NumRescueBoats([]int{3, 5, 3, 4}, 5) != 4 {
		t.Error("boats")
	}
	if TwoCitySchedCost([][2]int{{10, 20}, {30, 200}, {400, 50}, {30, 20}}) != 110 {
		t.Error("two city")
	}
	if GreedyCoinCount([]int{25, 10, 5, 1}, 63) != 6 || GreedyCoinCount([]int{4, 3, 1}, 6) != 3 {
		t.Error("greedy coins")
	}
}
