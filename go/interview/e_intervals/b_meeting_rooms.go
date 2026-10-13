package e_intervals

import (
	"container/heap"
	"sort"
)

type endTimes struct{ sort.IntSlice }

func (h *endTimes) Push(x any) { h.IntSlice = append(h.IntSlice, x.(int)) }
func (h *endTimes) Pop() any {
	old := h.IntSlice
	x := old[len(old)-1]
	h.IntSlice = old[:len(old)-1]
	return x
}

// MinRoomsHeap: sort by start; reuse the earliest-ending room if it is free. O(n log n).
func MinRoomsHeap(meetings [][2]int) int {
	sorted := append([][2]int(nil), meetings...)
	sort.Slice(sorted, func(i, j int) bool { return sorted[i][0] < sorted[j][0] })
	h := &endTimes{}
	for _, m := range sorted {
		if h.Len() > 0 && h.IntSlice[0] <= m[0] {
			heap.Pop(h)
		}
		heap.Push(h, m[1])
	}
	return h.Len()
}

// MinRoomsSweep: sorted starts and ends; +1 on a start, -1 on an end; the peak is the answer.
func MinRoomsSweep(meetings [][2]int) int {
	starts, ends := make([]int, len(meetings)), make([]int, len(meetings))
	for i, m := range meetings {
		starts[i], ends[i] = m[0], m[1]
	}
	sort.Ints(starts)
	sort.Ints(ends)
	rooms, best, e := 0, 0, 0
	for _, s := range starts {
		for ends[e] <= s {
			e++
			rooms--
		}
		rooms++
		best = max(best, rooms)
	}
	return best
}
