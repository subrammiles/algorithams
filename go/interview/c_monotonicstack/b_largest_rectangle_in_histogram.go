package c_monotonicstack

// LargestRectangle uses an increasing stack; a sentinel 0 flushes it at the end. O(n).
func LargestRectangle(h []int) int {
	var stack []int
	best := 0
	for i := 0; i <= len(h); i++ {
		cur := 0
		if i < len(h) {
			cur = h[i]
		}
		for len(stack) > 0 && h[stack[len(stack)-1]] > cur {
			height := h[stack[len(stack)-1]]
			stack = stack[:len(stack)-1]
			left := -1
			if len(stack) > 0 {
				left = stack[len(stack)-1]
			}
			best = max(best, height*(i-left-1))
		}
		stack = append(stack, i)
	}
	return best
}

// MaximalRectangle builds a histogram per row of a 0/1 matrix.
func MaximalRectangle(matrix []string) int {
	if len(matrix) == 0 {
		return 0
	}
	heights := make([]int, len(matrix[0]))
	best := 0
	for _, row := range matrix {
		for c := range heights {
			if row[c] == '1' {
				heights[c]++
			} else {
				heights[c] = 0
			}
		}
		best = max(best, LargestRectangle(heights))
	}
	return best
}
