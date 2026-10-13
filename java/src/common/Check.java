package common;

import java.util.Arrays;
import java.util.Objects;

/*
 * Tiny assertion helper used by every interview example.
 * Running any main() prints PASS lines, or throws AssertionError on a wrong answer,
 * so each file is a self-checking exercise. Delete the body of a solution and re-run to practise.
 */
public final class Check {

	private Check() {
	}

	public static void eq(String label, Object actual, Object expected) {
		if (!Objects.deepEquals(actual, expected)) {
			throw new AssertionError("FAIL " + label + " -> expected " + str(expected) + " but got " + str(actual));
		}
		System.out.println("PASS " + label + " = " + str(actual));
	}

	public static void near(String label, double actual, double expected) {
		if (Math.abs(actual - expected) > 1e-6) {
			throw new AssertionError("FAIL " + label + " -> expected " + expected + " but got " + actual);
		}
		System.out.println("PASS " + label + " = " + actual);
	}

	public static void isTrue(String label, boolean condition) {
		eq(label, condition, true);
	}

	private static String str(Object o) {
		String s = Arrays.deepToString(new Object[] { o });
		return s.substring(1, s.length() - 1);
	}
}
