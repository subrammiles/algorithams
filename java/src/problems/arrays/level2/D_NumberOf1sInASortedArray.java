package problems.arrays.level2;

/*
 Given a sorted binary array, efficiently count the total number of 1’s in it.

For example,

Input:  nums[] = [0, 0, 0, 0, 1, 1, 1]
 
Output: The total number of 1’s present is 3
 
 
Input:  nums[] = [0, 0, 1, 1, 1, 1, 1]
 
Output: The total number of 1’s present is 5 
 
 * */
public class D_NumberOf1sInASortedArray {

//////////////////////////////////////
/////////Method1 Linear//////////////
/////////////////////////////////////

	static void method1(int[] a, int n) {
		for (int i = 0; i < n; i++) {
			if (a[i] == 1) {
				System.out.println(n - i);
				break;
			}
		}
	}

//////////////////////////////////////
/////////Method2 Log n//////////////
/////////////////////////////////////

	static void method2(int[] a) {
		int c = count(a, 0, a.length - 1);
		System.out.println(c);
	}

	static int count(int[] arr, int left, int right) {
		if (arr == null || arr.length == 0) {
			return 0;
		}

		// if the last array element is 0, no 1's can be present since it is sorted
		if (arr[right] == 0) {
			return 0;
		}

		// if the first array element is 1, all its elements are ones only since it is
		// sorted
		if (arr[left] == 1) {
			return (right - left + 1);
		}

		// divide the array into left and right subarray and recur
		int mid = (left + right) / 2;
		return count(arr, left, mid) + count(arr, mid + 1, right);
	}

	public static void main(String[] args) {

		int[] myArray = new int[] { 0, 0, 1,1,1,1,1};
		int n = myArray.length;
		// method1(myArray,n);
		method2(myArray);
	}

}
