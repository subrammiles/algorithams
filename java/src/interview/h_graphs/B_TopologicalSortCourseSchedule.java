package interview.h_graphs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import interview.Check;

/*
 * Problem (Course Schedule II): prerequisites [a, b] mean b must come before a.
 * Return a valid order, or an empty array if there is a cycle.
 *
 * Approach 1 - Kahn's algorithm (BFS): repeatedly take nodes with in-degree 0.
 *   If fewer than n nodes are output, the graph has a cycle.
 * Approach 2 - DFS with 3 colours (white/grey/black); a grey->grey edge is a cycle;
 *   reverse postorder is a topological order.
 * Both O(V + E).
 *
 * Where it shows up: build systems, task schedulers, spreadsheet recalculation,
 * package managers, "parallel courses" (BFS levels = minimum semesters).
 */
public class B_TopologicalSortCourseSchedule {

	static int[] findOrderKahn(int n, int[][] prerequisites) {
		List<List<Integer>> graph = new ArrayList<>();
		for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
		int[] indegree = new int[n];
		for (int[] p : prerequisites) {
			graph.get(p[1]).add(p[0]);
			indegree[p[0]]++;
		}
		Deque<Integer> queue = new ArrayDeque<>();
		for (int i = 0; i < n; i++) if (indegree[i] == 0) queue.offer(i);
		int[] order = new int[n];
		int idx = 0;
		while (!queue.isEmpty()) {
			int u = queue.poll();
			order[idx++] = u;
			for (int v : graph.get(u)) if (--indegree[v] == 0) queue.offer(v);
		}
		return idx == n ? order : new int[0];
	}

	static boolean hasCycleDfs(int n, int[][] prerequisites) {
		List<List<Integer>> graph = new ArrayList<>();
		for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
		for (int[] p : prerequisites) graph.get(p[1]).add(p[0]);
		int[] color = new int[n]; // 0 white, 1 grey (on stack), 2 black (done)
		for (int i = 0; i < n; i++) if (color[i] == 0 && dfs(i, graph, color)) return true;
		return false;
	}

	private static boolean dfs(int u, List<List<Integer>> graph, int[] color) {
		color[u] = 1;
		for (int v : graph.get(u)) {
			if (color[v] == 1) return true;
			if (color[v] == 0 && dfs(v, graph, color)) return true;
		}
		color[u] = 2;
		return false;
	}

	public static void main(String[] args) {
		int[][] pre = { { 1, 0 }, { 2, 0 }, { 3, 1 }, { 3, 2 } };
		Check.eq("kahn order", findOrderKahn(4, pre), new int[] { 0, 1, 2, 3 });
		Check.eq("kahn cycle", findOrderKahn(2, new int[][] { { 0, 1 }, { 1, 0 } }), new int[0]);
		Check.eq("dfs no cycle", hasCycleDfs(4, pre), false);
		Check.isTrue("dfs cycle", hasCycleDfs(3, new int[][] { { 1, 0 }, { 2, 1 }, { 0, 2 } }));
	}
}
