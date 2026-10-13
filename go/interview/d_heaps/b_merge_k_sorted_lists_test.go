package d_heaps

import (
	"reflect"
	"testing"
)

func build(values ...int) *ListNode {
	dummy := &ListNode{}
	tail := dummy
	for _, v := range values {
		tail.Next = &ListNode{Val: v}
		tail = tail.Next
	}
	return dummy.Next
}

func values(head *ListNode) []int {
	out := []int{}
	for ; head != nil; head = head.Next {
		out = append(out, head.Val)
	}
	return out
}

func TestMergeKLists(t *testing.T) {
	got := values(MergeKLists([]*ListNode{build(1, 4, 5), build(1, 3, 4), build(2, 6)}))
	if !reflect.DeepEqual(got, []int{1, 1, 2, 3, 4, 4, 5, 6}) {
		t.Fatalf("got %v", got)
	}
	if got := values(MergeKLists([]*ListNode{nil})); len(got) != 0 {
		t.Fatal("empty")
	}
}
