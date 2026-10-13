package fundamentals.patterns.diamond;

public class DiamondPattern {

	static void printHollowDiamondPattern(int n) {
		int i, j, space, k = 0;

		for (i = 1; i <= n; i++) {
			for (j = 1; j <= n - 1; j++) {
				System.out.print(" ");
			}
			while (k != (2 * i - 1)) {
				if ((k == 0) || (k == 2 * i - 2))
					System.out.print(" *");
				else
					System.out.print(" ");
				k++;
			}
			k = 0;
			System.out.print("\n");
		}
		n--;
		for (i = n; i >= 1; i--) {
			for (j = 0; j <= n - i; j++) {
				System.out.print(" ");
			}
			k = 0;
			while (k != (2 * i - 1)) {
				if ((k == 0) || (k == 2 * i - 2))
					System.out.print("*");
				else
					System.out.print(" ");
				k++;
			}
			System.out.print("\n");
		}
	}

	static void diamondInRectangle(int n) {

		int i, j;
		// upper half of the pattern
		for (i = 0; i < n; i++) {
			for (j = 0; j < (2 * n); j++) {
				if (i + j <= n - 1) // upper left triangle
					System.out.print("*");
				else
					System.out.print(" ");
				if ((i + n) <= j) // upper right triangle
					System.out.print("*");
				else
					System.out.print(" ");
			}
			System.out.println();
		}
		// bottom half of the pattern
		for (i = 0; i < n; i++) {
			for (j = 0; j < (2 * n); j++) {
				if (i >= j) // bottom left triangle
					System.out.print("*");
				else
					System.out.print(" ");
				if (i >= (2 * n - 1) - j) // bottom right triangle
					System.out.print("*");
				else
					System.out.print(" ");
			}
			System.out.println();
		}
	}

	static void diamondNumericPattern() {
		int n = 5;

		for (int i = 1; i <= n; i++) {
			for (int j = 1; j < i; j++) {
				System.out.print(" ");
			}

			for (int k = i; k <= n; k++) {
				System.out.print(k + " ");
			}
			System.out.println();
		}
		for (int i = n - 1; i >= 1; i--) {
			for (int j = 1; j < i; j++) {
				System.out.print(" ");
			}
			for (int k = i; k <= n; k++) {
				System.out.print(k + " ");
			}

			System.out.println();
		}

	}

	public static void main(String[] args) {
		printHollowDiamondPattern(5);
		diamondInRectangle(5);
	}

}
