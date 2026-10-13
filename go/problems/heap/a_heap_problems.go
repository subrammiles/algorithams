// Package heap: heap interview problems built on container/heap.
package heap

import (
	stdheap "container/heap"
	"sort"
)

// intHeap is a min-heap of ints; less can flip it into a max-heap.
type intHeap struct {
	items []int
	less  func(a, b int) bool
}

func (h *intHeap) Len() int           { return len(h.items) }
func (h *intHeap) Less(i, j int) bool { return h.less(h.items[i], h.items[j]) }
func (h *intHeap) Swap(i, j int)      { h.items[i], h.items[j] = h.items[j], h.items[i] }
func (h *intHeap) Push(x any)         { h.items = append(h.items, x.(int)) }
func (h *intHeap) Pop() any {
	x := h.items[len(h.items)-1]
	h.items = h.items[:len(h.items)-1]
	return x
}

// KClosest keeps a max-heap (by squared distance) of size k over point indexes.
func KClosest(points [][2]int, k int) [][2]int {
	dist := func(i int) int { return points[i][0]*points[i][0] + points[i][1]*points[i][1] }
	h := &intHeap{less: func(a, b int) bool { return dist(a) > dist(b) }}
	for i := range points {
		stdheap.Push(h, i)
		if h.Len() > k {
			stdheap.Pop(h)
		}
	}
	out := make([][2]int, 0, k)
	for _, i := range h.items {
		out = append(out, points[i])
	}
	sort.Slice(out, func(i, j int) bool {
		if out[i][0] != out[j][0] {
			return out[i][0] < out[j][0]
		}
		return out[i][1] < out[j][1]
	})
	return out
}

// KthLargest keeps a min-heap of size k; its top is the answer.
type KthLargest struct {
	k int
	h *intHeap
}

func NewKthLargest(k int, nums []int) *KthLargest {
	kl := &KthLargest{k: k, h: &intHeap{less: func(a, b int) bool { return a < b }}}
	for _, x := range nums {
		kl.Add(x)
	}
	return kl
}

func (kl *KthLargest) Add(val int) int {
	stdheap.Push(kl.h, val)
	if kl.h.Len() > kl.k {
		stdheap.Pop(kl.h)
	}
	return kl.h.items[0]
}

// NthUglyNumber uses three pointers: O(n), no heap needed.
func NthUglyNumber(n int) int {
	ugly := []int{1}
	i2, i3, i5 := 0, 0, 0
	for len(ugly) < n {
		next := min(ugly[i2]*2, ugly[i3]*3, ugly[i5]*5)
		ugly = append(ugly, next)
		if next == ugly[i2]*2 {
			i2++
		}
		if next == ugly[i3]*3 {
			i3++
		}
		if next == ugly[i5]*5 {
			i5++
		}
	}
	return ugly[n-1]
}

func LastStoneWeight(stones []int) int {
	h := &intHeap{items: append([]int(nil), stones...), less: func(a, b int) bool { return a > b }}
	stdheap.Init(h)
	for h.Len() > 1 {
		a, b := stdheap.Pop(h).(int), stdheap.Pop(h).(int)
		if a != b {
			stdheap.Push(h, a-b)
		}
	}
	if h.Len() == 0 {
		return 0
	}
	return h.items[0]
}
