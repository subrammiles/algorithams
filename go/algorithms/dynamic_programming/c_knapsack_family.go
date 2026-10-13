package dynamic_programming

func Knapsack01Table(wt, val []int, capacity int) int {
	dp := make([][]int, len(wt)+1)
	for i := range dp {
		dp[i] = make([]int, capacity+1)
	}
	for i := 1; i <= len(wt); i++ {
		for w := 0; w <= capacity; w++ {
			dp[i][w] = dp[i-1][w]
			if wt[i-1] <= w {
				dp[i][w] = max(dp[i][w], dp[i-1][w-wt[i-1]]+val[i-1])
			}
		}
	}
	return dp[len(wt)][capacity]
}

// Knapsack01 iterates capacity DOWN so each item is used at most once.
func Knapsack01(wt, val []int, capacity int) int {
	dp := make([]int, capacity+1)
	for i, w0 := range wt {
		for w := capacity; w >= w0; w-- {
			dp[w] = max(dp[w], dp[w-w0]+val[i])
		}
	}
	return dp[capacity]
}

// UnboundedKnapsack iterates capacity UP so items can be reused.
func UnboundedKnapsack(wt, val []int, capacity int) int {
	dp := make([]int, capacity+1)
	for i, w0 := range wt {
		for w := w0; w <= capacity; w++ {
			dp[w] = max(dp[w], dp[w-w0]+val[i])
		}
	}
	return dp[capacity]
}

func RodCutting(prices []int, length int) int {
	dp := make([]int, length+1)
	for n := 1; n <= length; n++ {
		for piece := 1; piece <= n; piece++ {
			dp[n] = max(dp[n], prices[piece-1]+dp[n-piece])
		}
	}
	return dp[length]
}

// TargetSumWays counts subsets with sum (total + target) / 2.
func TargetSumWays(nums []int, target int) int {
	total := 0
	for _, x := range nums {
		total += x
	}
	if target > total || -target > total || (total+target)%2 != 0 {
		return 0
	}
	goal := (total + target) / 2
	ways := make([]int, goal+1)
	ways[0] = 1
	for _, x := range nums {
		for s := goal; s >= x; s-- {
			ways[s] += ways[s-x]
		}
	}
	return ways[goal]
}

func MinSubsetSumDifference(nums []int) int {
	total := 0
	for _, x := range nums {
		total += x
	}
	reachable := make([]bool, total/2+1)
	reachable[0] = true
	for _, x := range nums {
		for s := total / 2; s >= x; s-- {
			reachable[s] = reachable[s] || reachable[s-x]
		}
	}
	for s := total / 2; s >= 0; s-- {
		if reachable[s] {
			return total - 2*s
		}
	}
	return total
}
