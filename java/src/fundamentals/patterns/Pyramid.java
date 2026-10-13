package fundamentals.patterns;

import java.util.Scanner;

public class Pyramid {

	static void invertedFullPyrimide() {
		int i, j, k = 0;
		int n = 5;

		for (i = n; i >= 1; --i) {
			for (j = 0; j < n - i; ++j)
				System.out.print(" ");

			for (j = i; j <= 2 * i - 1; ++j)
				System.out.print("*");

			for (j = 0; j < i - 1; ++j)
				System.out.print("*");

			System.out.print("\n");
		}

	}

	static void fullPyrimide() {
		int i, j, k = 0;
		int n = 6; // Number od rows
		for (i = 1; i <= n; ++i) {
			k = 0;
			for (j = 1; j <= n - i; ++j) {
				System.out.print(" ");
			}

			while (k != 2 * i - 1) {
				System.out.print("*");
				++k;
			}

			System.out.print("\n");
		}

	}

	static void hollowFullPyramid() {
		int n = 4;

		int i, j, k = 0;
		for (i = 1; i <= n; i++) {
			for (j = i; j < n; j++) {
				System.out.print(" ");
			}
			while (k != (2 * i)) {
				if (k == 0 || k == 2 * i - 2)
					System.out.print("*");
				else
					System.out.print(" ");
				k++;
				;
			}
			k = 0;
			System.out.print("\n"); // print next row
		}
		for (i = 0; i < 2 * n - 1; i++) {
			System.out.print("*");
		}

	}

	static void invertedHallowHalfPyramid() {
		int n = 5;
		int i, j, k = 0;
		for (i = 0; i < n; i++) {
			System.out.print("* ");
		}
		for (i = n; i >= 1; i--) {
			for (j = 0; j < n - i; ++j)
				while (k != (2 * i - 1)) {
					if (k == 0 || k == 2 * i - 2)
						System.out.print("*");
					else
						System.out.print(" ");
					k++;

				}
			k = 0;
			System.out.print("\n"); // print next row
		}
	}

	static void invertedHollowFullPyramid() {
		int n = 5;
		int i, j;
		for (i = 1; i <= n; i++) {
			for (j = 1; j < i; j++) {
				System.out.print(" ");
			}

			for (j = 1; j <= (n * 2 - (2 * i - 1)); j++) {

				if (i == 1 || j == 1 || j == (n * 2 - (2 * i - 1))) {
					System.out.print("*");
				} else {
					System.out.print(" ");
				}
			}
			System.out.print("\n");
		}

	}

	public static void main(String[] args) {
		invertedHollowFullPyramid();

	}
}
