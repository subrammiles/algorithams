// Package n_rangequeries: Fenwick tree and lazy segment tree.
package n_rangequeries

import "sort"

type Fenwick struct{ tree []int }

func NewFenwick(n int) *Fenwick { return &Fenwick{tree: make([]int, n+1)} }

func (f *Fenwick) Add(i, delta int) {
	for ; i < len(f.tree); i += i & -i {
		f.tree[i] += delta
	}
}

func (f *Fenwick) PrefixSum(i int) int {
	s := 0
	for ; i > 0; i -= i & -i {
		s += f.tree[i]
	}
	return s
}

func (f *Fenwick) RangeSum(l, r int) int { return f.PrefixSum(r) - f.PrefixSum(l-1) }

// CountSmaller: coordinate-compress, scan right to left with a Fenwick tree. O(n log n).
func CountSmaller(nums []int) []int {
	sorted := append([]int(nil), nums...)
	sort.Ints(sorted)
	rank := map[int]int{}
	for _, v := range sorted {
		if _, ok := rank[v]; !ok {
			rank[v] = len(rank) + 1
		}
	}
	bit := NewFenwick(len(rank))
	result := make([]int, len(nums))
	for i := len(nums) - 1; i >= 0; i-- {
		result[i] = bit.PrefixSum(rank[nums[i]] - 1)
		bit.Add(rank[nums[i]], 1)
	}
	return result
}
