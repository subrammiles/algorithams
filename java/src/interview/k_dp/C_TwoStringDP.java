package interview.k_dp;

import interview.Check;

/*
 * Two-string DP: dp[i][j] describes prefixes s[0..i) and t[0..j).
 *
 * 1. Longest Common Subsequence:
 *    match -> dp[i-1][j-1] + 1, else max(dp[i-1][j], dp[i][j-1]). O(mn); space O(min(m,n)) with rolling rows.
 * 2. Regular Expression Matching ('.' any char, '*' zero or more of the previous char):
 *    if p[j-1] == '*': dp[i][j] = dp[i][j-2] (use zero times)
 *                                 || (first char matches && dp[i-1][j]) (use once more)
 *    else: dp[i][j] = first char matches && dp[i-1][j-1]
 * (Edit distance lives in a/strings/s4/EditDistanceProblem.java.)
 */
public class C_TwoStringDP {

	static int lcs(String s, String t) {
		int[] prev = new int[t.length() + 1], cur = new int[t.length() + 1];
		for (int i = 1; i <= s.length(); i++) {
			for (int j = 1; j <= t.length(); j++) {
				cur[j] = s.charAt(i - 1) == t.charAt(j - 1) ? prev[j - 1] + 1 : Math.max(prev[j], cur[j - 1]);
			}
			int[] tmp = prev;
			prev = cur;
			cur = tmp;
		}
		return prev[t.length()];
	}

	static boolean isMatch(String s, String p) {
		int m = s.length(), n = p.length();
		boolean[][] dp = new boolean[m + 1][n + 1];
		dp[0][0] = true;
		for (int i = 0; i <= m; i++) {
			for (int j = 1; j <= n; j++) {
				if (p.charAt(j - 1) == '*') {
					dp[i][j] = dp[i][j - 2] || (i > 0 && matches(s.charAt(i - 1), p.charAt(j - 2)) && dp[i - 1][j]);
				} else {
					dp[i][j] = i > 0 && matches(s.charAt(i - 1), p.charAt(j - 1)) && dp[i - 1][j - 1];
				}
			}
		}
		return dp[m][n];
	}

	private static boolean matches(char sc, char pc) {
		return pc == '.' || pc == sc;
	}

	public static void main(String[] args) {
		Check.eq("lcs", lcs("abcde", "ace"), 3);
		Check.eq("lcs none", lcs("abc", "def"), 0);
		Check.eq("regex a*", isMatch("aa", "a*"), true);
		Check.eq("regex .*", isMatch("ab", ".*"), true);
		Check.eq("regex c*a*b", isMatch("aab", "c*a*b"), true);
		Check.eq("regex fail", isMatch("mississippi", "mis*is*p*."), false);
	}
}
