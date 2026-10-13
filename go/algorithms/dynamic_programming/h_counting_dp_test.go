package dynamic_programming

import "testing"

func TestCountingDP(t *testing.T) {
	if NumTrees(3) != 5 || NumTrees(5) != 42 {
		t.Error("catalan")
	}
	if CountPalindromicSubstrings("aaa") != 6 || CountPalindromicSubstrings("abc") != 3 {
		t.Error("palindromic substrings")
	}
	if NumRollsToTarget(2, 6, 7) != 6 || NumRollsToTarget(30, 30, 500) != 222616187 {
		t.Error("dice")
	}
}
