package interview.l_backtracking;

import interview.Check;

/*
 * Problem: Solve a 9x9 Sudoku ('.' = empty).
 *
 * Approach: Backtracking with O(1) validity checks using bitmasks for rows, columns and boxes.
 * Heuristic (MRV - minimum remaining values): always fill the empty cell with the FEWEST
 * candidates first. This prunes the search tree drastically and is what makes it fast.
 *
 * Worst case is exponential; mention that real solvers use constraint propagation /
 * Dancing Links (Algorithm X) for exact cover.
 */
public class B_SudokuSolver {

	private final int[] rows = new int[9], cols = new int[9], boxes = new int[9];

	void solve(char[][] board) {
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				if (board[r][c] != '.') place(r, c, board[r][c] - '1', true);
			}
		}
		if (!backtrack(board)) throw new IllegalArgumentException("Unsolvable sudoku");
	}

	private boolean backtrack(char[][] board) {
		int bestR = -1, bestC = -1, bestCount = 10;
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				if (board[r][c] != '.') continue;
				int count = Integer.bitCount(candidates(r, c));
				if (count < bestCount) {
					bestCount = count;
					bestR = r;
					bestC = c;
				}
			}
		}
		if (bestR == -1) return true; // no empty cell left
		int mask = candidates(bestR, bestC);
		while (mask != 0) {
			int d = Integer.numberOfTrailingZeros(mask);
			mask &= mask - 1; // drop lowest set bit
			place(bestR, bestC, d, true);
			board[bestR][bestC] = (char) ('1' + d);
			if (backtrack(board)) return true;
			place(bestR, bestC, d, false);
			board[bestR][bestC] = '.';
		}
		return false;
	}

	private int candidates(int r, int c) {
		return ~(rows[r] | cols[c] | boxes[(r / 3) * 3 + c / 3]) & 0x1FF;
	}

	private void place(int r, int c, int d, boolean on) {
		int bit = 1 << d;
		if (on) {
			rows[r] |= bit;
			cols[c] |= bit;
			boxes[(r / 3) * 3 + c / 3] |= bit;
		} else {
			rows[r] &= ~bit;
			cols[c] &= ~bit;
			boxes[(r / 3) * 3 + c / 3] &= ~bit;
		}
	}

	public static void main(String[] args) {
		String[] puzzle = { "53..7....", "6..195...", ".98....6.", "8...6...3", "4..8.3..1", "7...2...6", ".6....28.",
				"...419..5", "....8..79" };
		String[] solution = { "534678912", "672195348", "198342567", "859761423", "426853791", "713924856",
				"961537284", "287419635", "345286179" };
		char[][] board = new char[9][];
		for (int i = 0; i < 9; i++) board[i] = puzzle[i].toCharArray();
		new B_SudokuSolver().solve(board);
		for (int i = 0; i < 9; i++) Check.eq("row " + i, new String(board[i]), solution[i]);
	}
}
