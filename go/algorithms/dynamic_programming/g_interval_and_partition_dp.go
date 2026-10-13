package dynamic_programming

import "math"

func MatrixChainOrder(dims []int) int {
	n := len(dims) - 1
	cost := make([][]int, n+1)
	for i := range cost {
		cost[i] = make([]int, n+1)
	}
	for length := 2; length <= n; length++ {
		for i := 1; i+length-1 <= n; i++ {
			j := i + length - 1
			cost[i][j] = math.MaxInt
			for k := i; k < j; k++ {
				cost[i][j] = min(cost[i][j], cost[i][k]+cost[k+1][j]+dims[i-1]*dims[k]*dims[j])
			}
		}
	}
	return cost[1][n]
}

func MinCutPalindromePartition(s string) int {
	n := len(s)
	pal := make([][]bool, n)
	for i := range pal {
		pal[i] = make([]bool, n)
	}
	cuts := make([]int, n)
	for j := 0; j < n; j++ {
		cuts[j] = j
		for i := 0; i <= j; i++ {
			if s[i] == s[j] && (j-i < 2 || pal[i+1][j-1]) {
				pal[i][j] = true
				if i == 0 {
					cuts[j] = 0
				} else {
					cuts[j] = min(cuts[j], cuts[i-1]+1)
				}
			}
		}
	}
	return cuts[n-1]
}

// EggDrop: floors(m, k) = floors(m-1, k-1) + floors(m-1, k) + 1; smallest m covering all floors.
func EggDrop(eggs, floors int) int {
	covered, moves := make([]int, eggs+1), 0
	for covered[eggs] < floors {
		moves++
		for k := eggs; k >= 1; k-- {
			covered[k] = covered[k-1] + covered[k] + 1
		}
	}
	return moves
}
