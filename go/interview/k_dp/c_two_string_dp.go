package k_dp

// LCS with two rolling rows. O(mn) time, O(n) space.
func LCS(s, t string) int {
	prev, cur := make([]int, len(t)+1), make([]int, len(t)+1)
	for i := 1; i <= len(s); i++ {
		for j := 1; j <= len(t); j++ {
			if s[i-1] == t[j-1] {
				cur[j] = prev[j-1] + 1
			} else {
				cur[j] = max(prev[j], cur[j-1])
			}
		}
		prev, cur = cur, prev
	}
	return prev[len(t)]
}

// IsMatch implements '.' and '*' regular expression matching.
func IsMatch(s, p string) bool {
	m, n := len(s), len(p)
	dp := make([][]bool, m+1)
	for i := range dp {
		dp[i] = make([]bool, n+1)
	}
	dp[0][0] = true
	matches := func(sc, pc byte) bool { return pc == '.' || pc == sc }
	for i := 0; i <= m; i++ {
		for j := 1; j <= n; j++ {
			if p[j-1] == '*' {
				dp[i][j] = dp[i][j-2] || (i > 0 && matches(s[i-1], p[j-2]) && dp[i-1][j])
			} else {
				dp[i][j] = i > 0 && matches(s[i-1], p[j-1]) && dp[i-1][j-1]
			}
		}
	}
	return dp[m][n]
}
