// Package hashing: problems solved by trading memory for O(1) lookups.
package hashing

import "sort"

func TwoSum(nums []int, target int) []int {
	seen := map[int]int{}
	for i, x := range nums {
		if j, ok := seen[target-x]; ok {
			return []int{j, i}
		}
		seen[x] = i
	}
	return nil
}

func GroupAnagrams(words []string) [][]string {
	groups := map[string][]string{}
	for _, w := range words {
		b := []byte(w)
		sort.Slice(b, func(i, j int) bool { return b[i] < b[j] })
		groups[string(b)] = append(groups[string(b)], w)
	}
	var out [][]string
	for _, g := range groups {
		sort.Strings(g)
		out = append(out, g)
	}
	sort.Slice(out, func(i, j int) bool { return out[i][0] < out[j][0] })
	return out
}

// LongestConsecutive is O(n): only count from x when x-1 is absent.
func LongestConsecutive(nums []int) int {
	set := map[int]bool{}
	for _, x := range nums {
		set[x] = true
	}
	best := 0
	for x := range set {
		if set[x-1] {
			continue
		}
		length := 1
		for set[x+length] {
			length++
		}
		best = max(best, length)
	}
	return best
}

func ContainsNearbyDuplicate(nums []int, k int) bool {
	last := map[int]int{}
	for i, x := range nums {
		if j, ok := last[x]; ok && i-j <= k {
			return true
		}
		last[x] = i
	}
	return false
}

// FourSumCount: meet in the middle, O(n^2).
func FourSumCount(a, b, c, d []int) int {
	sums := map[int]int{}
	for _, x := range a {
		for _, y := range b {
			sums[x+y]++
		}
	}
	count := 0
	for _, x := range c {
		for _, y := range d {
			count += sums[-(x + y)]
		}
	}
	return count
}
