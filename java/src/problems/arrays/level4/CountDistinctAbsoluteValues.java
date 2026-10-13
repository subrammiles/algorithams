package problems.arrays.level4;

import java.util.*;

/*
 Given a sorted array of integers, return the number of distinct absolute values among the elements of the array. 
 The input can contain duplicates values. 
 Input: [-3, -2, 0, 3, 4, 5]
Output: 5
0, 2, 3, 4 and 5
 
 Input:  [0, 0, 0]
Output: 1 
 */
public class CountDistinctAbsoluteValues {

	/* Method 1 */
	// The function returns number of
	// distinct absolute values among
	// the elements of the array
	static int distinctCount(int arr[], int n) {
		Set<Integer> s = new HashSet<Integer>();

		// Note that set keeps only one
		// copy even if we try to insert
		// multiple values
		for (int i = 0; i < n; i++)
			s.add(Math.abs(arr[i]));

		return s.size();

	}

	/* Method 2 */
	/*
	 * The function returns return number // of distinct absolute values // among
	 * the elements of the array
	 */
	static int distinctCount2(int arr[], int n) {
		// initialize count as number of elements
		int count = n;
		int i = 0, j = n - 1;
		int sum = 0;

		System.out.println("i = " + i + " j =" + j);

		while (i < j) {
			// Remove duplicate elements from the
			// left of the current window (i, j)
			// and also decrease the count
			while (i != j && arr[i] == arr[i + 1]) {
				System.out.print("");
				count--;
				i++;
			}
			// Remove duplicate elements from the
			// right of the current window (i, j)
			// and also decrease the count
			while (i != j && arr[j] == arr[j - 1]) {
				System.out.print("");
				count--;
				j--;
			}
			// break if only one element is left
			if (i == j) {
				break;
			}

			// Now look for the zero sum pair
			// in current window (i, j)
			sum = arr[i] + arr[j];

			if (sum == 0) {
				// decrease the count if (positive,
				// negative) pair is encountered
				count--;
				i++;
				j--;
			} else if (sum < 0) {
				i++;
			} else {
				j--;
			}

		}

		return count;
	}

	// Driver code
	public static void main(String[] args) {
		int arr[] = { -2, -1, 0, 1,1, 2, 3 };
		int n = arr.length;

		distinctCount2(arr, n);

	}

}
