package algorithms.dynamic_programming;

import common.Check;

/*
 * Grid DP: dp[r][c] depends on the cell above and the cell to the left.
 * A single row array is enough (dp[c] += dp[c-1] reuses the previous row in place).
 *
 *  - Unique paths (only right/down): dp[c] = dp[c] (from above) + dp[c-1] (from left).
 *    Closed form C(m+n-2, m-1) - worth mentioning.
 *  - With obstacles: a blocked cell has 0 ways.
 *  - Minimum path sum: dp = grid + min(above, left).
 *  - Maximal square of 1s: side(r,c) = 1 + min(top, left, top-left). The min is the key insight:
 *    a square can only grow as far as its smallest neighbouring square allows.
 */
public class B_GridDP {

	static int uniquePaths(int m, int n) {
		int[] dp = new int[n];
		java.util.Arrays.fill(dp, 1);
		for (int r = 1; r < m; r++) {
			for (int c = 1; c < n; c++) dp[c] += dp[c - 1];
		}
		return dp[n - 1];
	}

	static int uniquePathsWithObstacles(int[][] grid) {
		int n = grid[0].length;
		int[] dp = new int[n];
		dp[0] = grid[0][0] == 1 ? 0 : 1;
		for (int[] row : grid) {
			for (int c = 0; c < n; c++) {
				if (row[c] == 1) dp[c] = 0;
				else if (c > 0) dp[c] += dp[c - 1];
			}
		}
		return dp[n - 1];
	}

	static int minPathSum(int[][] grid) {
		int n = grid[0].length;
		int[] dp = new int[n];
		for (int r = 0; r < grid.length; r++) {
			for (int c = 0; c < n; c++) {
				if (r == 0 && c == 0) dp[c] = grid[0][0];
				else if (r == 0) dp[c] = dp[c - 1] + grid[r][c];
				else if (c == 0) dp[c] = dp[c] + grid[r][c];
				else dp[c] = Math.min(dp[c], dp[c - 1]) + grid[r][c];
			}
		}
		return dp[n - 1];
	}

	static int maximalSquare(String[] matrix) {
		int rows = matrix.length, cols = matrix[0].length(), best = 0;
		int[][] side = new int[rows + 1][cols + 1];
		for (int r = 1; r <= rows; r++) {
			for (int c = 1; c <= cols; c++) {
				if (matrix[r - 1].charAt(c - 1) == '1') {
					side[r][c] = 1 + Math.min(side[r - 1][c - 1], Math.min(side[r - 1][c], side[r][c - 1]));
					best = Math.max(best, side[r][c]);
				}
			}
		}
		return best * best;
	}

	public static void main(String[] args) {
		Check.eq("unique paths 3x7", uniquePaths(3, 7), 28);
		Check.eq("with obstacle", uniquePathsWithObstacles(new int[][] { { 0, 0, 0 }, { 0, 1, 0 }, { 0, 0, 0 } }), 2);
		Check.eq("blocked start", uniquePathsWithObstacles(new int[][] { { 1 } }), 0);
		Check.eq("min path sum", minPathSum(new int[][] { { 1, 3, 1 }, { 1, 5, 1 }, { 4, 2, 1 } }), 7);
		Check.eq("maximal square", maximalSquare(new String[] { "10100", "10111", "11111", "10010" }), 4);
		Check.eq("maximal square none", maximalSquare(new String[] { "0" }), 0);
	}
}
