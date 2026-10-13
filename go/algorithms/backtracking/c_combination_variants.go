package backtracking

import (
	"sort"
	"strings"
	"unicode"
)

func CombinationSum2(candidates []int, target int) [][]int {
	a := append([]int(nil), candidates...)
	sort.Ints(a)
	var out [][]int
	var cur []int
	var walk func(start, remaining int)
	walk = func(start, remaining int) {
		if remaining == 0 {
			out = append(out, append([]int(nil), cur...))
			return
		}
		for i := start; i < len(a) && a[i] <= remaining; i++ {
			if i > start && a[i] == a[i-1] {
				continue
			}
			cur = append(cur, a[i])
			walk(i+1, remaining-a[i])
			cur = cur[:len(cur)-1]
		}
	}
	walk(0, target)
	return out
}

func LetterCasePermutation(s string) []string {
	out := []string{""}
	for _, ch := range s {
		var next []string
		for _, p := range out {
			if unicode.IsLetter(ch) {
				next = append(next, p+strings.ToLower(string(ch)), p+strings.ToUpper(string(ch)))
			} else {
				next = append(next, p+string(ch))
			}
		}
		out = next
	}
	sort.Strings(out)
	return out
}

func Combine(n, k int) [][]int {
	var out [][]int
	var cur []int
	var walk func(start int)
	walk = func(start int) {
		if len(cur) == k {
			out = append(out, append([]int(nil), cur...))
			return
		}
		for i := start; i <= n-(k-len(cur))+1; i++ {
			cur = append(cur, i)
			walk(i + 1)
			cur = cur[:len(cur)-1]
		}
	}
	walk(1)
	return out
}

func Permute(nums []int) [][]int {
	var out [][]int
	var cur []int
	used := make([]bool, len(nums))
	var walk func()
	walk = func() {
		if len(cur) == len(nums) {
			out = append(out, append([]int(nil), cur...))
			return
		}
		for i, x := range nums {
			if used[i] {
				continue
			}
			used[i] = true
			cur = append(cur, x)
			walk()
			cur = cur[:len(cur)-1]
			used[i] = false
		}
	}
	walk()
	return out
}

func SubsetsBitmask(nums []int) [][]int {
	out := make([][]int, 0, 1<<len(nums))
	for mask := 0; mask < 1<<len(nums); mask++ {
		subset := []int{}
		for i, x := range nums {
			if mask>>i&1 == 1 {
				subset = append(subset, x)
			}
		}
		out = append(out, subset)
	}
	return out
}
