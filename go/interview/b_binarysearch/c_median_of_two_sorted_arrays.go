package b_binarysearch

import "math"

// FindMedian binary-searches the partition of the smaller array. O(log min(m, n)).
func FindMedian(a, b []int) float64 {
	if len(a) > len(b) {
		a, b = b, a
	}
	m, n := len(a), len(b)
	half := (m + n + 1) / 2
	at := func(s []int, i int) int {
		switch {
		case i < 0:
			return math.MinInt
		case i >= len(s):
			return math.MaxInt
		}
		return s[i]
	}
	for lo, hi := 0, m; lo <= hi; {
		i := (lo + hi) / 2
		j := half - i
		aLeft, aRight, bLeft, bRight := at(a, i-1), at(a, i), at(b, j-1), at(b, j)
		switch {
		case aLeft <= bRight && bLeft <= aRight:
			leftMax := max(aLeft, bLeft)
			if (m+n)%2 == 1 {
				return float64(leftMax)
			}
			return (float64(leftMax) + float64(min(aRight, bRight))) / 2
		case aLeft > bRight:
			hi = i - 1
		default:
			lo = i + 1
		}
	}
	panic("inputs are not sorted")
}
