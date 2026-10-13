package a_twopointers_slidingwindow

import "sort"

// ThreeSum returns all unique triplets summing to 0.
// Sort, fix i, two pointers on the rest, skip equal neighbours. O(n^2).
func ThreeSum(nums []int) [][]int {
	a := append([]int(nil), nums...)
	sort.Ints(a)
	result := [][]int{}
	for i := 0; i < len(a)-2; i++ {
		if i > 0 && a[i] == a[i-1] {
			continue
		}
		if a[i] > 0 {
			break
		}
		lo, hi := i+1, len(a)-1
		for lo < hi {
			switch sum := a[i] + a[lo] + a[hi]; {
			case sum < 0:
				lo++
			case sum > 0:
				hi--
			default:
				result = append(result, []int{a[i], a[lo], a[hi]})
				for lo < hi && a[lo] == a[lo+1] {
					lo++
				}
				for lo < hi && a[hi] == a[hi-1] {
					hi--
				}
				lo, hi = lo+1, hi-1
			}
		}
	}
	return result
}
