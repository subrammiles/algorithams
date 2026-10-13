package problems.arrays.level1;

/*Travel all the elements in an array*/
/*TC always >=n */
public class A_TravelAllArrayElements {

	public static void usingForLoop1(int[] myArray) {
		for (int i = 0; i < myArray.length; i++) {
			System.out.println(myArray[i]);
		}
	}

	public static void usingForLoop2(int[] myArray) {
		for (int a : myArray) {
			System.out.println(a);
		}
	}

	public static void usingWhileLoop(int[] myArray) {
		int i = myArray.length - 1;
		while (i >= 0) {
			System.out.println(myArray[i]);
			i--;
		}
	}

	public static void usingRecursion(int[] a, int index) {
		if (index < 0) {
			return;
		}
		System.out.println(a[index]);
		usingRecursion(a, index - 1);
	}

	public static void main(String[] args) {
		int[] myArray = new int[5];
		myArray = new int[] { 1, 2, 3, 4, 5 };
		usingWhileLoop(myArray);
		usingRecursion(myArray, myArray.length - 1);
	}

}
