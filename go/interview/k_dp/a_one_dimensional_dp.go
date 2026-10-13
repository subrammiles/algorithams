// Package k_dp: dynamic programming families (see java/src/interview/k_dp/README.md for the method).
package k_dp

import "strconv"

func Rob(nums []int) int {
	prev2, prev1 := 0, 0
	for _, x := range nums {
		prev2, prev1 = prev1, max(prev1, prev2+x)
	}
	return prev1
}

// CoinChangeMin: dp[a] = 1 + min(dp[a - coin]). Greedy fails for {1,3,4}, amount 6.
func CoinChangeMin(coins []int, amount int) int {
	dp := make([]int, amount+1)
	for a := 1; a <= amount; a++ {
		dp[a] = amount + 1
		for _, c := range coins {
			if c <= a {
				dp[a] = min(dp[a], dp[a-c]+1)
			}
		}
	}
	if dp[amount] > amount {
		return -1
	}
	return dp[amount]
}

// CoinChangeWays loops coins outer to count combinations, not permutations.
func CoinChangeWays(coins []int, amount int) int {
	dp := make([]int, amount+1)
	dp[0] = 1
	for _, c := range coins {
		for a := c; a <= amount; a++ {
			dp[a] += dp[a-c]
		}
	}
	return dp[amount]
}

func NumDecodings(s string) int {
	prev2, prev1 := 1, 1
	if s[0] == '0' {
		prev1 = 0
	}
	for i := 1; i < len(s); i++ {
		cur := 0
		if s[i] != '0' {
			cur = prev1
		}
		if two, _ := strconv.Atoi(s[i-1 : i+1]); two >= 10 && two <= 26 {
			cur += prev2
		}
		prev2, prev1 = prev1, cur
	}
	return prev1
}
