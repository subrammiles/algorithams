// Package e_intervals: sort by start (or end), then sweep.
package e_intervals

import "sort"

func Merge(intervals [][2]int) [][2]int {
	sorted := append([][2]int(nil), intervals...)
	sort.Slice(sorted, func(i, j int) bool { return sorted[i][0] < sorted[j][0] })
	var merged [][2]int
	for _, in := range sorted {
		if len(merged) == 0 || merged[len(merged)-1][1] < in[0] {
			merged = append(merged, in)
		} else {
			merged[len(merged)-1][1] = max(merged[len(merged)-1][1], in[1])
		}
	}
	return merged
}

// Insert into a sorted, non-overlapping list: before / overlapping / after. O(n).
func Insert(intervals [][2]int, add [2]int) [][2]int {
	var out [][2]int
	i := 0
	for ; i < len(intervals) && intervals[i][1] < add[0]; i++ {
		out = append(out, intervals[i])
	}
	for ; i < len(intervals) && intervals[i][0] <= add[1]; i++ {
		add = [2]int{min(add[0], intervals[i][0]), max(add[1], intervals[i][1])}
	}
	out = append(out, add)
	return append(out, intervals[i:]...)
}

// EraseOverlapIntervals: sort by end, keep the earliest-ending interval greedily.
func EraseOverlapIntervals(intervals [][2]int) int {
	sorted := append([][2]int(nil), intervals...)
	sort.Slice(sorted, func(i, j int) bool { return sorted[i][1] < sorted[j][1] })
	removed, end, started := 0, 0, false
	for _, in := range sorted {
		if !started || in[0] >= end {
			end, started = in[1], true
		} else {
			removed++
		}
	}
	return removed
}
