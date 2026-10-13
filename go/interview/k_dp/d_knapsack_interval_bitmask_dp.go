package k_dp

import "math"

// CanPartition is 0/1 knapsack; iterate capacity downwards so each item is used once.
func CanPartition(nums []int) bool {
	total := 0
	for _, x := range nums {
		total += x
	}
	if total%2 == 1 {
		return false
	}
	target := total / 2
	dp := make([]bool, target+1)
	dp[0] = true
	for _, x := range nums {
		for c := target; c >= x; c-- {
			dp[c] = dp[c] || dp[c-x]
		}
	}
	return dp[target]
}

// MaxCoins (burst balloons): k is the LAST balloon burst in (l, r). O(n^3).
func MaxCoins(nums []int) int {
	v := append(append([]int{1}, nums...), 1)
	n := len(v)
	dp := make([][]int, n)
	for i := range dp {
		dp[i] = make([]int, n)
	}
	for length := 2; length < n; length++ {
		for l := 0; l+length < n; l++ {
			r := l + length
			for k := l + 1; k < r; k++ {
				dp[l][r] = max(dp[l][r], dp[l][k]+dp[k][r]+v[l]*v[k]*v[r])
			}
		}
	}
	return dp[0][n-1]
}

// TSP with bitmask DP: dp[mask][last]. O(2^n * n^2).
func TSP(dist [][]int) int {
	n, inf := len(dist), math.MaxInt/2
	full := 1 << n
	dp := make([][]int, full)
	for i := range dp {
		dp[i] = make([]int, n)
		for j := range dp[i] {
			dp[i][j] = inf
		}
	}
	dp[1][0] = 0
	for mask := 1; mask < full; mask++ {
		for last := 0; last < n; last++ {
			if dp[mask][last] == inf || mask&(1<<last) == 0 {
				continue
			}
			for next := 0; next < n; next++ {
				if mask&(1<<next) == 0 {
					nm := mask | 1<<next
					dp[nm][next] = min(dp[nm][next], dp[mask][last]+dist[last][next])
				}
			}
		}
	}
	best := inf
	for last := 1; last < n; last++ {
		best = min(best, dp[full-1][last]+dist[last][0])
	}
	return best
}
