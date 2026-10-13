package f_linkedlist

import "testing"

func TestFloyd(t *testing.T) {
	head := Build(3, 2, 0, -4)
	head.Next.Next.Next.Next = head.Next
	if DetectCycle(head).Val != 2 || DetectCycle(Build(1, 2)) != nil {
		t.Fatal("detect cycle")
	}
	if FindDuplicate([]int{1, 3, 4, 2, 2}) != 2 || FindDuplicate([]int{3, 1, 3, 4, 2}) != 3 {
		t.Fatal("find duplicate")
	}
}
