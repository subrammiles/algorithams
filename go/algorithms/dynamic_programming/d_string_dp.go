package dynamic_programming

func EditDistance(a, b string) int {
	prev := make([]int, len(b)+1)
	for j := range prev {
		prev[j] = j
	}
	for i := 1; i <= len(a); i++ {
		cur := make([]int, len(b)+1)
		cur[0] = i
		for j := 1; j <= len(b); j++ {
			if a[i-1] == b[j-1] {
				cur[j] = prev[j-1]
			} else {
				cur[j] = 1 + min(prev[j-1], prev[j], cur[j-1])
			}
		}
		prev = cur
	}
	return prev[len(b)]
}

func LongestPalindromicSubsequence(s string) int {
	n := len(s)
	dp := make([][]int, n)
	for i := range dp {
		dp[i] = make([]int, n)
	}
	for i := n - 1; i >= 0; i-- {
		dp[i][i] = 1
		for j := i + 1; j < n; j++ {
			if s[i] == s[j] {
				dp[i][j] = 2 + dp[i+1][j-1]
			} else {
				dp[i][j] = max(dp[i+1][j], dp[i][j-1])
			}
		}
	}
	return dp[0][n-1]
}

// LongestPalindromicSubstring expands around all 2n-1 centres. O(n^2), O(1) space.
func LongestPalindromicSubstring(s string) string {
	bestStart, bestLen := 0, 0
	for centre := 0; centre < 2*len(s)-1; centre++ {
		lo, hi := centre/2, centre/2+centre%2
		for lo >= 0 && hi < len(s) && s[lo] == s[hi] {
			lo, hi = lo-1, hi+1
		}
		if hi-lo-1 > bestLen {
			bestStart, bestLen = lo+1, hi-lo-1
		}
	}
	return s[bestStart : bestStart+bestLen]
}

func WordBreak(s string, words []string) bool {
	dict, maxLen := map[string]bool{}, 0
	for _, w := range words {
		dict[w] = true
		maxLen = max(maxLen, len(w))
	}
	ok := make([]bool, len(s)+1)
	ok[0] = true
	for i := 1; i <= len(s); i++ {
		for l := 1; l <= min(i, maxLen) && !ok[i]; l++ {
			ok[i] = ok[i-l] && dict[s[i-l:i]]
		}
	}
	return ok[len(s)]
}

func NumDistinct(s, t string) int {
	dp := make([]int, len(t)+1)
	dp[0] = 1
	for i := 0; i < len(s); i++ {
		for j := len(t); j >= 1; j-- { // backwards: dp[j-1] still from the previous row
			if s[i] == t[j-1] {
				dp[j] += dp[j-1]
			}
		}
	}
	return dp[len(t)]
}

func IsInterleave(a, b, c string) bool {
	if len(a)+len(b) != len(c) {
		return false
	}
	dp := make([]bool, len(b)+1)
	for i := 0; i <= len(a); i++ {
		for j := 0; j <= len(b); j++ {
			if i == 0 && j == 0 {
				dp[j] = true
				continue
			}
			fromA := i > 0 && dp[j] && a[i-1] == c[i+j-1]
			fromB := j > 0 && dp[j-1] && b[j-1] == c[i+j-1]
			dp[j] = fromA || fromB
		}
	}
	return dp[len(b)]
}
