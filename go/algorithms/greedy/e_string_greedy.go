package greedy

import (
	"container/heap"
	"sort"
	"strconv"
	"strings"
)

// RemoveKdigits keeps a monotonic increasing stack of digits. O(n).
func RemoveKdigits(num string, k int) string {
	var stack []byte
	for i := 0; i < len(num); i++ {
		for k > 0 && len(stack) > 0 && stack[len(stack)-1] > num[i] {
			stack = stack[:len(stack)-1]
			k--
		}
		stack = append(stack, num[i])
	}
	stack = stack[:len(stack)-k]
	result := strings.TrimLeft(string(stack), "0")
	if result == "" {
		return "0"
	}
	return result
}

type charCount struct {
	ch    byte
	count int
}
type countHeap []charCount

func (h countHeap) Len() int { return len(h) }
func (h countHeap) Less(i, j int) bool {
	if h[i].count != h[j].count {
		return h[i].count > h[j].count
	}
	return h[i].ch < h[j].ch
}
func (h countHeap) Swap(i, j int) { h[i], h[j] = h[j], h[i] }
func (h *countHeap) Push(x any)   { *h = append(*h, x.(charCount)) }
func (h *countHeap) Pop() any {
	old := *h
	x := old[len(old)-1]
	*h = old[:len(old)-1]
	return x
}

// ReorganizeString places the most frequent remaining char that differs from the previous one.
func ReorganizeString(s string) string {
	var counts [26]int
	for i := 0; i < len(s); i++ {
		counts[s[i]-'a']++
	}
	h := &countHeap{}
	for i, c := range counts {
		if c > 0 {
			heap.Push(h, charCount{byte('a' + i), c})
		}
	}
	var out []byte
	var held *charCount
	for h.Len() > 0 {
		top := heap.Pop(h).(charCount)
		out = append(out, top.ch)
		if held != nil {
			heap.Push(h, *held)
			held = nil
		}
		if top.count > 1 {
			held = &charCount{top.ch, top.count - 1}
		}
	}
	if len(out) != len(s) {
		return ""
	}
	return string(out)
}

// LargestNumber sorts by a+b vs b+a.
func LargestNumber(nums []int) string {
	parts := make([]string, len(nums))
	for i, n := range nums {
		parts[i] = strconv.Itoa(n)
	}
	sort.Slice(parts, func(i, j int) bool { return parts[i]+parts[j] > parts[j]+parts[i] })
	if parts[0] == "0" {
		return "0"
	}
	return strings.Join(parts, "")
}
