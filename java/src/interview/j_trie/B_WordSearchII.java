package interview.j_trie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import common.Check;

/*
 * Problem: Find all dictionary words that can be traced on a board (adjacent cells, no reuse).
 *
 * Naive: run Word Search for every word -> O(words * cells * 4^L).
 * Better: put all words in a trie and run ONE backtracking DFS from every cell, walking the
 * trie in lock-step. A branch dies as soon as the prefix is not in the trie.
 *
 * Optimisations interviewers like:
 *  - Store the full word at the terminal node (no StringBuilder needed).
 *  - Null out the word once found (no duplicates).
 *  - Prune leaf nodes after use so later searches skip finished branches.
 */
public class B_WordSearchII {

	static class Node {
		Node[] next = new Node[26];
		String word;
	}

	static List<String> findWords(char[][] board, String[] words) {
		Node root = new Node();
		for (String w : words) {
			Node n = root;
			for (char c : w.toCharArray()) {
				if (n.next[c - 'a'] == null) n.next[c - 'a'] = new Node();
				n = n.next[c - 'a'];
			}
			n.word = w;
		}
		List<String> found = new ArrayList<>();
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[0].length; c++) dfs(board, r, c, root, found);
		}
		Collections.sort(found);
		return found;
	}

	private static void dfs(char[][] board, int r, int c, Node parent, List<String> found) {
		if (r < 0 || c < 0 || r >= board.length || c >= board[0].length) return;
		char ch = board[r][c];
		if (ch == '#' || parent.next[ch - 'a'] == null) return;
		Node node = parent.next[ch - 'a'];
		if (node.word != null) {
			found.add(node.word);
			node.word = null;
		}
		board[r][c] = '#'; // mark visited
		dfs(board, r + 1, c, node, found);
		dfs(board, r - 1, c, node, found);
		dfs(board, r, c + 1, node, found);
		dfs(board, r, c - 1, node, found);
		board[r][c] = ch;
		if (isLeaf(node) && node.word == null) parent.next[ch - 'a'] = null; // prune
	}

	private static boolean isLeaf(Node n) {
		for (Node child : n.next) if (child != null) return false;
		return true;
	}

	public static void main(String[] args) {
		char[][] board = { "oaan".toCharArray(), "etae".toCharArray(), "ihkr".toCharArray(), "iflv".toCharArray() };
		Check.eq("word search II", findWords(board, new String[] { "oath", "pea", "eat", "rain" }), List.of("eat", "oath"));
	}
}
