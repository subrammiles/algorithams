package dynamic_programming

func MaxSubarray(a []int) int {
	best, ending := a[0], a[0]
	for _, x := range a[1:] {
		ending = max(x, ending+x)
		best = max(best, ending)
	}
	return best
}

// MaxProduct tracks the max AND min product ending here (a negative flips them).
func MaxProduct(a []int) int {
	best, hi, lo := a[0], a[0], a[0]
	for _, x := range a[1:] {
		hi, lo = max(x, hi*x, lo*x), min(x, hi*x, lo*x)
		best = max(best, hi)
	}
	return best
}

func LongestCommonSubstring(a, b string) int {
	prev, best := make([]int, len(b)+1), 0
	for i := 1; i <= len(a); i++ {
		cur := make([]int, len(b)+1)
		for j := 1; j <= len(b); j++ {
			if a[i-1] == b[j-1] {
				cur[j] = prev[j-1] + 1
				best = max(best, cur[j])
			}
		}
		prev = cur
	}
	return best
}

func MaxSumIncreasingSubsequence(a []int) int {
	total := append([]int(nil), a...)
	best := 0
	for i := range a {
		for j := 0; j < i; j++ {
			if a[j] < a[i] {
				total[i] = max(total[i], total[j]+a[i])
			}
		}
		best = max(best, total[i])
	}
	return best
}

func LongestBitonic(a []int) int {
	n := len(a)
	inc, dec := make([]int, n), make([]int, n)
	for i := 0; i < n; i++ {
		inc[i] = 1
		for j := 0; j < i; j++ {
			if a[j] < a[i] {
				inc[i] = max(inc[i], inc[j]+1)
			}
		}
	}
	for i := n - 1; i >= 0; i-- {
		dec[i] = 1
		for j := n - 1; j > i; j-- {
			if a[j] < a[i] {
				dec[i] = max(dec[i], dec[j]+1)
			}
		}
	}
	best := 0
	for i := range a {
		best = max(best, inc[i]+dec[i]-1)
	}
	return best
}
