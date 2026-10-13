package fundamentals.recursion.types;

/* This program prints 1 to 20 numbers */

public class IndirectRecursion {
	static int currentNumber = 0;
	static void method2() {
		if (currentNumber <= 20) {
			System.out.print(currentNumber);
			currentNumber++;
			method1();
		} else {
			return;
		}	
	}
	static void method1() {
		if (currentNumber <= 20) {
			System.out.print(currentNumber);
			currentNumber++;
			method2();
		} else {
			return;
		}	
	}
	public static void main(String[] args) {
		method1();
	}
}
