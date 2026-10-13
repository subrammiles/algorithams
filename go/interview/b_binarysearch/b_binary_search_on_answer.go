package b_binarysearch

import "sort"

// MinEatingSpeed (Koko): smallest speed that finishes within h hours.
// sort.Search finds the first index where the predicate turns true - binary search on the answer.
func MinEatingSpeed(piles []int, h int) int {
	maxPile := 0
	for _, p := range piles {
		maxPile = max(maxPile, p)
	}
	return 1 + sort.Search(maxPile, func(i int) bool {
		speed, hours := i+1, 0
		for _, p := range piles {
			hours += (p + speed - 1) / speed
		}
		return hours <= h
	})
}

// SplitArray minimises the largest part sum when splitting nums into m parts.
func SplitArray(nums []int, m int) int {
	lo, hi := 0, 0
	for _, x := range nums {
		lo, hi = max(lo, x), hi+x
	}
	partsNeeded := func(limit int) int {
		parts, running := 1, 0
		for _, x := range nums {
			if running+x > limit {
				parts, running = parts+1, 0
			}
			running += x
		}
		return parts
	}
	for lo < hi {
		mid := lo + (hi-lo)/2
		if partsNeeded(mid) <= m {
			hi = mid
		} else {
			lo = mid + 1
		}
	}
	return lo
}
