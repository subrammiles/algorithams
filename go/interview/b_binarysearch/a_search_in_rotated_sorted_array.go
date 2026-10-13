// Package b_binarysearch: binary search on sorted/rotated data and on the answer space.
package b_binarysearch

// Search finds target in a rotated sorted array of distinct values. O(log n).
func Search(a []int, target int) int {
	lo, hi := 0, len(a)-1
	for lo <= hi {
		mid := lo + (hi-lo)/2
		if a[mid] == target {
			return mid
		}
		if a[lo] <= a[mid] { // left half sorted
			if a[lo] <= target && target < a[mid] {
				hi = mid - 1
			} else {
				lo = mid + 1
			}
		} else if a[mid] < target && target <= a[hi] {
			lo = mid + 1
		} else {
			hi = mid - 1
		}
	}
	return -1
}

// FindMinIndex returns the rotation point.
func FindMinIndex(a []int) int {
	lo, hi := 0, len(a)-1
	for lo < hi {
		mid := lo + (hi-lo)/2
		if a[mid] > a[hi] {
			lo = mid + 1
		} else {
			hi = mid
		}
	}
	return lo
}
