package e_intervals

import (
	"container/heap"
	"sort"
)

// Sweep line (full explanations in the Java version).
//
//	GetSkyline            events (x, -h) start / (x, h) end; max-heap + lazy deletion of heights
//	IntervalIntersection  two pointers; advance whichever interval ends first
//	EmployeeFreeTime      flatten, sort, merge; gaps are the free time
//	CarPooling            difference array over sorted event times

type maxHeights []int

func (h maxHeights) Len() int           { return len(h) }
func (h maxHeights) Less(i, j int) bool { return h[i] > h[j] }
func (h maxHeights) Swap(i, j int)      { h[i], h[j] = h[j], h[i] }
func (h *maxHeights) Push(x any)        { *h = append(*h, x.(int)) }
func (h *maxHeights) Pop() any {
	old := *h
	x := old[len(old)-1]
	*h = old[:len(old)-1]
	return x
}

// GetSkyline: buildings[i] = {left, right, height}; returns key points {x, height}.
func GetSkyline(buildings [][3]int) [][2]int {
	var events [][2]int
	for _, b := range buildings {
		events = append(events, [2]int{b[0], -b[2]}, [2]int{b[1], b[2]})
	}
	sort.Slice(events, func(i, j int) bool {
		if events[i][0] != events[j][0] {
			return events[i][0] < events[j][0]
		}
		return events[i][1] < events[j][1]
	})
	h := &maxHeights{0}
	toRemove := map[int]int{}
	var out [][2]int
	prev := 0
	for _, e := range events {
		if e[1] < 0 {
			heap.Push(h, -e[1])
		} else {
			toRemove[e[1]]++
		}
		for toRemove[(*h)[0]] > 0 {
			toRemove[(*h)[0]]--
			heap.Pop(h)
		}
		if cur := (*h)[0]; cur != prev {
			out = append(out, [2]int{e[0], cur})
			prev = cur
		}
	}
	return out
}

func IntervalIntersection(a, b [][2]int) [][2]int {
	var out [][2]int
	for i, j := 0, 0; i < len(a) && j < len(b); {
		lo, hi := max(a[i][0], b[j][0]), min(a[i][1], b[j][1])
		if lo <= hi {
			out = append(out, [2]int{lo, hi})
		}
		if a[i][1] < b[j][1] {
			i++
		} else {
			j++
		}
	}
	return out
}

func EmployeeFreeTime(schedules [][][2]int) [][2]int {
	var all [][2]int
	for _, person := range schedules {
		all = append(all, person...)
	}
	sort.Slice(all, func(i, j int) bool { return all[i][0] < all[j][0] })
	var free [][2]int
	end := all[0][1]
	for _, in := range all {
		if in[0] > end {
			free = append(free, [2]int{end, in[0]})
		}
		end = max(end, in[1])
	}
	return free
}

// CarPooling: trips[i] = {passengers, from, to}.
func CarPooling(trips [][3]int, capacity int) bool {
	delta := map[int]int{}
	for _, t := range trips {
		delta[t[1]] += t[0]
		delta[t[2]] -= t[0]
	}
	times := make([]int, 0, len(delta))
	for t := range delta {
		times = append(times, t)
	}
	sort.Ints(times)
	load := 0
	for _, t := range times {
		load += delta[t]
		if load > capacity {
			return false
		}
	}
	return true
}
