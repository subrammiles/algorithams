// Package d_heaps: top-k, k-way merge and running median with container/heap.
package d_heaps

import (
	"container/heap"
	"math/rand"
)

type pair struct{ value, freq int }

// minFreqHeap implements heap.Interface ordered by frequency.
type minFreqHeap []pair

func (h minFreqHeap) Len() int           { return len(h) }
func (h minFreqHeap) Less(i, j int) bool { return h[i].freq < h[j].freq }
func (h minFreqHeap) Swap(i, j int)      { h[i], h[j] = h[j], h[i] }
func (h *minFreqHeap) Push(x any)        { *h = append(*h, x.(pair)) }
func (h *minFreqHeap) Pop() any {
	old := *h
	x := old[len(old)-1]
	*h = old[:len(old)-1]
	return x
}

func frequencies(nums []int) map[int]int {
	freq := map[int]int{}
	for _, x := range nums {
		freq[x]++
	}
	return freq
}

// TopKHeap keeps a size-k min-heap of frequencies. O(n log k).
func TopKHeap(nums []int, k int) []int {
	h := &minFreqHeap{}
	for v, f := range frequencies(nums) {
		heap.Push(h, pair{v, f})
		if h.Len() > k {
			heap.Pop(h)
		}
	}
	result := make([]int, h.Len())
	for i := len(result) - 1; i >= 0; i-- {
		result[i] = heap.Pop(h).(pair).value
	}
	return result
}

// TopKBucket buckets values by frequency. O(n).
func TopKBucket(nums []int, k int) []int {
	buckets := make([][]int, len(nums)+1)
	for v, f := range frequencies(nums) {
		buckets[f] = append(buckets[f], v)
	}
	var result []int
	for f := len(nums); f > 0 && len(result) < k; f-- {
		result = append(result, buckets[f]...)
	}
	return result[:k]
}

// KthLargest uses quickselect with a random pivot: average O(n).
func KthLargest(nums []int, k int) int {
	a := append([]int(nil), nums...)
	target, lo, hi := len(a)-k, 0, len(a)-1
	rng := rand.New(rand.NewSource(42))
	for {
		p := lo + rng.Intn(hi-lo+1)
		a[p], a[hi] = a[hi], a[p]
		store := lo
		for i := lo; i < hi; i++ {
			if a[i] < a[hi] {
				a[i], a[store] = a[store], a[i]
				store++
			}
		}
		a[store], a[hi] = a[hi], a[store]
		switch {
		case store == target:
			return a[store]
		case store < target:
			lo = store + 1
		default:
			hi = store - 1
		}
	}
}
