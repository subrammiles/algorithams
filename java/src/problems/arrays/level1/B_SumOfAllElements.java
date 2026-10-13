package problems.arrays.level1;

/*
  Find sum of all elements in an given array. 
 */

public class B_SumOfAllElements {

	private static int recursion1(int[] myArray, int sum, int index) {
		if (index < 0) {
			return sum;
		} else {
			return recursion1(myArray, sum + myArray[index], index - 1);
		}
	}

	private static int recursion2(int[] myArray, int l, int h) {
		if (l == h) {
			return myArray[l];
		} else {
			return (myArray[l] + recursion2(myArray, l + 1, h));
		}
	}

	private static int iteration1(int[] myArray) {
		int sum = 0;
		for (int i = 0; i < myArray.length; i++) {
			sum = sum + myArray[i];
		}
		System.out.println(sum);
		return sum;
	}

	public static void main(String[] args) {

		int[] myArray = new int[5];
		myArray = new int[] { 1, 2, 3, 4, 5 };

		int sum1 = recursion1(myArray, 0, myArray.length - 1);
		System.out.println(sum1);

		int sum2 = recursion2(myArray, 0, myArray.length - 1);
		System.out.println(sum2);

		int sum3 = iteration1(myArray);
		System.out.println(sum3);

	}
}
