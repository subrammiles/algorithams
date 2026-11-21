// Package t_arrays: in-place array techniques (full explanations in the Java version).
//
//	ProductExceptSelf     prefix pass then suffix pass, no division (zeros!)
//	FirstMissingPositive  cyclic sort: value v belongs at index v-1
//	FindDuplicates        flip the sign of nums[v-1] as a "seen v" bit
//	SortColors            Dutch national flag; don't advance mid after swapping with high
//	NextPermutation       pivot, swap with rightmost bigger, reverse suffix
//	Rotate                three reversals, k %= n
//	CorpFlightBookings    difference array + prefix sum
//	MajorityElementII     Boyer-Moore with two candidates, then verify (> n/3)
package t_arrays

import "sort"

func ProductExceptSelf(nums []int) []int {
	out := make([]int, len(nums))
	left := 1
	for i, x := range nums {
		out[i] = left
		left *= x
	}
	right := 1
	for i := len(nums) - 1; i >= 0; i-- {
		out[i] *= right
		right *= nums[i]
	}
	return out
}

func FirstMissingPositive(input []int) int {
	nums := append([]int(nil), input...)
	n := len(nums)
	for i := range nums {
		for nums[i] >= 1 && nums[i] <= n && nums[nums[i]-1] != nums[i] {
			j := nums[i] - 1
			nums[i], nums[j] = nums[j], nums[i]
		}
	}
	for i, v := range nums {
		if v != i+1 {
			return i + 1
		}
	}
	return n + 1
}

func FindDuplicates(input []int) []int {
	nums := append([]int(nil), input...)
	var out []int
	for i := range nums {
		v := nums[i]
		if v < 0 {
			v = -v
		}
		if nums[v-1] < 0 {
			out = append(out, v)
		} else {
			nums[v-1] = -nums[v-1]
		}
	}
	return out
}

func SortColors(a []int) {
	low, mid, high := 0, 0, len(a)-1
	for mid <= high {
		switch a[mid] {
		case 0:
			a[low], a[mid] = a[mid], a[low]
			low++
			mid++
		case 1:
			mid++
		default:
			a[mid], a[high] = a[high], a[mid]
			high--
		}
	}
}

func reverse(a []int, i, j int) {
	for ; i < j; i, j = i+1, j-1 {
		a[i], a[j] = a[j], a[i]
	}
}

func NextPermutation(a []int) {
	i := len(a) - 2
	for i >= 0 && a[i] >= a[i+1] {
		i--
	}
	if i >= 0 {
		j := len(a) - 1
		for a[j] <= a[i] {
			j--
		}
		a[i], a[j] = a[j], a[i]
	}
	reverse(a, i+1, len(a)-1)
}

func Rotate(a []int, k int) {
	n := len(a)
	if n == 0 {
		return
	}
	k %= n
	reverse(a, 0, n-1)
	reverse(a, 0, k-1)
	reverse(a, k, n-1)
}

// CorpFlightBookings: bookings[i] = {first, last, seats} on flights 1..n.
func CorpFlightBookings(bookings [][3]int, n int) []int {
	diff := make([]int, n+1)
	for _, b := range bookings {
		diff[b[0]-1] += b[2]
		diff[b[1]] -= b[2]
	}
	out := make([]int, n)
	running := 0
	for i := 0; i < n; i++ {
		running += diff[i]
		out[i] = running
	}
	return out
}

func MajorityElementII(nums []int) []int {
	c1, c2, n1, n2 := 0, 1, 0, 0
	for _, x := range nums {
		switch {
		case x == c1:
			n1++
		case x == c2:
			n2++
		case n1 == 0:
			c1, n1 = x, 1
		case n2 == 0:
			c2, n2 = x, 1
		default:
			n1--
			n2--
		}
	}
	n1, n2 = 0, 0
	for _, x := range nums {
		if x == c1 {
			n1++
		} else if x == c2 {
			n2++
		}
	}
	out := []int{}
	if n1 > len(nums)/3 {
		out = append(out, c1)
	}
	if n2 > len(nums)/3 {
		out = append(out, c2)
	}
	sort.Ints(out)
	return out
}
