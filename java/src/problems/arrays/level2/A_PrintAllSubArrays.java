package problems.arrays.level2;

public class A_PrintAllSubArrays {

	static void printAllSubArrays(int[] arrA) {
		int arrSize = arrA.length;
		for (int startPoint = 0; startPoint < arrSize; startPoint++) {
			for (int grps = startPoint; grps <= arrSize; grps++) {
				for (int j = startPoint; j < grps; j++) {
					System.out.print(arrA[j] + " ");
				}
				System.out.println();
			}
		}
	}

	public static void main(String[] args) {
		int[] a = new int[] { 1, 2, 4 };
		printAllSubArrays(a);
	}
}
