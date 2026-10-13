package problems.matrix;

import common.Check;

/*
 * Matrix problems (rotate and spiral are in interview/s_math).
 *
 *  - Set matrix zeroes in O(1) extra space: use the first row/column as markers; remember
 *    separately whether the first row/column themselves had a zero.
 *  - Search a matrix sorted by rows AND columns: start top-right; go left if too big, down if too
 *    small. O(rows + cols) - "staircase search".
 *  - Game of Life in place: encode the next state in a second bit (bit 1 = next, bit 0 = current),
 *    then shift right at the end.
 */
public class A_MatrixProblems {

	static void setZeroes(int[][] m) {
		int rows = m.length, cols = m[0].length;
		boolean firstRowZero = false, firstColZero = false;
		for (int c = 0; c < cols; c++) if (m[0][c] == 0) firstRowZero = true;
		for (int r = 0; r < rows; r++) if (m[r][0] == 0) firstColZero = true;
		for (int r = 1; r < rows; r++) {
			for (int c = 1; c < cols; c++) {
				if (m[r][c] == 0) {
					m[r][0] = 0;
					m[0][c] = 0;
				}
			}
		}
		for (int r = 1; r < rows; r++) for (int c = 1; c < cols; c++) if (m[r][0] == 0 || m[0][c] == 0) m[r][c] = 0;
		if (firstRowZero) for (int c = 0; c < cols; c++) m[0][c] = 0;
		if (firstColZero) for (int r = 0; r < rows; r++) m[r][0] = 0;
	}

	static boolean searchMatrixII(int[][] m, int target) {
		int r = 0, c = m[0].length - 1;
		while (r < m.length && c >= 0) {
			if (m[r][c] == target) return true;
			if (m[r][c] > target) c--;
			else r++;
		}
		return false;
	}

	static void gameOfLife(int[][] board) {
		int rows = board.length, cols = board[0].length;
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				int live = 0;
				for (int dr = -1; dr <= 1; dr++) {
					for (int dc = -1; dc <= 1; dc++) {
						int nr = r + dr, nc = c + dc;
						if ((dr != 0 || dc != 0) && nr >= 0 && nc >= 0 && nr < rows && nc < cols) live += board[nr][nc] & 1;
					}
				}
				boolean alive = (board[r][c] & 1) == 1;
				if ((alive && (live == 2 || live == 3)) || (!alive && live == 3)) board[r][c] |= 2; // next state in bit 1
			}
		}
		for (int[] row : board) for (int c = 0; c < cols; c++) row[c] >>= 1;
	}

	public static void main(String[] args) {
		int[][] a = { { 1, 1, 1 }, { 1, 0, 1 }, { 1, 1, 1 } };
		setZeroes(a);
		Check.eq("set zeroes", a, new int[][] { { 1, 0, 1 }, { 0, 0, 0 }, { 1, 0, 1 } });
		int[][] b = { { 0, 1, 2, 0 }, { 3, 4, 5, 2 }, { 1, 3, 1, 5 } };
		setZeroes(b);
		Check.eq("set zeroes first row", b, new int[][] { { 0, 0, 0, 0 }, { 0, 4, 5, 0 }, { 0, 3, 1, 0 } });
		int[][] m = { { 1, 4, 7, 11, 15 }, { 2, 5, 8, 12, 19 }, { 3, 6, 9, 16, 22 }, { 10, 13, 14, 17, 24 }, { 18, 21, 23, 26, 30 } };
		Check.isTrue("staircase 5", searchMatrixII(m, 5));
		Check.eq("staircase 20", searchMatrixII(m, 20), false);
		int[][] life = { { 0, 1, 0 }, { 0, 0, 1 }, { 1, 1, 1 }, { 0, 0, 0 } };
		gameOfLife(life);
		Check.eq("game of life", life, new int[][] { { 0, 0, 0 }, { 1, 0, 1 }, { 0, 1, 1 }, { 0, 1, 0 } });
	}
}
