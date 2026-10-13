package algorithms.dynamic_programming;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import common.Check;

/*
 * String DP. Two strings -> dp[i][j] over prefixes. One string -> dp[i][j] over substring s[i..j].
 *
 *  - Edit distance: equal chars -> dp[i-1][j-1]; else 1 + min(insert, delete, replace).
 *  - Longest palindromic subsequence: s[i]==s[j] -> 2 + dp[i+1][j-1], else max(drop i, drop j).
 *    (Equivalently LCS(s, reverse(s)).)
 *  - Longest palindromic substring: expand around each of the 2n-1 centres. O(n^2), O(1) space.
 *    (Manacher's algorithm does O(n) - see problems/strings/level3.)
 *  - Word break: ok[i] = some word w ends at i with ok[i - |w|]. O(n * maxWordLen) with a set.
 *  - Distinct subsequences: ways to form t from s; s[i]==t[j] -> use it or skip it.
 *  - Interleaving string: dp[i][j] = can s1[:i] and s2[:j] form s3[:i+j].
 */
public class D_StringDP {

	static int editDistance(String a, String b) {
		int[][] dp = new int[a.length() + 1][b.length() + 1];
		for (int i = 0; i <= a.length(); i++) dp[i][0] = i;
		for (int j = 0; j <= b.length(); j++) dp[0][j] = j;
		for (int i = 1; i <= a.length(); i++) {
			for (int j = 1; j <= b.length(); j++) {
				if (a.charAt(i - 1) == b.charAt(j - 1)) dp[i][j] = dp[i - 1][j - 1];
				else dp[i][j] = 1 + Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1]));
			}
		}
		return dp[a.length()][b.length()];
	}

	static int longestPalindromicSubsequence(String s) {
		int n = s.length();
		int[][] dp = new int[n][n];
		for (int i = n - 1; i >= 0; i--) {
			dp[i][i] = 1;
			for (int j = i + 1; j < n; j++) {
				dp[i][j] = s.charAt(i) == s.charAt(j) ? 2 + dp[i + 1][j - 1] : Math.max(dp[i + 1][j], dp[i][j - 1]);
			}
		}
		return dp[0][n - 1];
	}

	static String longestPalindromicSubstring(String s) {
		int bestStart = 0, bestLen = 0;
		for (int centre = 0; centre < 2 * s.length() - 1; centre++) {
			int lo = centre / 2, hi = lo + centre % 2; // odd centres then even centres
			while (lo >= 0 && hi < s.length() && s.charAt(lo) == s.charAt(hi)) {
				lo--;
				hi++;
			}
			if (hi - lo - 1 > bestLen) {
				bestLen = hi - lo - 1;
				bestStart = lo + 1;
			}
		}
		return s.substring(bestStart, bestStart + bestLen);
	}

	static boolean wordBreak(String s, List<String> words) {
		Set<String> dict = new HashSet<>(words);
		int maxLen = 0;
		for (String w : words) maxLen = Math.max(maxLen, w.length());
		boolean[] ok = new boolean[s.length() + 1];
		ok[0] = true;
		for (int i = 1; i <= s.length(); i++) {
			for (int len = 1; len <= Math.min(i, maxLen) && !ok[i]; len++) {
				ok[i] = ok[i - len] && dict.contains(s.substring(i - len, i));
			}
		}
		return ok[s.length()];
	}

	static long numDistinct(String s, String t) {
		long[] dp = new long[t.length() + 1];
		dp[0] = 1; // empty t can always be formed
		for (int i = 1; i <= s.length(); i++) {
			for (int j = t.length(); j >= 1; j--) { // backwards: use the previous row's dp[j-1]
				if (s.charAt(i - 1) == t.charAt(j - 1)) dp[j] += dp[j - 1];
			}
		}
		return dp[t.length()];
	}

	static boolean isInterleave(String a, String b, String c) {
		if (a.length() + b.length() != c.length()) return false;
		boolean[] dp = new boolean[b.length() + 1];
		for (int i = 0; i <= a.length(); i++) {
			for (int j = 0; j <= b.length(); j++) {
				if (i == 0 && j == 0) dp[j] = true;
				else {
					boolean fromA = i > 0 && dp[j] && a.charAt(i - 1) == c.charAt(i + j - 1);
					boolean fromB = j > 0 && dp[j - 1] && b.charAt(j - 1) == c.charAt(i + j - 1);
					dp[j] = fromA || fromB;
				}
			}
		}
		return dp[b.length()];
	}

	public static void main(String[] args) {
		Check.eq("edit distance", editDistance("horse", "ros"), 3);
		Check.eq("edit distance 2", editDistance("intention", "execution"), 5);
		Check.eq("palindromic subsequence", longestPalindromicSubsequence("bbbab"), 4);
		Check.eq("palindromic substring", longestPalindromicSubstring("babad"), "bab");
		Check.eq("palindromic substring even", longestPalindromicSubstring("cbbd"), "bb");
		Check.isTrue("word break", wordBreak("leetcode", Arrays.asList("leet", "code")));
		Check.eq("word break no", wordBreak("catsandog", Arrays.asList("cats", "dog", "sand", "and", "cat")), false);
		Check.eq("distinct subsequences", numDistinct("rabbbit", "rabbit"), 3L);
		Check.isTrue("interleave", isInterleave("aabcc", "dbbca", "aadbbcbcac"));
		Check.eq("interleave no", isInterleave("aabcc", "dbbca", "aadbbbaccc"), false);
	}
}
