package problems.arrays.level4;

import java.util.Arrays;

public class ArrangeArrayInAlternatePositiveAndNegative {

	/* Method 1 */
	private void rotateSubarrayRightByOneElement(int[] array, int low, int high) {
		int lastElement = array[high];

		for (int i = high; i > low; i--) {
			array[i] = array[i - 1];
		}

		array[low] = lastElement;
	}

	private boolean notAtRightPosition(int[] array, int index) {
		// even indices should have positive elements in them
		if ((index % 2) == 0) {
			// if negative element
			if ((array[index]) < 0) {
				return true;
			} else {
				return false;
			}
		} else // odd indices should have negative elements in them
		{
			if ((array[index]) < 0) {
				return false;
			} else {
				return true;
			}

		}
	}

	private int getNextOpposite(int[] array, int index) {
		for (int i = index + 1; i < array.length; i++) {
			if ((array[i] * array[index]) < 0) {
				return i;
			}
		}

		return -1;
	}

	public void reArrangePositivesNegatives(int[] array) {
		for (int i = 0; i < array.length; i++) {
			if (notAtRightPosition(array, i)) {
				int nextOppositeIndex = getNextOpposite(array, i);
				if (nextOppositeIndex != -1) {
					rotateSubarrayRightByOneElement(array, i, nextOppositeIndex);
				} else // no more opposite signed elements
				{
					break;
				}
			}
		}
	}

	/* Method 2 */

	void rightrotate(int arr[], int n, int outofplace, int cur) {
		int tmp = arr[cur];
		for (int i = cur; i > outofplace; i--)
			arr[i] = arr[i - 1];
		arr[outofplace] = tmp;
	}

	void rearrange(int arr[], int n) {
		int outofplace = -1;
		for (int index = 0; index < n; index++) {
			if (outofplace >= 0) {

				if (((arr[index] >= 0) && (arr[outofplace] < 0)) || ((arr[index] < 0) && (arr[outofplace] >= 0))) {
					rightrotate(arr, n, outofplace, index);

					// the new out-of-place entry is now 2
					// steps ahead
					if (index - outofplace >= 2)
						outofplace = outofplace + 2;
					else
						outofplace = -1;
				}
			} else if (outofplace == -1) {
				/* if no entry has been flagged out-of-place */
				// check if current entry is out-of-place
				boolean isPositiveNumber = arr[index] >= 0;
				boolean isEvenIndex = (index & 0x01) == 0;
				boolean isNegativeNumber = (arr[index] < 0);
				boolean isOddIndex = (index & 0x01) == 1;

				if (((isPositiveNumber) && (isEvenIndex)) || (isNegativeNumber && isOddIndex))
					outofplace = index;
			}
		}
	}

	/* Method 3 */
	void fill1(int a[], int neg, int pos) {
		if (neg % 2 == 1) {
			for (int i = 1; i < neg; i += 2) {
				int c = a[i];
				int d = a[i + neg];
				int temp = c;
				a[i] = d;
				a[i + neg] = temp;
			}
		} else {
			for (int i = 1; i <= neg; i += 2) {
				int c = a[i];
				int d = a[i + neg - 1];
				int temp = c;
				a[i] = d;
				a[i + neg - 1] = temp;
			}
		}
	}

// function which works in the condition when number of
	// negative numbers are greater than positive numbers
	void fill2(int a[], int neg, int pos) {
		if (pos % 2 == 1) {
			for (int i = 1; i < pos; i += 2) {
				int c = a[i];
				int d = a[i + pos];
				int temp = c;
				a[i] = d;
				a[i + pos] = temp;
			}
		} else {
			for (int i = 1; i <= pos; i += 2) {
				int c = a[i];
				int d = a[i + pos - 1];
				int temp = c;
				a[i] = d;
				a[i + pos - 1] = temp;
			}
		}
	}

	void reverse(int a[], int n) {
		int i, k, t;
		for (i = 0; i < n / 2; i++) {
			t = a[i];
			a[i] = a[n - i - 1];
			a[n - i - 1] = t;
		}
	}

	void print(int a[], int n) {
		System.out.println(Arrays.toString(a));
	}

	void method3() {
		// Given array
		int[] arr = { 1,2,3,-4,-1,4 };
		int n = arr.length;

		int neg = 0, pos = 0;
		for (int i = 0; i < n; i++) {
			if (arr[i] < 0)
				neg++;
			else
				pos++;
		}
		// Sort the array
		Arrays.sort(arr);

		if (neg <= pos) {
			fill1(arr, neg, pos);
		} else {
			// reverse the array in this condition
			reverse(arr, n);
			fill2(arr, neg, pos);
		}
		print(arr, n);
	}

	/* Method 3 ends */
	public static void main(String[] args) {
		ArrangeArrayInAlternatePositiveAndNegative solution = new ArrangeArrayInAlternatePositiveAndNegative();

		int[] testArray = { 1,2,3-4,-1,4};

		/* Method 1 */
		// solution.reArrangePositivesNegatives(testArray);

		/* Method 2 */
		//solution.rearrange(testArray, testArray.length);

		//System.out.println(Arrays.toString(testArray));
		/*Method 3*/
		solution.method3();

	}

}
