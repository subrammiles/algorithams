package algorithms.graphs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.Check;

/*
 * Everyday graph traversal problems (BFS/DFS basics are in data_structures_tutorials/graph).
 *
 *  - Clone graph: DFS/BFS with a map original -> copy; the map doubles as the visited set.
 *  - Graph valid tree: exactly n-1 edges AND connected (no cycle follows). O(V + E).
 *  - Number of connected components: DFS from every unvisited node (or union-find).
 *  - All paths from source to target in a DAG: DFS with a path list; no visited set needed
 *    because a DAG has no cycles. Output can be exponential.
 */
public class A_GraphTraversalProblems {

	static class Node {
		int val;
		List<Node> neighbors = new ArrayList<>();

		Node(int val) {
			this.val = val;
		}
	}

	static Node cloneGraph(Node node) {
		return node == null ? null : clone(node, new HashMap<>());
	}

	private static Node clone(Node node, Map<Node, Node> copies) {
		Node existing = copies.get(node);
		if (existing != null) return existing;
		Node copy = new Node(node.val);
		copies.put(node, copy); // register BEFORE recursing, or cycles loop forever
		for (Node nb : node.neighbors) copy.neighbors.add(clone(nb, copies));
		return copy;
	}

	private static List<List<Integer>> adjacency(int n, int[][] edges) {
		List<List<Integer>> g = new ArrayList<>();
		for (int i = 0; i < n; i++) g.add(new ArrayList<>());
		for (int[] e : edges) {
			g.get(e[0]).add(e[1]);
			g.get(e[1]).add(e[0]);
		}
		return g;
	}

	static boolean validTree(int n, int[][] edges) {
		if (edges.length != n - 1) return false;
		return countComponents(n, edges) == 1;
	}

	static int countComponents(int n, int[][] edges) {
		List<List<Integer>> g = adjacency(n, edges);
		boolean[] seen = new boolean[n];
		int components = 0;
		for (int s = 0; s < n; s++) {
			if (seen[s]) continue;
			components++;
			Deque<Integer> stack = new ArrayDeque<>();
			stack.push(s);
			seen[s] = true;
			while (!stack.isEmpty()) {
				for (int v : g.get(stack.pop())) {
					if (!seen[v]) {
						seen[v] = true;
						stack.push(v);
					}
				}
			}
		}
		return components;
	}

	static List<List<Integer>> allPathsSourceTarget(int[][] graph) {
		List<List<Integer>> paths = new ArrayList<>();
		List<Integer> path = new ArrayList<>();
		path.add(0);
		allPaths(graph, 0, path, paths);
		return paths;
	}

	private static void allPaths(int[][] graph, int u, List<Integer> path, List<List<Integer>> paths) {
		if (u == graph.length - 1) {
			paths.add(new ArrayList<>(path));
			return;
		}
		for (int v : graph[u]) {
			path.add(v);
			allPaths(graph, v, path, paths);
			path.remove(path.size() - 1);
		}
	}

	public static void main(String[] args) {
		Node[] nodes = { new Node(1), new Node(2), new Node(3), new Node(4) };
		for (int i = 0; i < 4; i++) { // square 1-2-3-4-1
			nodes[i].neighbors.add(nodes[(i + 1) % 4]);
			nodes[i].neighbors.add(nodes[(i + 3) % 4]);
		}
		Node copy = cloneGraph(nodes[0]);
		Check.isTrue("clone is a new node", copy != nodes[0] && copy.val == 1);
		Check.eq("clone neighbours", List.of(copy.neighbors.get(0).val, copy.neighbors.get(1).val), List.of(2, 4));
		Check.isTrue("clone keeps cycle", copy.neighbors.get(0).neighbors.get(0).neighbors.get(0).neighbors.get(0) == copy);
		Check.isTrue("valid tree", validTree(5, new int[][] { { 0, 1 }, { 0, 2 }, { 0, 3 }, { 1, 4 } }));
		Check.eq("has cycle", validTree(5, new int[][] { { 0, 1 }, { 1, 2 }, { 2, 3 }, { 1, 3 }, { 1, 4 } }), false);
		Check.eq("components", countComponents(5, new int[][] { { 0, 1 }, { 1, 2 }, { 3, 4 } }), 2);
		Check.eq("all paths", allPathsSourceTarget(new int[][] { { 1, 2 }, { 3 }, { 3 }, {} }), List.of(List.of(0, 1, 3), List.of(0, 2, 3)));
	}
}
