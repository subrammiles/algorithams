package a_twopointers_slidingwindow

// TrapPrefix: water at i = min(maxLeft[i], maxRight[i]) - h[i]. O(n) time, O(n) space.
func TrapPrefix(h []int) int {
	n := len(h)
	if n == 0 {
		return 0
	}
	left, right := make([]int, n), make([]int, n)
	left[0], right[n-1] = h[0], h[n-1]
	for i := 1; i < n; i++ {
		left[i] = max(left[i-1], h[i])
	}
	for i := n - 2; i >= 0; i-- {
		right[i] = max(right[i+1], h[i])
	}
	water := 0
	for i := range h {
		water += min(left[i], right[i]) - h[i]
	}
	return water
}

// TrapTwoPointers moves the side with the smaller max inward. O(n) time, O(1) space.
func TrapTwoPointers(h []int) int {
	lo, hi, leftMax, rightMax, water := 0, len(h)-1, 0, 0, 0
	for lo < hi {
		if h[lo] < h[hi] {
			leftMax = max(leftMax, h[lo])
			water += leftMax - h[lo]
			lo++
		} else {
			rightMax = max(rightMax, h[hi])
			water += rightMax - h[hi]
			hi--
		}
	}
	return water
}
