package fundamentals.patterns;

public class AlphabetPal {

	static void printPyramid1(int n) {
		int k, l, i;

		for (i = 1; i <= n; i++) {
			for (k = 1; k <= i; k++) {
				System.out.print((char) (k + 65 - 1) + " ");
			}
			for (l = i - 1; l >= 1; l--) {
				System.out.print((char) (l + 65 - 1) + " ");
			}
			System.out.print(" \n");
		}
	}

	public static void main(String[] args) {
		
		printPyramid1(5);

	}

}
