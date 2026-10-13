"""N-Queens: one queen per row; track columns and both diagonals (r - c, r + c) for O(1) checks.
Counting with bitmasks: free = ~(cols | left | right) & full. n=8 -> 92, n=12 -> 14200.
"""


def solve_n_queens(n):
    out, cols, diag, anti, queen_col = [], set(), set(), set(), [0] * n

    def place(r):
        if r == n:
            out.append(["." * c + "Q" + "." * (n - c - 1) for c in queen_col])
            return
        for c in range(n):
            if c in cols or r - c in diag or r + c in anti:
                continue
            queen_col[r] = c
            cols.add(c), diag.add(r - c), anti.add(r + c)
            place(r + 1)
            cols.remove(c), diag.remove(r - c), anti.remove(r + c)

    place(0)
    return out


def total_n_queens(n):
    full = (1 << n) - 1

    def count(cols, left, right):
        if cols == full:
            return 1
        total, free = 0, ~(cols | left | right) & full
        while free:
            bit = free & -free
            free &= free - 1
            total += count(cols | bit, (left | bit) << 1 & full, (right | bit) >> 1)
        return total

    return count(0, 0, 0)


if __name__ == "__main__":
    assert solve_n_queens(4) == [[".Q..", "...Q", "Q...", "..Q."], ["..Q.", "Q...", "...Q", ".Q.."]]
    assert total_n_queens(8) == 92 == len(solve_n_queens(8))
    assert total_n_queens(12) == 14200 and total_n_queens(3) == 0
    print("all checks passed")
