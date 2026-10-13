package fundamentals.recursion;

import java.util.HashMap;
import java.util.Map;

import common.Check;

/*
 * Recursion basics. Every recursive function needs:
 *   1. a BASE CASE that stops the recursion, and
 *   2. a RECURSIVE CASE that moves towards the base case.
 * Each call gets its own stack frame, so recursion depth n costs O(n) stack space
 * (Java's default stack overflows somewhere around 10^4 - 10^5 frames).
 *
 *  - factorial(n)      n! = n * (n-1)!                         O(n)
 *  - fibonacci naive   two calls per level -> O(2^n)  (draw the call tree in an interview!)
 *  - fibonacci memo    cache results -> O(n): this IS top-down dynamic programming
 *  - fastPower(x, n)   x^n = (x^(n/2))^2 -> O(log n) instead of O(n)
 *  - sumOfDigits, reverse, isPalindrome: shrink the input by one step per call
 */
public class A_RecursionBasics {

	static long factorial(int n) {
		if (n <= 1) return 1; // base case
		return n * factorial(n - 1); // recursive case
	}

	static long fibNaive(int n) {
		if (n < 2) return n;
		return fibNaive(n - 1) + fibNaive(n - 2);
	}

	static long fibMemo(int n, Map<Integer, Long> memo) {
		if (n < 2) return n;
		Long cached = memo.get(n);
		if (cached != null) return cached;
		long value = fibMemo(n - 1, memo) + fibMemo(n - 2, memo);
		memo.put(n, value);
		return value;
	}

	/* Handles negative exponents; long avoids overflow of -Integer.MIN_VALUE. */
	static double fastPower(double x, long n) {
		if (n < 0) return 1 / fastPower(x, -n);
		if (n == 0) return 1;
		double half = fastPower(x, n / 2);
		return n % 2 == 0 ? half * half : half * half * x;
	}

	static int sumOfDigits(int n) {
		return n < 10 ? n : n % 10 + sumOfDigits(n / 10);
	}

	static String reverse(String s) {
		return s.isEmpty() ? s : reverse(s.substring(1)) + s.charAt(0);
	}

	static boolean isPalindrome(String s, int lo, int hi) {
		if (lo >= hi) return true;
		return s.charAt(lo) == s.charAt(hi) && isPalindrome(s, lo + 1, hi - 1);
	}

	public static void main(String[] args) {
		Check.eq("factorial(10)", factorial(10), 3628800L);
		Check.eq("fib naive(20)", fibNaive(20), 6765L);
		Check.eq("fib memo(50)", fibMemo(50, new HashMap<>()), 12586269025L);
		Check.near("2^10", fastPower(2, 10), 1024);
		Check.near("2^-2", fastPower(2, -2), 0.25);
		Check.eq("sum of digits", sumOfDigits(9875), 29);
		Check.eq("reverse", reverse("hello"), "olleh");
		Check.isTrue("palindrome", isPalindrome("racecar", 0, 6));
		Check.eq("not palindrome", isPalindrome("abca", 0, 3), false);
	}
}
