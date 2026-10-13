package k_dp

import "testing"

func TestTwoStringDP(t *testing.T) {
	if LCS("abcde", "ace") != 3 || LCS("abc", "def") != 0 {
		t.Error("lcs")
	}
	if !IsMatch("aa", "a*") || !IsMatch("ab", ".*") || !IsMatch("aab", "c*a*b") || IsMatch("mississippi", "mis*is*p*.") {
		t.Error("regex")
	}
}
