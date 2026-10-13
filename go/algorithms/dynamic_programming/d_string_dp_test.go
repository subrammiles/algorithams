package dynamic_programming

import "testing"

func TestStringDP(t *testing.T) {
	if EditDistance("horse", "ros") != 3 || EditDistance("intention", "execution") != 5 {
		t.Error("edit distance")
	}
	if LongestPalindromicSubsequence("bbbab") != 4 || LongestPalindromicSubstring("babad") != "bab" || LongestPalindromicSubstring("cbbd") != "bb" {
		t.Error("palindromes")
	}
	if !WordBreak("leetcode", []string{"leet", "code"}) || WordBreak("catsandog", []string{"cats", "dog", "sand", "and", "cat"}) {
		t.Error("word break")
	}
	if NumDistinct("rabbbit", "rabbit") != 3 {
		t.Error("distinct subsequences")
	}
	if !IsInterleave("aabcc", "dbbca", "aadbbcbcac") || IsInterleave("aabcc", "dbbca", "aadbbbaccc") {
		t.Error("interleave")
	}
}
