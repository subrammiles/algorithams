package algorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

import common.Check;

/*
 * Backtracking on grids and strings.
 *
 *  - Word search: DFS from every cell, mark the cell visited in place ('#'), restore on return.
 *    O(cells * 4 * 3^(L-1)). Pruning tip: check the board has enough of each letter first.
 *  - Palindrome partitioning: choose the next palindromic prefix, recurse on the rest.
 *    (Precomputing isPal[i][j] removes the repeated palindrome checks.)
 *  - Restore IP addresses: 4 parts, each 1-3 digits, value <= 255, no leading zeros.
 *    The search space is tiny (at most 3^4 choices) - prune on remaining length.
 */
public class B_GridAndStringBacktracking {

	static boolean exist(char[][] board, String word) {
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[0].length; c++) if (search(board, word, 0, r, c)) return true;
		}
		return false;
	}

	private static boolean search(char[][] b, String w, int i, int r, int c) {
		if (i == w.length()) return true;
		if (r < 0 || c < 0 || r >= b.length || c >= b[0].length || b[r][c] != w.charAt(i)) return false;
		char saved = b[r][c];
		b[r][c] = '#';
		boolean found = search(b, w, i + 1, r + 1, c) || search(b, w, i + 1, r - 1, c) || search(b, w, i + 1, r, c + 1) || search(b, w, i + 1, r, c - 1);
		b[r][c] = saved;
		return found;
	}

	static List<List<String>> partition(String s) {
		List<List<String>> out = new ArrayList<>();
		partition(s, 0, new ArrayList<>(), out);
		return out;
	}

	private static void partition(String s, int start, List<String> cur, List<List<String>> out) {
		if (start == s.length()) {
			out.add(new ArrayList<>(cur));
			return;
		}
		for (int end = start + 1; end <= s.length(); end++) {
			if (isPalindrome(s, start, end - 1)) {
				cur.add(s.substring(start, end));
				partition(s, end, cur, out);
				cur.remove(cur.size() - 1);
			}
		}
	}

	private static boolean isPalindrome(String s, int lo, int hi) {
		while (lo < hi) if (s.charAt(lo++) != s.charAt(hi--)) return false;
		return true;
	}

	static List<String> restoreIpAddresses(String s) {
		List<String> out = new ArrayList<>();
		ip(s, 0, new ArrayList<>(), out);
		return out;
	}

	private static void ip(String s, int start, List<String> parts, List<String> out) {
		int remaining = s.length() - start, partsLeft = 4 - parts.size();
		if (remaining < partsLeft || remaining > 3 * partsLeft) return; // prune
		if (partsLeft == 0) {
			out.add(String.join(".", parts));
			return;
		}
		for (int len = 1; len <= 3 && start + len <= s.length(); len++) {
			String part = s.substring(start, start + len);
			if ((part.length() > 1 && part.charAt(0) == '0') || Integer.parseInt(part) > 255) continue;
			parts.add(part);
			ip(s, start + len, parts, out);
			parts.remove(parts.size() - 1);
		}
	}

	public static void main(String[] args) {
		char[][] board = { "ABCE".toCharArray(), "SFCS".toCharArray(), "ADEE".toCharArray() };
		Check.isTrue("ABCCED", exist(board, "ABCCED"));
		Check.isTrue("SEE", exist(board, "SEE"));
		Check.eq("ABCB reuses a cell", exist(board, "ABCB"), false);
		Check.eq("palindrome partitions", partition("aab"), List.of(List.of("a", "a", "b"), List.of("aa", "b")));
		Check.eq("ip", restoreIpAddresses("25525511135"), List.of("255.255.11.135", "255.255.111.35"));
		Check.eq("ip zeros", restoreIpAddresses("0000"), List.of("0.0.0.0"));
		Check.eq("ip many", restoreIpAddresses("101023"), List.of("1.0.10.23", "1.0.102.3", "10.1.0.23", "10.10.2.3", "101.0.2.3"));
	}
}
