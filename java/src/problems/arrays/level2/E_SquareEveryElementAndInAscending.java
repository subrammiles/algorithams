package problems.arrays.level2;

import java.util.Arrays;

/*
 →Given an array of sorted integers. 
→Find the square of every element in an array and an output should be in ascending order.
Input: [-5, -4, -3, -2, -1]
Output: [1,4,9,16,25]

Input: [1, 2, 3, 5, 6, 8, 9]
Output: [1,4,9,25,36,64,81]

Input: [ -3, -2, -1,1,4]
Output: [1,1,4,9,16]

 * */
public class E_SquareEveryElementAndInAscending {

//////////////////////////////////////
/////////Method1/////////////////////
/////////////////////////////////////

	private static void method1(int[] array) {
		for (int i = 0; i < array.length; i++) {
			array[i] = array[i] * array[i];
		}
		Arrays.sort(array);
		System.out.println(Arrays.toString(array));
	}

//////////////////////////////////////
/////////Method2/////////////////////
/////////////////////////////////////
	//two-pointer method //
	static void method2(int[] array) {
		int[] result = new int[array.length];
		int l = 0;
		int h = array.length - 1;
		for (int i = array.length - 1; i >= 0; i--) {
			int smallValue = array[l];
			int largeValue = array[h];
			if (Math.abs(smallValue) > Math.abs(largeValue)) {
				result[i] = smallValue * smallValue;
				l++;
			} else {
				result[i] = largeValue * largeValue;
				h--;
			}
		}
		System.out.println(Arrays.toString(array));
	}
	
//////////////////////////////////////
/////////Method3/////////////////////
/////////////////////////////////////	
	static void method3(int[] arr)
	{
	    int n = arr.length;
	    // first divide array into negative and positive part
	    int k;
	    for (k = 0; k < n; k++) {
	        if (arr[k] >= 0)
	            break;
	    }

	    int i = k - 1; // Initial index of first half
	    int j = k; // Initial index of second half
	    int ind = 0; // Initial index of temp array

	    int[] temp = new int[n];
	    while (i >= 0 && j < n) {
	        if (arr[i] * arr[i] < arr[j] * arr[j]) {
	            temp[ind] = arr[i] * arr[i];
	            i--;
	        }
	        else {
	            temp[ind] = arr[j] * arr[j];
	            j++;
	        }
	        ind++;
	    }

	    while (i >= 0) {
	        temp[ind++] = arr[i] * arr[i];
	        i--;
	    }
	    while (j < n) {
	        temp[ind++] = arr[j] * arr[j];
	        j++;
	    }
	    // copy 'temp' array into original array
	    for (int x = 0; x < n; x++)
	        arr[x] = temp[x];
	}


//////////////////////////////////////
/////////Main /////////////////////
/////////////////////////////////////
	public static void main(String[] args) {
		test();
	}

	static void test() {
		/* Iteration */
		int[] myArray1 = new int[] { 1, 2, 3, 4, 5 };
		int[] myArray2 = new int[] { -5, -4, -3, -2, -1 };
		int[] myArray3 = new int[] { -3, -2, -1, 1, 4 };
//		method1(myArray1);
//		method1(myArray2);
//		method1(myArray3);

//		method2(myArray1);
//		method2(myArray2);
//		method2(myArray3);
		
		method3(myArray1);
		method3(myArray2);
		method3(myArray3);

	}

}
