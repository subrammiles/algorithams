// Package l_backtracking: choose -> recurse -> un-choose.
package l_backtracking

import "sort"

func SubsetsWithDup(nums []int) [][]int {
	a := append([]int(nil), nums...)
	sort.Ints(a)
	out := [][]int{}
	var cur []int
	var gen func(int)
	gen = func(start int) {
		out = append(out, append([]int{}, cur...)) // copy: cur keeps changing
		for i := start; i < len(a); i++ {
			if i > start && a[i] == a[i-1] {
				continue
			}
			cur = append(cur, a[i])
			gen(i + 1)
			cur = cur[:len(cur)-1]
		}
	}
	gen(0)
	return out
}

func PermuteUnique(nums []int) [][]int {
	a := append([]int(nil), nums...)
	sort.Ints(a)
	var out [][]int
	used := make([]bool, len(a))
	var cur []int
	var gen func()
	gen = func() {
		if len(cur) == len(a) {
			out = append(out, append([]int{}, cur...))
			return
		}
		for i := range a {
			if used[i] || (i > 0 && a[i] == a[i-1] && !used[i-1]) {
				continue
			}
			used[i] = true
			cur = append(cur, a[i])
			gen()
			cur = cur[:len(cur)-1]
			used[i] = false
		}
	}
	gen()
	return out
}

func CombinationSum(candidates []int, target int) [][]int {
	a := append([]int(nil), candidates...)
	sort.Ints(a)
	var out [][]int
	var cur []int
	var gen func(start, remaining int)
	gen = func(start, remaining int) {
		if remaining == 0 {
			out = append(out, append([]int{}, cur...))
			return
		}
		for i := start; i < len(a) && a[i] <= remaining; i++ {
			cur = append(cur, a[i])
			gen(i, remaining-a[i]) // i, not i+1: reuse allowed
			cur = cur[:len(cur)-1]
		}
	}
	gen(0, target)
	return out
}
