// Package divide_and_conquer: split, solve halves, combine. O(n log n) with a linear combine.
package divide_and_conquer

import "math"

// CountInversions counts pairs i < j with a[i] > a[j] while merge sorting.
func CountInversions(nums []int) int {
	a := append([]int(nil), nums...)
	return sortCount(a, make([]int, len(a)))
}

func sortCount(a, tmp []int) int {
	if len(a) <= 1 {
		return 0
	}
	mid := len(a) / 2
	count := sortCount(a[:mid], tmp[:mid]) + sortCount(a[mid:], tmp[mid:])
	i, j, k := 0, mid, 0
	for i < mid && j < len(a) {
		if a[i] <= a[j] {
			tmp[k] = a[i]
			i++
		} else {
			count += mid - i // all remaining left elements are bigger
			tmp[k] = a[j]
			j++
		}
		k++
	}
	k += copy(tmp[k:], a[i:mid])
	copy(tmp[k:], a[j:])
	copy(a, tmp[:len(a)])
	return count
}

func MaxSubarray(a []int) int {
	if len(a) == 1 {
		return a[0]
	}
	mid := len(a) / 2
	bestLeft, bestRight, s := math.MinInt, math.MinInt, 0
	for i := mid - 1; i >= 0; i-- {
		s += a[i]
		bestLeft = max(bestLeft, s)
	}
	s = 0
	for i := mid; i < len(a); i++ {
		s += a[i]
		bestRight = max(bestRight, s)
	}
	return max(bestLeft+bestRight, MaxSubarray(a[:mid]), MaxSubarray(a[mid:]))
}

func MajorityElement(a []int) int {
	if len(a) == 1 {
		return a[0]
	}
	mid := len(a) / 2
	left, right := MajorityElement(a[:mid]), MajorityElement(a[mid:])
	if left == right {
		return left
	}
	countOf := func(x int) int {
		c := 0
		for _, v := range a {
			if v == x {
				c++
			}
		}
		return c
	}
	if countOf(left) > countOf(right) {
		return left
	}
	return right
}
