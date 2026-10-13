package algorithms.greedy;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import common.Check;

/*
 * Huffman coding: optimal prefix-free code for known symbol frequencies.
 * Greedy step: repeatedly merge the two LEAST frequent trees (min-heap). O(n log n).
 * Frequent symbols end up near the root -> short codes. No code is a prefix of another,
 * so a bit stream decodes unambiguously by walking the tree.
 *
 * Total encoded length = sum(freq * codeLength) = sum of all merge costs.
 * Ties can be broken differently, giving different (equally optimal) codes - so test the cost,
 * not the exact bit patterns. Used in ZIP/DEFLATE, JPEG, MP3.
 */
public class B_HuffmanCoding {

	static class Node {
		final char symbol;
		final int freq, order; // order: deterministic tie-breaking
		final Node left, right;

		Node(char symbol, int freq, int order, Node left, Node right) {
			this.symbol = symbol;
			this.freq = freq;
			this.order = order;
			this.left = left;
			this.right = right;
		}

		boolean isLeaf() {
			return left == null;
		}
	}

	static Node buildTree(Map<Character, Integer> freq) {
		PriorityQueue<Node> heap = new PriorityQueue<>((a, b) -> a.freq != b.freq ? Integer.compare(a.freq, b.freq) : Integer.compare(a.order, b.order));
		int order = 0;
		for (char c : new java.util.TreeSet<>(freq.keySet())) heap.add(new Node(c, freq.get(c), order++, null, null));
		while (heap.size() > 1) {
			Node a = heap.poll(), b = heap.poll();
			heap.add(new Node('\0', a.freq + b.freq, order++, a, b));
		}
		return heap.poll();
	}

	static Map<Character, String> codes(Node root) {
		Map<Character, String> codes = new HashMap<>();
		assign(root, "", codes);
		return codes;
	}

	private static void assign(Node n, String prefix, Map<Character, String> codes) {
		if (n.isLeaf()) {
			codes.put(n.symbol, prefix.isEmpty() ? "0" : prefix); // single-symbol input still needs one bit
			return;
		}
		assign(n.left, prefix + "0", codes);
		assign(n.right, prefix + "1", codes);
	}

	static String encode(String text, Map<Character, String> codes) {
		StringBuilder sb = new StringBuilder();
		for (char c : text.toCharArray()) sb.append(codes.get(c));
		return sb.toString();
	}

	static String decode(String bits, Node root) {
		if (root.isLeaf()) return String.valueOf(root.symbol).repeat(bits.length());
		StringBuilder out = new StringBuilder();
		Node n = root;
		for (char b : bits.toCharArray()) {
			n = b == '0' ? n.left : n.right;
			if (n.isLeaf()) {
				out.append(n.symbol);
				n = root;
			}
		}
		return out.toString();
	}

	public static void main(String[] args) {
		Map<Character, Integer> freq = new HashMap<>();
		int[] f = { 5, 9, 12, 13, 16, 45 };
		for (int i = 0; i < f.length; i++) freq.put((char) ('a' + i), f[i]);
		Map<Character, String> codes = codes(buildTree(freq));
		int cost = 0;
		for (char c : freq.keySet()) cost += freq.get(c) * codes.get(c).length();
		Check.eq("optimal total bits", cost, 224);
		Check.eq("most frequent gets 1 bit", codes.get('f').length(), 1);
		boolean prefixFree = true;
		for (String x : codes.values()) for (String y : codes.values()) if (x != y && y.startsWith(x)) prefixFree = false;
		Check.isTrue("prefix free", prefixFree);

		String text = "abracadabra";
		Map<Character, Integer> textFreq = new HashMap<>();
		for (char c : text.toCharArray()) textFreq.merge(c, 1, Integer::sum);
		Node root = buildTree(textFreq);
		String bits = encode(text, codes(root));
		Check.eq("round trip", decode(bits, root), text);
		Check.isTrue("shorter than 8 bits/char (" + bits.length() + ")", bits.length() < text.length() * 8);
	}
}
