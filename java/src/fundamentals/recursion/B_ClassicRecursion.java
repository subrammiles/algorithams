package fundamentals.recursion;

import java.util.ArrayList;
import java.util.List;

import common.Check;

/*
 * Classic recursion problems interviewers use to see if you can "trust the recursion".
 *
 *  - Tower of Hanoi: move n-1 disks out of the way, move the biggest, move n-1 back on top.
 *    T(n) = 2T(n-1) + 1  =>  2^n - 1 moves.
 *  - Josephus: n people in a circle, every k-th is eliminated.
 *    J(1) = 0, J(n) = (J(n-1) + k) % n  (0-based). O(n).
 *  - Binary strings of length n without two consecutive 1s (count = Fibonacci(n+2)).
 *  - Letter combinations of a phone number: one recursion level per digit. O(4^n * n).
 *  - Generate valid parentheses: add '(' while open < n, add ')' while close < open.
 *    Output size is the Catalan number C(n).
 */
public class B_ClassicRecursion {

	static void hanoi(int n, char from, char to, char via, List<String> moves) {
		if (n == 0) return;
		hanoi(n - 1, from, via, to, moves);
		moves.add(from + "->" + to);
		hanoi(n - 1, via, to, from, moves);
	}

	/* Returns the 1-based position of the survivor. */
	static int josephus(int n, int k) {
		return josephusZeroBased(n, k) + 1;
	}

	private static int josephusZeroBased(int n, int k) {
		return n == 1 ? 0 : (josephusZeroBased(n - 1, k) + k) % n;
	}

	static List<String> binaryStringsNoConsecutiveOnes(int n) {
		List<String> out = new ArrayList<>();
		buildBinary(n, "", out);
		return out;
	}

	private static void buildBinary(int n, String cur, List<String> out) {
		if (cur.length() == n) {
			out.add(cur);
			return;
		}
		buildBinary(n, cur + "0", out);
		if (cur.isEmpty() || cur.charAt(cur.length() - 1) == '0') buildBinary(n, cur + "1", out);
	}

	private static final String[] KEYPAD = { "", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz" };

	static List<String> letterCombinations(String digits) {
		List<String> out = new ArrayList<>();
		if (!digits.isEmpty()) letters(digits, 0, new StringBuilder(), out);
		return out;
	}

	private static void letters(String digits, int i, StringBuilder cur, List<String> out) {
		if (i == digits.length()) {
			out.add(cur.toString());
			return;
		}
		for (char c : KEYPAD[digits.charAt(i) - '0'].toCharArray()) {
			cur.append(c);
			letters(digits, i + 1, cur, out);
			cur.deleteCharAt(cur.length() - 1);
		}
	}

	static List<String> generateParentheses(int n) {
		List<String> out = new ArrayList<>();
		parens(n, 0, 0, new StringBuilder(), out);
		return out;
	}

	private static void parens(int n, int open, int close, StringBuilder cur, List<String> out) {
		if (cur.length() == 2 * n) {
			out.add(cur.toString());
			return;
		}
		if (open < n) {
			cur.append('(');
			parens(n, open + 1, close, cur, out);
			cur.deleteCharAt(cur.length() - 1);
		}
		if (close < open) {
			cur.append(')');
			parens(n, open, close + 1, cur, out);
			cur.deleteCharAt(cur.length() - 1);
		}
	}

	public static void main(String[] args) {
		List<String> moves = new ArrayList<>();
		hanoi(2, 'A', 'C', 'B', moves);
		Check.eq("hanoi 2", moves, List.of("A->B", "A->C", "B->C"));
		moves.clear();
		hanoi(10, 'A', 'C', 'B', moves);
		Check.eq("hanoi 10 = 2^10 - 1 moves", moves.size(), 1023);
		Check.eq("josephus(7,3)", josephus(7, 3), 4);
		Check.eq("josephus(5,2)", josephus(5, 2), 3);
		Check.eq("binary strings", binaryStringsNoConsecutiveOnes(3), List.of("000", "001", "010", "100", "101"));
		Check.eq("phone letters", letterCombinations("23"), List.of("ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"));
		Check.eq("phone empty", letterCombinations(""), List.of());
		Check.eq("parentheses 3", generateParentheses(3), List.of("((()))", "(()())", "(())()", "()(())", "()()()"));
	}
}
