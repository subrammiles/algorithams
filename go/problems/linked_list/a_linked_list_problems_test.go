package linked_list

import (
	"reflect"
	"testing"
)

func eq(t *testing.T, name string, got, want []int) {
	t.Helper()
	if !reflect.DeepEqual(got, want) {
		t.Errorf("%s: got %v want %v", name, got, want)
	}
}

func TestLinkedListProblems(t *testing.T) {
	eq(t, "merge", Values(MergeTwoLists(Of(1, 2, 4), Of(1, 3, 4))), []int{1, 1, 2, 3, 4, 4})
	eq(t, "remove nth", Values(RemoveNthFromEnd(Of(1, 2, 3, 4, 5), 2)), []int{1, 2, 3, 5})
	eq(t, "remove only", Values(RemoveNthFromEnd(Of(1), 1)), []int{})
	pal := Of(1, 2, 2, 1)
	if !IsPalindrome(pal) || IsPalindrome(Of(1, 2)) {
		t.Error("palindrome")
	}
	eq(t, "palindrome restores", Values(pal), []int{1, 2, 2, 1})
	eq(t, "add", Values(AddTwoNumbers(Of(2, 4, 3), Of(5, 6, 4))), []int{7, 0, 8})
	eq(t, "add carry", Values(AddTwoNumbers(Of(9, 9, 9, 9, 9, 9, 9), Of(9, 9, 9, 9))), []int{8, 9, 9, 9, 0, 0, 0, 1})
	shared := Of(8, 4, 5)
	a, b := Of(4, 1), Of(5, 6, 1)
	a.Next.Next, b.Next.Next.Next = shared, shared
	if GetIntersectionNode(a, b) != shared || GetIntersectionNode(Of(1), Of(2)) != nil {
		t.Error("intersection")
	}
	r := Of(1, 2, 3, 4, 5)
	ReorderList(r)
	eq(t, "reorder", Values(r), []int{1, 5, 2, 4, 3})
	eq(t, "sort", Values(SortList(Of(4, 2, 1, 3))), []int{1, 2, 3, 4})
	eq(t, "sort 2", Values(SortList(Of(-1, 5, 3, 4, 0))), []int{-1, 0, 3, 4, 5})
}
