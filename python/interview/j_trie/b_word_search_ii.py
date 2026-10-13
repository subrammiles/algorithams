"""Word Search II: all dictionary words traceable on a board.

Put words in a trie; one backtracking DFS from each cell walks the trie in lock-step.
Store the word at its terminal node, clear it when found, prune empty branches.
"""


def find_words(board, words):
    root = {}
    for w in words:
        n = root
        for ch in w:
            n = n.setdefault(ch, {})
        n["$"] = w
    rows, cols, found = len(board), len(board[0]), []

    def dfs(r, c, parent):
        ch = board[r][c]
        node = parent[ch]
        if "$" in node:
            found.append(node.pop("$"))
        board[r][c] = "#"
        for dr, dc in ((1, 0), (-1, 0), (0, 1), (0, -1)):
            nr, nc = r + dr, c + dc
            if 0 <= nr < rows and 0 <= nc < cols and board[nr][nc] in node:
                dfs(nr, nc, node)
        board[r][c] = ch
        if not node:
            parent.pop(ch)  # prune finished branch

    for r in range(rows):
        for c in range(cols):
            if board[r][c] in root:
                dfs(r, c, root)
    return sorted(found)


if __name__ == "__main__":
    board = [list("oaan"), list("etae"), list("ihkr"), list("iflv")]
    assert find_words(board, ["oath", "pea", "eat", "rain"]) == ["eat", "oath"]
    print("all checks passed")
