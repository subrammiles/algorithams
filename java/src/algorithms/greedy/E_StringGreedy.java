package algorithms.greedy;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.PriorityQueue;

import common.Check;

/*
 * String greedy problems.
 *
 *  - Remove K digits for the smallest number: a monotonic increasing stack - a bigger digit
 *    followed by a smaller one should be removed (it is the most significant improvement). O(n).
 *  - Reorganize string (no two equal neighbours): always place the most frequent remaining char
 *    that differs from the previous one (max-heap). Impossible if maxFreq > (n + 1) / 2.
 *  - Largest number from integers: sort by the comparator (a+b vs b+a) - this ordering is
 *    transitive, which is what makes the greedy sort valid.
 */
public class E_StringGreedy {

	static String removeKdigits(String num, int k) {
		Deque<Character> stack = new ArrayDeque<>();
		for (char c : num.toCharArray()) {
			while (k > 0 && !stack.isEmpty() && stack.peekLast() > c) {
				stack.pollLast();
				k--;
			}
			stack.addLast(c);
		}
		while (k-- > 0 && !stack.isEmpty()) stack.pollLast();
		StringBuilder sb = new StringBuilder();
		for (char c : stack) if (!(sb.length() == 0 && c == '0')) sb.append(c); // strip leading zeros
		return sb.length() == 0 ? "0" : sb.toString();
	}

	static String reorganizeString(String s) {
		int[] count = new int[26];
		for (char c : s.toCharArray()) count[c - 'a']++;
		PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> b[1] != a[1] ? b[1] - a[1] : a[0] - b[0]);
		for (int i = 0; i < 26; i++) if (count[i] > 0) heap.add(new int[] { i, count[i] });
		StringBuilder sb = new StringBuilder();
		int[] held = null; // the char just used, kept out of the heap for one step
		while (!heap.isEmpty()) {
			int[] top = heap.poll();
			sb.append((char) ('a' + top[0]));
			top[1]--;
			if (held != null && held[1] > 0) heap.add(held);
			held = top;
		}
		return sb.length() == s.length() ? sb.toString() : "";
	}

	static String largestNumber(int[] nums) {
		String[] parts = Arrays.stream(nums).mapToObj(String::valueOf).toArray(String[]::new);
		Arrays.sort(parts, (a, b) -> (b + a).compareTo(a + b));
		return parts[0].equals("0") ? "0" : String.join("", parts);
	}

	private static boolean noEqualNeighbours(String s) {
		for (int i = 1; i < s.length(); i++) if (s.charAt(i) == s.charAt(i - 1)) return false;
		return true;
	}

	private static String sorted(String s) {
		char[] c = s.toCharArray();
		Arrays.sort(c);
		return new String(c);
	}

	public static void main(String[] args) {
		Check.eq("remove k", removeKdigits("1432219", 3), "1219");
		Check.eq("remove k leading zeros", removeKdigits("10200", 1), "200");
		Check.eq("remove all", removeKdigits("10", 2), "0");
		String r = reorganizeString("aaabbc");
		Check.isTrue("reorganize valid (" + r + ")", noEqualNeighbours(r) && sorted(r).equals("aaabbc"));
		Check.eq("reorganize impossible", reorganizeString("aaab"), "");
		Check.eq("largest number", largestNumber(new int[] { 3, 30, 34, 5, 9 }), "9534330");
		Check.eq("largest number zeros", largestNumber(new int[] { 0, 0 }), "0");
	}
}
