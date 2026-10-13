package d_heaps

import "container/heap"

type ListNode struct {
	Val  int
	Next *ListNode
}

type nodeHeap []*ListNode

func (h nodeHeap) Len() int           { return len(h) }
func (h nodeHeap) Less(i, j int) bool { return h[i].Val < h[j].Val }
func (h nodeHeap) Swap(i, j int)      { h[i], h[j] = h[j], h[i] }
func (h *nodeHeap) Push(x any)        { *h = append(*h, x.(*ListNode)) }
func (h *nodeHeap) Pop() any {
	old := *h
	x := old[len(old)-1]
	*h = old[:len(old)-1]
	return x
}

// MergeKLists merges with a min-heap of current heads. O(N log k).
func MergeKLists(lists []*ListNode) *ListNode {
	h := &nodeHeap{}
	for _, head := range lists {
		if head != nil {
			*h = append(*h, head)
		}
	}
	heap.Init(h)
	dummy := &ListNode{}
	tail := dummy
	for h.Len() > 0 {
		smallest := heap.Pop(h).(*ListNode)
		tail.Next = smallest
		tail = smallest
		if smallest.Next != nil {
			heap.Push(h, smallest.Next)
		}
	}
	return dummy.Next
}
