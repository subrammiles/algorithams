package k_dp

import "strconv"

// Digit DP, longest valid parentheses, k-equal-subsets bitmask DP (full explanations in Java).
//
//	CountSpecialNumbers      digit DP over (pos, mask, tight, started); memo for !tight && started
//	LongestValidParentheses  stack of indices with the last unmatched ')' as the base
//	CanPartitionKSubsets     dp[mask] = fill level of the current bucket, -1 if unreachable

func CountSpecialNumbers(n int) int {
	digits := strconv.Itoa(n)
	memo := make([][1 << 10]int, len(digits))
	for i := range memo {
		for j := range memo[i] {
			memo[i][j] = -1
		}
	}
	var gofn func(pos, mask int, tight, started bool) int
	gofn = func(pos, mask int, tight, started bool) int {
		if pos == len(digits) {
			if started {
				return 1
			}
			return 0
		}
		if !tight && started && memo[pos][mask] >= 0 {
			return memo[pos][mask]
		}
		limit := 9
		if tight {
			limit = int(digits[pos] - '0')
		}
		total := 0
		for d := 0; d <= limit; d++ {
			nextTight := tight && d == limit
			if !started && d == 0 {
				total += gofn(pos+1, mask, nextTight, false)
			} else if mask&(1<<d) == 0 {
				total += gofn(pos+1, mask|1<<d, nextTight, true)
			}
		}
		if !tight && started {
			memo[pos][mask] = total
		}
		return total
	}
	return gofn(0, 0, true, false)
}

func LongestValidParentheses(s string) int {
	stack := []int{-1}
	best := 0
	for i := 0; i < len(s); i++ {
		if s[i] == '(' {
			stack = append(stack, i)
			continue
		}
		stack = stack[:len(stack)-1]
		if len(stack) == 0 {
			stack = append(stack, i)
		} else {
			best = max(best, i-stack[len(stack)-1])
		}
	}
	return best
}

func CanPartitionKSubsets(nums []int, k int) bool {
	total := 0
	for _, x := range nums {
		total += x
	}
	if total%k != 0 {
		return false
	}
	target, n := total/k, len(nums)
	dp := make([]int, 1<<n)
	for i := range dp {
		dp[i] = -1
	}
	dp[0] = 0
	for mask := 0; mask < 1<<n; mask++ {
		if dp[mask] < 0 {
			continue
		}
		for i := 0; i < n; i++ {
			next := mask | 1<<i
			if next != mask && dp[next] < 0 && dp[mask]+nums[i] <= target {
				dp[next] = (dp[mask] + nums[i]) % target
			}
		}
	}
	return dp[1<<n-1] == 0
}
