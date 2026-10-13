// Package c_monotonicstack: next/previous greater or smaller element in O(n).
package c_monotonicstack

func NextGreaterCircular(nums []int) []int {
	n := len(nums)
	result := make([]int, n)
	for i := range result {
		result[i] = -1
	}
	var stack []int
	for i := 0; i < 2*n; i++ {
		x := nums[i%n]
		for len(stack) > 0 && nums[stack[len(stack)-1]] < x {
			result[stack[len(stack)-1]] = x
			stack = stack[:len(stack)-1]
		}
		if i < n {
			stack = append(stack, i)
		}
	}
	return result
}

func DailyTemperatures(t []int) []int {
	wait := make([]int, len(t))
	var stack []int
	for i, temp := range t {
		for len(stack) > 0 && t[stack[len(stack)-1]] < temp {
			j := stack[len(stack)-1]
			stack = stack[:len(stack)-1]
			wait[j] = i - j
		}
		stack = append(stack, i)
	}
	return wait
}

func StockSpan(prices []int) []int {
	span := make([]int, len(prices))
	var stack []int
	for i, p := range prices {
		for len(stack) > 0 && prices[stack[len(stack)-1]] <= p {
			stack = stack[:len(stack)-1]
		}
		if len(stack) == 0 {
			span[i] = i + 1
		} else {
			span[i] = i - stack[len(stack)-1]
		}
		stack = append(stack, i)
	}
	return span
}
