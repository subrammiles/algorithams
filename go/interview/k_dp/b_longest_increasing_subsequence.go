package k_dp

import "sort"

func LISQuadratic(a []int) int {
	dp, best := make([]int, len(a)), 0
	for i := range a {
		dp[i] = 1
		for j := 0; j < i; j++ {
			if a[j] < a[i] {
				dp[i] = max(dp[i], dp[j]+1)
			}
		}
		best = max(best, dp[i])
	}
	return best
}

// LISFast: tails[k] = smallest tail of an increasing subsequence of length k+1. O(n log n).
func LISFast(a []int) int {
	var tails []int
	for _, x := range a {
		i := sort.SearchInts(tails, x)
		if i == len(tails) {
			tails = append(tails, x)
		} else {
			tails[i] = x
		}
	}
	return len(tails)
}

// MaxEnvelopes: sort by width asc, height desc, then LIS on heights.
func MaxEnvelopes(envelopes [][2]int) int {
	e := append([][2]int(nil), envelopes...)
	sort.Slice(e, func(i, j int) bool {
		if e[i][0] != e[j][0] {
			return e[i][0] < e[j][0]
		}
		return e[i][1] > e[j][1]
	})
	heights := make([]int, len(e))
	for i := range e {
		heights[i] = e[i][1]
	}
	return LISFast(heights)
}
