package d_heaps

import (
	"container/heap"
	"sort"
)

// intHeap is a min-heap; negate values to use it as a max-heap.
type intHeap struct{ sort.IntSlice }

func (h *intHeap) Push(x any) { h.IntSlice = append(h.IntSlice, x.(int)) }
func (h *intHeap) Pop() any {
	old := h.IntSlice
	x := old[len(old)-1]
	h.IntSlice = old[:len(old)-1]
	return x
}

// MedianFinder: low = max-heap of the smaller half (negated), high = min-heap of the larger half.
type MedianFinder struct {
	low, high intHeap
}

func (m *MedianFinder) AddNum(x int) {
	heap.Push(&m.low, -x)
	heap.Push(&m.high, -heap.Pop(&m.low).(int))
	if m.high.Len() > m.low.Len() {
		heap.Push(&m.low, -heap.Pop(&m.high).(int))
	}
}

func (m *MedianFinder) FindMedian() float64 {
	if m.low.Len() > m.high.Len() {
		return float64(-m.low.IntSlice[0])
	}
	return float64(-m.low.IntSlice[0]+m.high.IntSlice[0]) / 2
}
