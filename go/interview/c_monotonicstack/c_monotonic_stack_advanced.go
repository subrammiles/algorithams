package c_monotonicstack

import "math"

// Harder monotonic-stack problems (full explanations in the Java version).
//
//	SumSubarrayMins         contribution technique: previous strictly smaller, next smaller-or-equal
//	RemoveDuplicateLetters  greedy increasing stack; pop a bigger top if it appears again later
//	Find132Pattern          right-to-left decreasing stack; popped values are candidate "2"s

const mod = 1_000_000_007

func SumSubarrayMins(a []int) int {
	n := len(a)
	left, right := make([]int, n), make([]int, n)
	var stack []int
	for i := 0; i < n; i++ {
		for len(stack) > 0 && a[stack[len(stack)-1]] >= a[i] {
			stack = stack[:len(stack)-1]
		}
		if len(stack) == 0 {
			left[i] = i + 1
		} else {
			left[i] = i - stack[len(stack)-1]
		}
		stack = append(stack, i)
	}
	stack = stack[:0]
	for i := n - 1; i >= 0; i-- {
		for len(stack) > 0 && a[stack[len(stack)-1]] > a[i] {
			stack = stack[:len(stack)-1]
		}
		if len(stack) == 0 {
			right[i] = n - i
		} else {
			right[i] = stack[len(stack)-1] - i
		}
		stack = append(stack, i)
	}
	total := 0
	for i := range a {
		total = (total + a[i]*left[i]%mod*right[i]) % mod
	}
	return total
}

func RemoveDuplicateLetters(s string) string {
	var last [26]int
	for i := 0; i < len(s); i++ {
		last[s[i]-'a'] = i
	}
	var inStack [26]bool
	var stack []byte
	for i := 0; i < len(s); i++ {
		c := s[i]
		if inStack[c-'a'] {
			continue
		}
		for len(stack) > 0 && stack[len(stack)-1] > c && last[stack[len(stack)-1]-'a'] > i {
			inStack[stack[len(stack)-1]-'a'] = false
			stack = stack[:len(stack)-1]
		}
		stack = append(stack, c)
		inStack[c-'a'] = true
	}
	return string(stack)
}

func Find132Pattern(a []int) bool {
	var stack []int
	third := math.MinInt
	for i := len(a) - 1; i >= 0; i-- {
		if a[i] < third {
			return true
		}
		for len(stack) > 0 && stack[len(stack)-1] < a[i] {
			third = stack[len(stack)-1]
			stack = stack[:len(stack)-1]
		}
		stack = append(stack, a[i])
	}
	return false
}
