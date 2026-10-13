package algorithms.backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import common.Check;

/*
 * N-Queens: place n queens so none attack each other.
 *
 * Place one queen per ROW; track used columns and both diagonals:
 *   main diagonal  r - c   (shift by n-1 to index an array)
 *   anti diagonal  r + c
 * Then each placement check is O(1).
 *
 * Counting version with bitmasks: the free columns in the next row are
 *   ~(cols | leftDiagonals | rightDiagonals) & fullMask, and diagonals shift by one each row.
 * Very fast; n = 8 -> 92 solutions, n = 12 -> 14200.
 * (The older algorithms/backtracking/NQueenProblem.java prints one board.)
 */
public class A_NQueens {

	static List<List<String>> solveNQueens(int n) {
		List<List<String>> out = new ArrayList<>();
		place(0, n, new int[n], new boolean[n], new boolean[2 * n - 1], new boolean[2 * n - 1], out);
		return out;
	}

	private static void place(int row, int n, int[] queenCol, boolean[] cols, boolean[] diag, boolean[] anti, List<List<String>> out) {
		if (row == n) {
			List<String> board = new ArrayList<>();
			for (int c : queenCol) {
				char[] line = new char[n];
				Arrays.fill(line, '.');
				line[c] = 'Q';
				board.add(new String(line));
			}
			out.add(board);
			return;
		}
		for (int c = 0; c < n; c++) {
			int d = row - c + n - 1, a = row + c;
			if (cols[c] || diag[d] || anti[a]) continue;
			queenCol[row] = c;
			cols[c] = diag[d] = anti[a] = true;
			place(row + 1, n, queenCol, cols, diag, anti, out);
			cols[c] = diag[d] = anti[a] = false;
		}
	}

	static int totalNQueens(int n) {
		return count(n, 0, 0, 0, (1 << n) - 1);
	}

	private static int count(int n, int cols, int left, int right, int full) {
		if (cols == full) return 1;
		int total = 0;
		for (int free = ~(cols | left | right) & full; free != 0; free &= free - 1) {
			int bit = free & -free;
			total += count(n, cols | bit, (left | bit) << 1 & full, (right | bit) >> 1, full);
		}
		return total;
	}

	public static void main(String[] args) {
		Check.eq("4 queens boards", solveNQueens(4),
				List.of(List.of(".Q..", "...Q", "Q...", "..Q."), List.of("..Q.", "Q...", "...Q", ".Q..")));
		Check.eq("8 queens count", totalNQueens(8), 92);
		Check.eq("8 queens boards", solveNQueens(8).size(), 92);
		Check.eq("12 queens count", totalNQueens(12), 14200);
		Check.eq("3 queens none", totalNQueens(3), 0);
	}
}
