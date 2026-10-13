package interview.o_strings;

import java.util.ArrayList;
import java.util.List;

import common.Check;

/*
 * Linear-time pattern matching (naive search is O(n*m); Rabin-Karp is in problems/strings/classic).
 *
 * KMP: lps[i] = length of the longest proper prefix of p[0..i] that is also a suffix.
 *   On a mismatch, jump the pattern pointer to lps[j-1] instead of restarting. O(n + m).
 *   Bonus uses: shortest palindrome (lps of s + '#' + reverse(s)), repeated substring pattern.
 *
 * Z-algorithm: z[i] = length of the longest substring starting at i that matches a prefix.
 *   Search: compute z on p + '$' + text; every z[i] == |p| is a match. O(n + m).
 */
public class A_KMPAndZAlgorithm {

	static int[] lps(String p) {
		int[] lps = new int[p.length()];
		for (int i = 1, len = 0; i < p.length();) {
			if (p.charAt(i) == p.charAt(len)) {
				lps[i++] = ++len;
			} else if (len > 0) {
				len = lps[len - 1];
			} else {
				lps[i++] = 0;
			}
		}
		return lps;
	}

	static List<Integer> kmpSearch(String text, String p) {
		int[] lps = lps(p);
		List<Integer> matches = new ArrayList<>();
		for (int i = 0, j = 0; i < text.length();) {
			if (text.charAt(i) == p.charAt(j)) {
				i++;
				j++;
				if (j == p.length()) {
					matches.add(i - j);
					j = lps[j - 1];
				}
			} else if (j > 0) {
				j = lps[j - 1];
			} else {
				i++;
			}
		}
		return matches;
	}

	static int[] zArray(String s) {
		int n = s.length();
		int[] z = new int[n];
		for (int i = 1, l = 0, r = 0; i < n; i++) {
			if (i < r) z[i] = Math.min(r - i, z[i - l]);
			while (i + z[i] < n && s.charAt(z[i]) == s.charAt(i + z[i])) z[i]++;
			if (i + z[i] > r) {
				l = i;
				r = i + z[i];
			}
		}
		return z;
	}

	static List<Integer> zSearch(String text, String p) {
		int[] z = zArray(p + "$" + text);
		List<Integer> matches = new ArrayList<>();
		for (int i = p.length() + 1; i < z.length; i++) if (z[i] == p.length()) matches.add(i - p.length() - 1);
		return matches;
	}

	static boolean repeatedSubstringPattern(String s) {
		int longest = lps(s)[s.length() - 1];
		return longest > 0 && s.length() % (s.length() - longest) == 0;
	}

	public static void main(String[] args) {
		Check.eq("lps", lps("aabaaab"), new int[] { 0, 1, 0, 1, 2, 2, 3 });
		Check.eq("kmp", kmpSearch("ababcabcabababd", "ababd"), List.of(10));
		Check.eq("kmp overlap", kmpSearch("aaaa", "aa"), List.of(0, 1, 2));
		Check.eq("z search", zSearch("ababcabcabababd", "ababd"), List.of(10));
		Check.isTrue("repeated abab", repeatedSubstringPattern("abcabcabc"));
		Check.eq("repeated aba", repeatedSubstringPattern("aba"), false);
	}
}
