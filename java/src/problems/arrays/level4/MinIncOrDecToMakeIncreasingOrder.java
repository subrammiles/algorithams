package problems.arrays.level4;

import java.util.ArrayList;
import java.util.Arrays;

public class MinIncOrDecToMakeIncreasingOrder {

	public static int getSmallest(int[] a, int total) {
		int[] newA = new int[a.length];
		for (int i = 0; i < a.length; i++) {
			newA[i] = a[i];
		}
		Arrays.sort(newA);
		return newA[0];
	}

	public static int getLargest(int[] a, int total) {
		int[] newA = new int[a.length];
		for (int i = 0; i < a.length; i++) {
			newA[i] = a[i];
		}
		Arrays.sort(newA);
		return newA[newA.length - 1];
	}

	static void printMatrix(int[][] grid) {
		for (int r = 0; r < grid.length; r++) {
			for (int c = 0; c < grid[r].length; c++)
				System.out.print(grid[r][c] + " ");
			System.out.println();
		}
	}

	static int getMinimumOprerations(int[] a, int n) {

		/* smallest element in the array */
		int s = getSmallest(a, a.length);
		/* Finding the largest element in the array */
		int l = getLargest(a, a.length);

		int[][] dp = new int[n][l + 1];

		/* Filling the dp[][] array for base cases */
		for (int j = s; j <= l; j++) {
			dp[0][j] = Math.abs(a[0] - j);
		}
		printMatrix(dp);

		/*
		 * Using results for the first (i - 1) elements, calculate the result for the
		 * ith element.
		 */

		for (int i = 1; i < n; i++) {
			int minimum = Integer.MAX_VALUE;
			for (int j = s; j <= l; j++) {
				/*
				 * If the ith element is j then we can have any value from s to j for the
				 * (i-1)th element We choose the one that requires the minimum number of
				 * operations.
				 */
				minimum = Math.min(minimum, dp[i - 1][j]);
				dp[i][j] = minimum + Math.abs(a[i] - j);
			}
		}
		System.out.println("Final");
		printMatrix(dp);

		/*
		 * If we made the (n - 1)th element equal to j we would require dp(n-1, j)
		 * operations. We choose the minimum among all possible dp(n-1, j) where j goes
		 * from small to large
		 */

		int ans = Integer.MAX_VALUE;
		for (int j = s; j <= l; j++) {
			ans = Math.min(ans, dp[n - 1][j]);
		}

		System.out.println(ans);
		return ans;
	}

	public static void main(String[] args) {
		int a[] = new int[] { 3, 1, 2, 4 };
		// int a[] = new int[] { 5, 6, 6, 3 };
		// int a[] = new int[] { 2, 3, 2, 5,4 };
		getMinimumOprerations(a, a.length);

	}

}
