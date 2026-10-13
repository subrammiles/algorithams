"""Backtracking on grids and strings: word search (mark cell, restore), palindrome partitioning,
restore IP addresses (prune on remaining length).
"""


def exist(board, word):
    rows, cols = len(board), len(board[0])

    def search(i, r, c):
        if i == len(word):
            return True
        if not (0 <= r < rows and 0 <= c < cols) or board[r][c] != word[i]:
            return False
        saved, board[r][c] = board[r][c], "#"
        found = any(search(i + 1, r + dr, c + dc) for dr, dc in ((1, 0), (-1, 0), (0, 1), (0, -1)))
        board[r][c] = saved
        return found

    return any(search(0, r, c) for r in range(rows) for c in range(cols))


def partition(s):
    out = []

    def go(start, cur):
        if start == len(s):
            out.append(cur[:])
            return
        for end in range(start + 1, len(s) + 1):
            piece = s[start:end]
            if piece == piece[::-1]:
                cur.append(piece)
                go(end, cur)
                cur.pop()

    go(0, [])
    return out


def restore_ip_addresses(s):
    out = []

    def go(start, parts):
        remaining, parts_left = len(s) - start, 4 - len(parts)
        if not parts_left <= remaining <= 3 * parts_left:
            return
        if parts_left == 0:
            out.append(".".join(parts))
            return
        for length in range(1, 4):
            part = s[start:start + length]
            if len(part) < length or (len(part) > 1 and part[0] == "0") or int(part) > 255:
                continue
            go(start + length, parts + [part])

    go(0, [])
    return out


if __name__ == "__main__":
    board = [list("ABCE"), list("SFCS"), list("ADEE")]
    assert exist(board, "ABCCED") and exist(board, "SEE") and not exist(board, "ABCB")
    assert partition("aab") == [["a", "a", "b"], ["aa", "b"]]
    assert restore_ip_addresses("25525511135") == ["255.255.11.135", "255.255.111.35"]
    assert restore_ip_addresses("0000") == ["0.0.0.0"]
    assert restore_ip_addresses("101023") == ["1.0.10.23", "1.0.102.3", "10.1.0.23", "10.10.2.3", "101.0.2.3"]
    print("all checks passed")
