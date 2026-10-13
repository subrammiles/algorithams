package k_dp

import "testing"

func TestOneDimensionalDP(t *testing.T) {
	if Rob([]int{2, 7, 9, 3, 1}) != 12 {
		t.Error("rob")
	}
	if CoinChangeMin([]int{1, 3, 4}, 6) != 2 || CoinChangeMin([]int{2}, 3) != -1 || CoinChangeWays([]int{1, 2, 5}, 5) != 4 {
		t.Error("coin change")
	}
	if NumDecodings("226") != 3 || NumDecodings("06") != 0 {
		t.Error("decode ways")
	}
}
