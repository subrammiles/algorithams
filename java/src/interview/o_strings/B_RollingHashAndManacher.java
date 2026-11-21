package interview.o_strings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.Check;

/*
 * Two more linear-time string tools to sit next to KMP / Z (A_KMPAndZAlgorithm).
 *
 * 1. Rabin-Karp (rolling hash).
 *    hash(s[i..i+m)) = s[i]*B^(m-1) + ... + s[i+m-1]   (mod M)
 *    Slide by one: hash = (hash - s[i]*B^(m-1)) * B + s[i+m]   - O(1) per step.
 *    Equal hashes can still be a collision, so VERIFY with a real comparison before reporting.
 *    Expected O(n + m). Its edge over KMP: compares many patterns / substrings at once.
 *    Traps: negative values after subtraction (add M before %), overflow (use long, M ~ 1e9+7).
 *    Stronger: double hashing (two moduli) or a random base, so adversarial inputs can't collide.
 *
 * 2. Longest duplicate substring = binary search on the length + rolling hash.
 *    If a length-L duplicate exists, a length-(L-1) one does too (monotone) -> binary search L,
 *    and check each L in O(n) with a hash -> start-index map. O(n log n) expected.
 *    (Suffix array + LCP solves it in O(n log n) deterministically.)
 *
 * 3. Manacher - longest palindromic substring in O(n).
 *    Insert '#' between characters so every palindrome has odd length ("abba" -> "#a#b#b#a#").
 *    p[i] = palindrome radius around i in the new string (= its length in the original string).
 *    Keep the rightmost palindrome [c - p[c], c + p[c]]. For i inside it, the mirror j = 2c - i
 *    gives a free lower bound p[i] >= min(p[j], right - i); then expand. The right edge only
 *    moves forward, so the total work is O(n).
 *    Expand-around-centre (O(n^2), dynamic_programming/D_StringDP) is usually fine to code first;
 *    name Manacher as the follow-up.
 */
public class B_RollingHashAndManacher {

	static final long MOD = 1_000_000_007L, BASE = 131;

	static List<Integer> rabinKarp(String text, String pattern) {
		List<Integer> out = new ArrayList<>();
		int n = text.length(), m = pattern.length();
		if (m == 0 || m > n) return out;
		long power = 1; // BASE^(m-1)
		for (int i = 1; i < m; i++) power = power * BASE % MOD;
		long target = 0, window = 0;
		for (int i = 0; i < m; i++) {
			target = (target * BASE + pattern.charAt(i)) % MOD;
			window = (window * BASE + text.charAt(i)) % MOD;
		}
		for (int i = 0; ; i++) {
			if (window == target && text.regionMatches(i, pattern, 0, m)) out.add(i); // verify: collisions
			if (i + m == n) break;
			window = (window - text.charAt(i) * power % MOD + MOD) % MOD;
			window = (window * BASE + text.charAt(i + m)) % MOD;
		}
		return out;
	}

	static String longestDupSubstring(String s) {
		int lo = 1, hi = s.length() - 1, bestStart = 0, bestLen = 0;
		while (lo <= hi) {
			int len = (lo + hi) >>> 1;
			int start = duplicateOfLength(s, len);
			if (start >= 0) {
				bestStart = start;
				bestLen = len;
				lo = len + 1;
			} else {
				hi = len - 1;
			}
		}
		return s.substring(bestStart, bestStart + bestLen);
	}

	// start of some substring of length len that occurs twice, or -1
	private static int duplicateOfLength(String s, int len) {
		long power = 1;
		for (int i = 1; i < len; i++) power = power * BASE % MOD;
		Map<Long, List<Integer>> seen = new HashMap<>();
		long h = 0;
		for (int i = 0; i < s.length(); i++) {
			if (i >= len) h = (h - s.charAt(i - len) * power % MOD + MOD) % MOD;
			h = (h * BASE + s.charAt(i)) % MOD;
			if (i < len - 1) continue;
			int start = i - len + 1;
			List<Integer> starts = seen.computeIfAbsent(h, k -> new ArrayList<>());
			for (int other : starts) {
				if (s.regionMatches(other, s, start, len)) return start;
			}
			starts.add(start);
		}
		return -1;
	}

	static String longestPalindrome(String s) {
		if (s.isEmpty()) return s;
		StringBuilder sb = new StringBuilder("#");
		for (char c : s.toCharArray()) sb.append(c).append('#');
		String t = sb.toString();
		int n = t.length();
		int[] p = new int[n];
		int center = 0, right = 0, best = 0;
		for (int i = 0; i < n; i++) {
			if (i < right) p[i] = Math.min(right - i, p[2 * center - i]);
			while (i - p[i] - 1 >= 0 && i + p[i] + 1 < n && t.charAt(i - p[i] - 1) == t.charAt(i + p[i] + 1)) p[i]++;
			if (i + p[i] > right) {
				center = i;
				right = i + p[i];
			}
			if (p[i] > p[best]) best = i;
		}
		int start = (best - p[best]) / 2;
		return s.substring(start, start + p[best]);
	}

	public static void main(String[] args) {
		Check.eq("rabin-karp abra", rabinKarp("abracadabra", "abra"), List.of(0, 7));
		Check.eq("rabin-karp overlapping", rabinKarp("aaaa", "aa"), List.of(0, 1, 2));
		Check.eq("rabin-karp missing", rabinKarp("abc", "abcd"), List.of());

		Check.eq("longest dup banana", longestDupSubstring("banana"), "ana");
		Check.eq("longest dup none", longestDupSubstring("abcd"), "");
		Check.eq("longest dup aaaaa", longestDupSubstring("aaaaa"), "aaaa");

		Check.eq("manacher babad length", longestPalindrome("babad").length(), 3);
		Check.eq("manacher cbbd", longestPalindrome("cbbd"), "bb");
		Check.eq("manacher geeks", longestPalindrome("forgeeksskeegfor"), "geeksskeeg");
		Check.eq("manacher single", longestPalindrome("a"), "a");
	}
}
