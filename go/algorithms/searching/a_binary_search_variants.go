// Package searching: binary search variants. sort.Search(n, cond) is Go's built-in
// "first index where cond becomes true" - the one template that covers most variants.
package searching

import "sort"

func LowerBound(a []int, x int) int {
	return sort.Search(len(a), func(i int) bool { return a[i] >= x })
}
func UpperBound(a []int, x int) int { return sort.Search(len(a), func(i int) bool { return a[i] > x }) }

func SearchRange(a []int, x int) [2]int {
	first := LowerBound(a, x)
	if first == len(a) || a[first] != x {
		return [2]int{-1, -1}
	}
	return [2]int{first, UpperBound(a, x) - 1}
}

func FindPeakElement(a []int) int {
	lo, hi := 0, len(a)-1
	for lo < hi {
		mid := (lo + hi) / 2
		if a[mid] < a[mid+1] {
			lo = mid + 1
		} else {
			hi = mid
		}
	}
	return lo
}

func MySqrt(n int) int {
	return sort.Search(n+1, func(x int) bool { return x*x > n }) - 1
}

func SearchMatrix(m [][]int, target int) bool {
	cols := len(m[0])
	i := sort.Search(len(m)*cols, func(k int) bool { return m[k/cols][k%cols] >= target })
	return i < len(m)*cols && m[i/cols][i%cols] == target
}

func TernarySearchMax(a []int) int {
	lo, hi := 0, len(a)-1
	for hi-lo > 2 {
		m1, m2 := lo+(hi-lo)/3, hi-(hi-lo)/3
		if a[m1] < a[m2] {
			lo = m1 + 1
		} else {
			hi = m2 - 1
		}
	}
	best := lo
	for i := lo; i <= hi; i++ {
		if a[i] > a[best] {
			best = i
		}
	}
	return best
}
