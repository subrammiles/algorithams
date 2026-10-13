package fundamentals.patterns;

public class SymbolAtRateInSqare {

	static void pattern1() {
		
		int i, j;
		for (i = 0; i < 5; i++) {
			for (j = 0; j < 5; j++) {
				System.out.print(" @ ");
			}
			System.out.print("\n");
		}
	}

	static void pattern2() {
		
		int i, j;
		for (i = 0; i < 5; i++) {
			for (j = 0; j <= i; j++) {
				System.out.print(" @ ");
			}
			System.out.print("\n");
		}

	}

	static void pattern3() {

		int i, j, k;
		for (i = 1; i <= 5; i++) {
			for (j = 5; j >= i; j--) {
				System.out.print(" ");
			}
			for (k = 1; k <= i; k++) {
				System.out.print("@");
			}
			System.out.print("\n");
		}

	}

	static void pattern4() {
		
		int i, j, k, samp = 1;
		for (i = 5; i >= 1; i--) {
			for (k = samp; k >= 0; k--) {
				System.out.print(" ");
				// only 1 space
			}
			for (j = i; j >= 1; j--) {
				System.out.print("@");
			}
			samp = samp + 1;
			System.out.print("\n");
		}

	}

	public static void main(String[] args) {
		pattern4();

	}

}




