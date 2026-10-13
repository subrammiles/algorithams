package interview.j_trie;

import java.util.ArrayList;
import java.util.List;

import common.Check;

/*
 * Trie (prefix tree). insert/search/startsWith are O(L) for a word of length L.
 *
 * Extensions shown here:
 *  - autocomplete(prefix, limit): DFS from the prefix node in lexicographic order.
 *  - searchWithDot: '.' matches any letter (Design Add and Search Words).
 *
 * Senior discussion (typeahead system design):
 *  - Store top-k suggestions at each node to answer in O(L) instead of a DFS.
 *  - Memory: array[26] children vs HashMap children vs compressed (radix) trie.
 *  - Shard by prefix, cache hot prefixes, rebuild offline from query logs.
 */
public class A_TrieAndAutocomplete {

	static class Node {
		Node[] next = new Node[26];
		boolean isWord;
	}

	private final Node root = new Node();

	void insert(String word) {
		Node n = root;
		for (char c : word.toCharArray()) {
			if (n.next[c - 'a'] == null) n.next[c - 'a'] = new Node();
			n = n.next[c - 'a'];
		}
		n.isWord = true;
	}

	boolean search(String word) {
		Node n = walk(word);
		return n != null && n.isWord;
	}

	boolean startsWith(String prefix) {
		return walk(prefix) != null;
	}

	private Node walk(String s) {
		Node n = root;
		for (char c : s.toCharArray()) {
			n = n.next[c - 'a'];
			if (n == null) return null;
		}
		return n;
	}

	List<String> autocomplete(String prefix, int limit) {
		List<String> out = new ArrayList<>();
		Node start = walk(prefix);
		if (start != null) collect(start, new StringBuilder(prefix), out, limit);
		return out;
	}

	private void collect(Node n, StringBuilder sb, List<String> out, int limit) {
		if (out.size() == limit) return;
		if (n.isWord) out.add(sb.toString());
		for (int i = 0; i < 26; i++) {
			if (n.next[i] != null) {
				sb.append((char) ('a' + i));
				collect(n.next[i], sb, out, limit);
				sb.deleteCharAt(sb.length() - 1);
			}
		}
	}

	boolean searchWithDot(String pattern) {
		return dot(root, pattern, 0);
	}

	private boolean dot(Node n, String p, int i) {
		if (n == null) return false;
		if (i == p.length()) return n.isWord;
		char c = p.charAt(i);
		if (c != '.') return dot(n.next[c - 'a'], p, i + 1);
		for (Node child : n.next) if (dot(child, p, i + 1)) return true;
		return false;
	}

	public static void main(String[] args) {
		A_TrieAndAutocomplete t = new A_TrieAndAutocomplete();
		for (String w : new String[] { "apple", "app", "apply", "ape", "bat" }) t.insert(w);
		Check.isTrue("search app", t.search("app"));
		Check.eq("search ap", t.search("ap"), false);
		Check.isTrue("startsWith ap", t.startsWith("ap"));
		Check.eq("autocomplete", t.autocomplete("app", 10), List.of("app", "apple", "apply"));
		Check.eq("autocomplete limit", t.autocomplete("a", 2), List.of("ape", "app"));
		Check.isTrue("dot", t.searchWithDot("b.t"));
		Check.eq("dot miss", t.searchWithDot("a..x"), false);
	}
}
