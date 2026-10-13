package dynamic_programming

func UniquePaths(m, n int) int {
	dp := make([]int, n)
	for c := range dp {
		dp[c] = 1
	}
	for r := 1; r < m; r++ {
		for c := 1; c < n; c++ {
			dp[c] += dp[c-1]
		}
	}
	return dp[n-1]
}

func UniquePathsWithObstacles(grid [][]int) int {
	dp := make([]int, len(grid[0]))
	if grid[0][0] == 0 {
		dp[0] = 1
	}
	for _, row := range grid {
		for c, cell := range row {
			if cell == 1 {
				dp[c] = 0
			} else if c > 0 {
				dp[c] += dp[c-1]
			}
		}
	}
	return dp[len(dp)-1]
}

func MinPathSum(grid [][]int) int {
	dp := make([]int, len(grid[0]))
	for r, row := range grid {
		for c, v := range row {
			switch {
			case r == 0 && c == 0:
				dp[c] = v
			case r == 0:
				dp[c] = dp[c-1] + v
			case c == 0:
				dp[c] += v
			default:
				dp[c] = min(dp[c], dp[c-1]) + v
			}
		}
	}
	return dp[len(dp)-1]
}

// MaximalSquare: side = 1 + min(top, left, top-left).
func MaximalSquare(matrix []string) int {
	rows, cols, best := len(matrix), len(matrix[0]), 0
	side := make([][]int, rows+1)
	for i := range side {
		side[i] = make([]int, cols+1)
	}
	for r := 1; r <= rows; r++ {
		for c := 1; c <= cols; c++ {
			if matrix[r-1][c-1] == '1' {
				side[r][c] = 1 + min(side[r-1][c-1], side[r-1][c], side[r][c-1])
				best = max(best, side[r][c])
			}
		}
	}
	return best * best
}
