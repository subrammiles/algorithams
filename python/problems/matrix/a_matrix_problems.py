"""Matrix problems: set zeroes with O(1) extra space (first row/col as markers),
staircase search in a row- and column-sorted matrix O(r + c), Game of Life in place (bit 1 = next state).
"""


def set_zeroes(m):
    rows, cols = len(m), len(m[0])
    first_row_zero = any(v == 0 for v in m[0])
    first_col_zero = any(m[r][0] == 0 for r in range(rows))
    for r in range(1, rows):
        for c in range(1, cols):
            if m[r][c] == 0:
                m[r][0] = m[0][c] = 0
    for r in range(1, rows):
        for c in range(1, cols):
            if m[r][0] == 0 or m[0][c] == 0:
                m[r][c] = 0
    if first_row_zero:
        m[0] = [0] * cols
    if first_col_zero:
        for r in range(rows):
            m[r][0] = 0


def search_matrix_ii(m, target):
    r, c = 0, len(m[0]) - 1
    while r < len(m) and c >= 0:
        if m[r][c] == target:
            return True
        if m[r][c] > target:
            c -= 1
        else:
            r += 1
    return False


def game_of_life(board):
    rows, cols = len(board), len(board[0])
    for r in range(rows):
        for c in range(cols):
            live = sum(board[nr][nc] & 1 for nr in range(r - 1, r + 2) for nc in range(c - 1, c + 2)
                       if (nr, nc) != (r, c) and 0 <= nr < rows and 0 <= nc < cols)
            if (board[r][c] & 1 and live in (2, 3)) or (not board[r][c] & 1 and live == 3):
                board[r][c] |= 2
    for row in board:
        for c in range(cols):
            row[c] >>= 1


if __name__ == "__main__":
    a = [[1, 1, 1], [1, 0, 1], [1, 1, 1]]
    set_zeroes(a)
    assert a == [[1, 0, 1], [0, 0, 0], [1, 0, 1]]
    b = [[0, 1, 2, 0], [3, 4, 5, 2], [1, 3, 1, 5]]
    set_zeroes(b)
    assert b == [[0, 0, 0, 0], [0, 4, 5, 0], [0, 3, 1, 0]]
    m = [[1, 4, 7, 11, 15], [2, 5, 8, 12, 19], [3, 6, 9, 16, 22], [10, 13, 14, 17, 24], [18, 21, 23, 26, 30]]
    assert search_matrix_ii(m, 5) and not search_matrix_ii(m, 20)
    life = [[0, 1, 0], [0, 0, 1], [1, 1, 1], [0, 0, 0]]
    game_of_life(life)
    assert life == [[0, 0, 0], [1, 0, 1], [0, 1, 1], [0, 1, 0]]
    print("all checks passed")
