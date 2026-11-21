package algorithms.backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import common.Check;

/*
 * The four "textbook" backtracking problems (older single-problem versions: RatMaze, MColoringProblem,
 * HamiltonianCycle, KnightTour in this folder). Same skeleton every time:
 *
 *     solve(state):
 *         if complete(state): record / return true
 *         for choice in candidates(state):
 *             if valid(choice): apply(choice); if solve(state) return true; undo(choice)
 *
 * 1. Rat in a maze - all paths from (0,0) to (n-1,n-1) through open cells, moves D L R U.
 *    Mark the cell visited before recursing, unmark after (undo). Trying D, L, R, U in that order
 *    yields the paths already in lexicographic order. Worst case O(4^(n^2)), fine for small grids.
 *
 * 2. M-colouring - colour graph vertices with at most m colours so no edge joins equal colours.
 *    Colour vertex v with the first colour that no coloured neighbour uses; backtrack on dead ends.
 *    O(m^V). m = 2 is just a bipartite check (BFS, O(V + E)); m = 3 is NP-complete in general.
 *
 * 3. Hamiltonian cycle - visit every vertex exactly once and return to the start.
 *    Fix vertex 0 as the start (a cycle can start anywhere), extend the path with unvisited
 *    neighbours. O(V!) - also NP-complete. Bitmask DP dp[mask][v] gives O(2^V * V^2) (see TSP).
 *
 * 4. Knight's tour - visit every square of an n x n board once with knight moves.
 *    Plain backtracking is exponential; Warnsdorff's rule (try the square with the FEWEST onward
 *    moves first) almost never backtracks. Tours exist for n = 1 and n >= 5.
 *
 * Interview angle: name the pruning ("check validity before recursing, not after"), the undo step,
 * and the complexity class. Most of these are asked to test the skeleton, not to optimise.
 */
public class D_ClassicBacktracking {

	// ---------- 1. rat in a maze ----------
	private static final int[] DR = { 1, 0, 0, -1 }, DC = { 0, -1, 1, 0 };
	private static final char[] DIR = { 'D', 'L', 'R', 'U' }; // alphabetical -> sorted output

	static List<String> ratInMaze(int[][] maze) {
		List<String> out = new ArrayList<>();
		int n = maze.length;
		if (n == 0 || maze[0][0] == 0 || maze[n - 1][n - 1] == 0) return out;
		boolean[][] seen = new boolean[n][n];
		seen[0][0] = true;
		walk(maze, 0, 0, new StringBuilder(), seen, out);
		return out;
	}

	private static void walk(int[][] maze, int r, int c, StringBuilder path, boolean[][] seen, List<String> out) {
		int n = maze.length;
		if (r == n - 1 && c == n - 1) {
			out.add(path.toString());
			return;
		}
		for (int d = 0; d < 4; d++) {
			int nr = r + DR[d], nc = c + DC[d];
			if (nr < 0 || nc < 0 || nr >= n || nc >= n || maze[nr][nc] == 0 || seen[nr][nc]) continue;
			seen[nr][nc] = true;
			path.append(DIR[d]);
			walk(maze, nr, nc, path, seen, out);
			path.deleteCharAt(path.length() - 1); // undo
			seen[nr][nc] = false;
		}
	}

	// ---------- 2. m-colouring ----------
	/** Colours 1..m per vertex, or an empty array if m colours are not enough. */
	static int[] mColoring(int n, int[][] edges, int m) {
		List<List<Integer>> adj = adjacency(n, edges);
		int[] color = new int[n];
		return colorFrom(0, adj, m, color) ? color : new int[0];
	}

	private static boolean colorFrom(int v, List<List<Integer>> adj, int m, int[] color) {
		if (v == color.length) return true;
		for (int c = 1; c <= m; c++) {
			boolean ok = true;
			for (int u : adj.get(v)) ok &= color[u] != c;
			if (!ok) continue;
			color[v] = c;
			if (colorFrom(v + 1, adj, m, color)) return true;
			color[v] = 0; // undo
		}
		return false;
	}

	// ---------- 3. Hamiltonian cycle ----------
	/** A cycle starting and ending at 0, or an empty list if none exists. */
	static List<Integer> hamiltonianCycle(int n, int[][] edges) {
		boolean[][] adj = new boolean[n][n];
		for (int[] e : edges) adj[e[0]][e[1]] = adj[e[1]][e[0]] = true;
		List<Integer> path = new ArrayList<>(List.of(0));
		boolean[] used = new boolean[n];
		used[0] = true;
		if (!extend(adj, path, used)) return List.of();
		path.add(0);
		return path;
	}

	private static boolean extend(boolean[][] adj, List<Integer> path, boolean[] used) {
		int n = adj.length, last = path.get(path.size() - 1);
		if (path.size() == n) return adj[last][0]; // can we close the cycle?
		for (int v = 1; v < n; v++) {
			if (used[v] || !adj[last][v]) continue;
			used[v] = true;
			path.add(v);
			if (extend(adj, path, used)) return true;
			path.remove(path.size() - 1); // undo
			used[v] = false;
		}
		return false;
	}

	// ---------- 4. knight's tour ----------
	private static final int[] KR = { 2, 1, -1, -2, -2, -1, 1, 2 }, KC = { 1, 2, 2, 1, -1, -2, -2, -1 };

	/** board[r][c] = move number 0..n*n-1 starting at (0,0), or an empty array if there is no tour. */
	static int[][] knightsTour(int n) {
		int[][] board = new int[n][n];
		for (int[] row : board) Arrays.fill(row, -1);
		board[0][0] = 0;
		return tour(board, 0, 0, 1) ? board : new int[0][];
	}

	private static boolean tour(int[][] board, int r, int c, int step) {
		int n = board.length;
		if (step == n * n) return true;
		// Warnsdorff: try next squares in order of how few onward moves they have
		List<int[]> next = new ArrayList<>();
		for (int k = 0; k < 8; k++) {
			int nr = r + KR[k], nc = c + KC[k];
			if (free(board, nr, nc)) next.add(new int[] { nr, nc, onwardMoves(board, nr, nc) });
		}
		next.sort((a, b) -> Integer.compare(a[2], b[2]));
		for (int[] sq : next) {
			board[sq[0]][sq[1]] = step;
			if (tour(board, sq[0], sq[1], step + 1)) return true;
			board[sq[0]][sq[1]] = -1; // undo
		}
		return false;
	}

	private static boolean free(int[][] b, int r, int c) {
		return r >= 0 && c >= 0 && r < b.length && c < b.length && b[r][c] == -1;
	}

	private static int onwardMoves(int[][] b, int r, int c) {
		int count = 0;
		for (int k = 0; k < 8; k++) if (free(b, r + KR[k], c + KC[k])) count++;
		return count;
	}

	static boolean isValidTour(int[][] board) {
		int n = board.length;
		int[][] where = new int[n * n][];
		for (int r = 0; r < n; r++) for (int c = 0; c < n; c++) {
			int s = board[r][c];
			if (s < 0 || s >= n * n || where[s] != null) return false;
			where[s] = new int[] { r, c };
		}
		for (int s = 1; s < n * n; s++) {
			int dr = Math.abs(where[s][0] - where[s - 1][0]), dc = Math.abs(where[s][1] - where[s - 1][1]);
			if (dr * dc != 2) return false; // knight move = (1,2) or (2,1)
		}
		return true;
	}

	private static List<List<Integer>> adjacency(int n, int[][] edges) {
		List<List<Integer>> adj = new ArrayList<>();
		for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
		for (int[] e : edges) {
			adj.get(e[0]).add(e[1]);
			adj.get(e[1]).add(e[0]);
		}
		return adj;
	}

	public static void main(String[] args) {
		int[][] maze = { { 1, 0, 0, 0 }, { 1, 1, 0, 1 }, { 1, 1, 0, 0 }, { 0, 1, 1, 1 } };
		Check.eq("rat in maze", ratInMaze(maze), List.of("DDRDRR", "DRDDRR"));
		Check.eq("rat blocked", ratInMaze(new int[][] { { 1, 0 }, { 0, 1 } }), List.of());

		int[][] graph = { { 0, 1 }, { 1, 2 }, { 2, 3 }, { 3, 0 }, { 0, 2 } };
		Check.eq("3-colouring", mColoring(4, graph, 3), new int[] { 1, 2, 3, 2 });
		Check.eq("2 colours not enough (triangle 0-1-2)", mColoring(4, graph, 2), new int[0]);

		int[][] g1 = { { 0, 1 }, { 0, 3 }, { 1, 2 }, { 1, 3 }, { 1, 4 }, { 2, 4 }, { 3, 4 } };
		Check.eq("hamiltonian cycle", hamiltonianCycle(5, g1), List.of(0, 1, 2, 4, 3, 0));
		int[][] g2 = { { 0, 1 }, { 0, 3 }, { 1, 2 }, { 1, 3 }, { 1, 4 }, { 2, 4 } };
		Check.eq("no hamiltonian cycle", hamiltonianCycle(5, g2), List.of());

		Check.isTrue("knight's tour 5x5", isValidTour(knightsTour(5)));
		Check.isTrue("knight's tour 8x8", isValidTour(knightsTour(8)));
		Check.eq("no tour on 3x3", knightsTour(3).length, 0);
		Check.eq("no tour on 4x4", knightsTour(4).length, 0);
	}
}
