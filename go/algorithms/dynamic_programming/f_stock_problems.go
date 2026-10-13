package dynamic_programming

import "math"

// Stock problems as one state machine:
//
//	hold' = max(hold, cash - price)   cash' = max(cash, hold + price - fee)
func MaxProfitOnce(prices []int) int {
	low, best := math.MaxInt, 0
	for _, p := range prices {
		low = min(low, p)
		best = max(best, p-low)
	}
	return best
}

func MaxProfitUnlimited(prices []int, fee int) int {
	hold, cash := math.MinInt/2, 0
	for _, p := range prices {
		hold, cash = max(hold, cash-p), max(cash, hold+p-fee)
	}
	return cash
}

func MaxProfitK(k int, prices []int) int {
	if k >= len(prices)/2 {
		return MaxProfitUnlimited(prices, 0)
	}
	hold, cash := make([]int, k+1), make([]int, k+1)
	for t := range hold {
		hold[t] = math.MinInt / 2
	}
	for _, p := range prices {
		for t := k; t >= 1; t-- {
			cash[t] = max(cash[t], hold[t]+p)
			hold[t] = max(hold[t], cash[t-1]-p)
		}
	}
	return cash[k]
}

// MaxProfitCooldown: buying uses the cash from two days ago.
func MaxProfitCooldown(prices []int) int {
	hold, cash, cashTwoDaysAgo := math.MinInt/2, 0, 0
	for _, p := range prices {
		hold, cash, cashTwoDaysAgo = max(hold, cashTwoDaysAgo-p), max(cash, hold+p), cash
	}
	return cash
}
