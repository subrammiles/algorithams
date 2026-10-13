package problems.arrays.level1;

/*
Count Number of occurences in a sorted array
[1,2,2,2,2,3,4,7,8,8],number 2
* */
public class G_CountFrequencyInAnUnSortedArray {

//////////////////////////////////////
/////////Method1 /////////////////////
/////////////////////////////////////
	public static void method1(int[] a, int number) {
		int count = 0;
		for (int i = 0; i < a.length; i++) {
			if (number == a[i]) {
				count++;
			}
		}
		System.out.println(count);
	}


	public static void main(String[] args) {

		int[] a = new int[] { 1, 2, 2, 2, 2, 3, 4, 7, 8, 8 };
		method1(a, 2);

	}

}
