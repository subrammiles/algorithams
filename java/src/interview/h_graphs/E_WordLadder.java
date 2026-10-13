package interview.h_graphs;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import common.Check;

/*
 * Problem: Shortest transformation sequence from beginWord to endWord, changing one letter
 * at a time, every intermediate word in the dictionary. Return the number of words, or 0.
 *
 * Approach: BFS on an implicit graph. Generate neighbours by trying all 26 letters at each
 * position (O(L * 26) per word) instead of comparing all word pairs (O(N^2 * L)).
 *
 * Senior optimisation: bidirectional BFS - always expand the smaller frontier. It cuts the
 * explored nodes from b^d to roughly 2 * b^(d/2).
 */
public class E_WordLadder {

	static int ladderLength(String begin, String end, List<String> wordList) {
		Set<String> dict = new HashSet<>(wordList);
		if (!dict.contains(end)) return 0;
		Set<String> front = new HashSet<>(), back = new HashSet<>(), visited = new HashSet<>();
		front.add(begin);
		back.add(end);
		int steps = 1;
		while (!front.isEmpty() && !back.isEmpty()) {
			if (front.size() > back.size()) { // expand the smaller side
				Set<String> t = front;
				front = back;
				back = t;
			}
			Set<String> next = new HashSet<>();
			for (String word : front) {
				char[] chars = word.toCharArray();
				for (int i = 0; i < chars.length; i++) {
					char original = chars[i];
					for (char c = 'a'; c <= 'z'; c++) {
						if (c == original) continue;
						chars[i] = c;
						String candidate = new String(chars);
						if (back.contains(candidate)) return steps + 1;
						if (dict.contains(candidate) && visited.add(candidate)) next.add(candidate);
					}
					chars[i] = original;
				}
			}
			front = next;
			steps++;
		}
		return 0;
	}

	/* Plain one-directional BFS kept for comparison. */
	static int ladderLengthBfs(String begin, String end, List<String> wordList) {
		Set<String> dict = new HashSet<>(wordList);
		Deque<String> queue = new ArrayDeque<>();
		queue.offer(begin);
		for (int steps = 1; !queue.isEmpty(); steps++) {
			for (int size = queue.size(); size > 0; size--) {
				String word = queue.poll();
				if (word.equals(end)) return steps;
				char[] chars = word.toCharArray();
				for (int i = 0; i < chars.length; i++) {
					char original = chars[i];
					for (char c = 'a'; c <= 'z'; c++) {
						chars[i] = c;
						String candidate = new String(chars);
						if (dict.remove(candidate)) queue.offer(candidate);
					}
					chars[i] = original;
				}
			}
		}
		return 0;
	}

	public static void main(String[] args) {
		List<String> words = Arrays.asList("hot", "dot", "dog", "lot", "log", "cog");
		Check.eq("bidirectional", ladderLength("hit", "cog", words), 5);
		Check.eq("bfs", ladderLengthBfs("hit", "cog", words), 5);
		Check.eq("no end word", ladderLength("hit", "cog", Arrays.asList("hot", "dot", "dog", "lot", "log")), 0);
	}
}
