package fundamentals.patterns;

public class LeftAndRightArrows {

	public static void main(String[] args) {

		int i, j;
		int n = 7; // ‘n’ must be odd
		int num1 = n / 2 * 3;
		// right arrow
		System.out.println("Right Arrow");
		for (i = 0; i < n; i++) {
			for (j = 0; j < n; j++) {
				// center horizontal, upper right diagonal, bottom right diagonal
				if (i == n / 2 || j - i == n / 2 || i + j == num1)
					System.out.print("*");
				else
					System.out.print(" ");
			}
			System.out.println();
		}
		// left arrow
		System.out.println("Left Arrow");
		for (i = 0; i < n; i++) {
			for (j = 0; j < n; j++) {
				// center horizontal, bottom left diagonal, upper left diagonal
				if (i == n / 2 || i - j == n / 2 || i + j == n / 2)
					System.out.print("*");
				else
					System.out.print(" ");
			}
			System.out.println();
		}
	}

}
