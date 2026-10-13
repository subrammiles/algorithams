"""Sudoku solver: backtracking with bitmasks for rows/cols/boxes + MRV heuristic
(always fill the empty cell with the fewest candidates first).
"""


def solve(board):
    rows, cols, boxes = [0] * 9, [0] * 9, [0] * 9

    def box(r, c):
        return (r // 3) * 3 + c // 3

    def toggle(r, c, d):
        bit = 1 << d
        rows[r] ^= bit
        cols[c] ^= bit
        boxes[box(r, c)] ^= bit

    def candidates(r, c):
        return ~(rows[r] | cols[c] | boxes[box(r, c)]) & 0x1FF

    for r in range(9):
        for c in range(9):
            if board[r][c] != ".":
                toggle(r, c, int(board[r][c]) - 1)

    def backtrack():
        best = None
        for r in range(9):
            for c in range(9):
                if board[r][c] == ".":
                    count = bin(candidates(r, c)).count("1")
                    if best is None or count < best[0]:
                        best = (count, r, c)
        if best is None:
            return True
        _, r, c = best
        mask = candidates(r, c)
        while mask:
            d = (mask & -mask).bit_length() - 1
            mask &= mask - 1
            toggle(r, c, d)
            board[r][c] = str(d + 1)
            if backtrack():
                return True
            toggle(r, c, d)
            board[r][c] = "."
        return False

    if not backtrack():
        raise ValueError("unsolvable")


if __name__ == "__main__":
    puzzle = ["53..7....", "6..195...", ".98....6.", "8...6...3", "4..8.3..1",
              "7...2...6", ".6....28.", "...419..5", "....8..79"]
    solution = ["534678912", "672195348", "198342567", "859761423", "426853791",
                "713924856", "961537284", "287419635", "345286179"]
    board = [list(row) for row in puzzle]
    solve(board)
    assert ["".join(row) for row in board] == solution
    print("all checks passed")
