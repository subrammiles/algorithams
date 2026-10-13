package problems.arrays.level1;

import java.util.Arrays;

/*Find minimum and maximum elements in a given  array*/
public class D_FindMinAndMaxInAnArray {
	
	private static void minMaxInanArray(int[] myArray) {
		int max = myArray[0];
		int min = myArray[0];
		for (int i = 0; i < myArray.length; i++) {
			if (myArray[i] > max) {
				max = myArray[i];
			}
			if (myArray[i] < min) {
				min = myArray[i];
			}
		}
		System.out.println(max);
		System.out.println(min);
	}

	private static void minMaxInanArray2(int[] myArray) {

		boolean isOddLength = isOddLengthArray(myArray);
		int max = Integer.MIN_VALUE;
		int min = Integer.MAX_VALUE;
		int n = myArray.length;
		if (isOddLength) {
			n--;
		}
		for (int i = 0; i < n; i = i + 2) {
			int tempMax = 0;
			int tempMin = 0;
			if (myArray[i] > myArray[i + 1]) {
				tempMax = myArray[i];
				tempMin = myArray[i + 1];
			} else {
				tempMax = myArray[i + 1];
				tempMin = myArray[i];
			}

			if (tempMax > max) {
				max = tempMax;
			}
			if (tempMin < min) {
				min = tempMin;
			}
		}

		if (isOddLength) {
			if (myArray[myArray.length] > max) {
				max = myArray[myArray.length];
			}
			if (myArray[myArray.length] < min) {
				min = myArray[myArray.length];
			}
		}

		System.out.println(max);
		System.out.println(min);
	}

	/* Helper methods */
	private static boolean isOddLengthArray(int[] myArray) {

		if (myArray.length % 2 == 0) {
			return false;
		} else {
			return true;
		}
	}

	static int recursion1(int[] myArray, int i, int minValue) {
		// Get Min value
		if (i < 0) {
			return minValue;
		} else {
			int min = minValue < myArray[i] ? minValue : myArray[i];
			return recursion1(myArray, i - 1, min);
		}
	}

	public static void main(String[] args) {
		test();
	}



	static void test() {
		/* Iteration */
		int[] myArray = new int[5];
		myArray = new int[] { 2, 2, 9, 3, 4, 1, 7, 5 };
		minMaxInanArray(myArray);
		minMaxInanArray2(myArray);
		/* Recursion */
		int minValue = recursion1(myArray, myArray.length - 1, myArray[myArray.length - 1]);
		System.out.println(minValue);
	}
}
