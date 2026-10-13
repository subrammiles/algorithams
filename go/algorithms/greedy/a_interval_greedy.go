// Package greedy: locally optimal choices that can be proven safe (exchange argument).
package greedy

import "sort"

// MaxActivities: sort by end, take every activity that starts after the last chosen end.
func MaxActivities(start, end []int) int {
	idx := make([]int, len(start))
	for i := range idx {
		idx[i] = i
	}
	sort.Slice(idx, func(a, b int) bool { return end[idx[a]] < end[idx[b]] })
	count, lastEnd, first := 0, 0, true
	for _, i := range idx {
		if first || start[i] >= lastEnd {
			count, lastEnd, first = count+1, end[i], false
		}
	}
	return count
}

func FindMinArrowShots(points [][2]int) int {
	p := append([][2]int(nil), points...)
	sort.Slice(p, func(a, b int) bool { return p[a][1] < p[b][1] })
	arrows, arrowAt := 0, 0
	for i, balloon := range p {
		if i == 0 || balloon[0] > arrowAt {
			arrows++
			arrowAt = balloon[1]
		}
	}
	return arrows
}

func PartitionLabels(s string) []int {
	var last [26]int
	for i := 0; i < len(s); i++ {
		last[s[i]-'a'] = i
	}
	var sizes []int
	start, end := 0, 0
	for i := 0; i < len(s); i++ {
		end = max(end, last[s[i]-'a'])
		if i == end {
			sizes = append(sizes, end-start+1)
			start = i + 1
		}
	}
	return sizes
}

func MinPlatforms(arrivals, departures []int) int {
	arr, dep := append([]int(nil), arrivals...), append([]int(nil), departures...)
	sort.Ints(arr)
	sort.Ints(dep)
	platforms, best, d := 0, 0, 0
	for _, a := range arr {
		for dep[d] < a {
			d++
			platforms--
		}
		platforms++
		best = max(best, platforms)
	}
	return best
}
