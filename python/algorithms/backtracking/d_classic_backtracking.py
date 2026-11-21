"""Classic backtracking: rat in a maze, m-colouring, Hamiltonian cycle, knight's tour.
Full explanations in D_ClassicBacktracking.java.

Skeleton: if complete -> record; for each choice: if valid -> apply, recurse, UNDO.
rat_in_maze        all paths (moves D L R U, alphabetical -> sorted output), mark/unmark visited.
m_coloring         first colour unused by coloured neighbours; O(m^V).
hamiltonian_cycle  fix start 0, extend with unvisited neighbours, close the cycle at the end; O(V!).
knights_tour       Warnsdorff: try the square with the fewest onward moves first.
"""

MOVES = [(1, 0, "D"), (0, -1, "L"), (0, 1, "R"), (-1, 0, "U")]
KNIGHT = [(2, 1), (1, 2), (-1, 2), (-2, 1), (-2, -1), (-1, -2), (1, -2), (2, -1)]


def rat_in_maze(maze):
    n = len(maze)
    out = []
    if n == 0 or not maze[0][0] or not maze[n - 1][n - 1]:
        return out
    seen = {(0, 0)}

    def walk(r, c, path):
        if (r, c) == (n - 1, n - 1):
            out.append("".join(path))
            return
        for dr, dc, ch in MOVES:
            nr, nc = r + dr, c + dc
            if 0 <= nr < n and 0 <= nc < n and maze[nr][nc] and (nr, nc) not in seen:
                seen.add((nr, nc))
                path.append(ch)
                walk(nr, nc, path)
                path.pop()  # undo
                seen.discard((nr, nc))

    walk(0, 0, [])
    return out


def m_coloring(n, edges, m):
    """Colours 1..m per vertex, or [] if m colours are not enough."""
    adj = [[] for _ in range(n)]
    for u, v in edges:
        adj[u].append(v)
        adj[v].append(u)
    color = [0] * n

    def color_from(v):
        if v == n:
            return True
        for c in range(1, m + 1):
            if all(color[u] != c for u in adj[v]):
                color[v] = c
                if color_from(v + 1):
                    return True
                color[v] = 0
        return False

    return color if color_from(0) else []


def hamiltonian_cycle(n, edges):
    """A cycle starting and ending at 0, or [] if none exists."""
    adj = [[False] * n for _ in range(n)]
    for u, v in edges:
        adj[u][v] = adj[v][u] = True
    path, used = [0], [True] + [False] * (n - 1)

    def extend():
        last = path[-1]
        if len(path) == n:
            return adj[last][0]
        for v in range(1, n):
            if not used[v] and adj[last][v]:
                used[v] = True
                path.append(v)
                if extend():
                    return True
                path.pop()
                used[v] = False
        return False

    return path + [0] if extend() else []


def knights_tour(n):
    """board[r][c] = move number starting at (0, 0), or [] if there is no tour."""
    board = [[-1] * n for _ in range(n)]
    board[0][0] = 0

    def free(r, c):
        return 0 <= r < n and 0 <= c < n and board[r][c] == -1

    def onward(r, c):
        return sum(free(r + dr, c + dc) for dr, dc in KNIGHT)

    def tour(r, c, step):
        if step == n * n:
            return True
        candidates = sorted(((onward(r + dr, c + dc), r + dr, c + dc) for dr, dc in KNIGHT if free(r + dr, c + dc)),
                            key=lambda t: t[0])  # stable sort keeps move order on ties, like the Java version
        for _, nr, nc in candidates:
            board[nr][nc] = step
            if tour(nr, nc, step + 1):
                return True
            board[nr][nc] = -1
        return False

    return board if tour(0, 0, 1) else []


def is_valid_tour(board):
    n = len(board)
    where = {}
    for r in range(n):
        for c in range(n):
            where[board[r][c]] = (r, c)
    if sorted(where) != list(range(n * n)):
        return False
    return all(abs(where[s][0] - where[s - 1][0]) * abs(where[s][1] - where[s - 1][1]) == 2 for s in range(1, n * n))


if __name__ == "__main__":
    maze = [[1, 0, 0, 0], [1, 1, 0, 1], [1, 1, 0, 0], [0, 1, 1, 1]]
    assert rat_in_maze(maze) == ["DDRDRR", "DRDDRR"]
    assert rat_in_maze([[1, 0], [0, 1]]) == []

    graph = [(0, 1), (1, 2), (2, 3), (3, 0), (0, 2)]
    assert m_coloring(4, graph, 3) == [1, 2, 3, 2]
    assert m_coloring(4, graph, 2) == []

    g1 = [(0, 1), (0, 3), (1, 2), (1, 3), (1, 4), (2, 4), (3, 4)]
    assert hamiltonian_cycle(5, g1) == [0, 1, 2, 4, 3, 0]
    assert hamiltonian_cycle(5, g1[:-1]) == []

    assert is_valid_tour(knights_tour(5)) and is_valid_tour(knights_tour(8))
    assert knights_tour(3) == [] and knights_tour(4) == []
    print("all checks passed")
