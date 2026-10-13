"""Classic recursion: Tower of Hanoi (2^n - 1 moves), Josephus J(n) = (J(n-1) + k) % n,
binary strings without consecutive 1s, phone letter combinations, valid parentheses (Catalan).
"""


def hanoi(n, src="A", dst="C", via="B", moves=None):
    moves = [] if moves is None else moves
    if n:
        hanoi(n - 1, src, via, dst, moves)
        moves.append(f"{src}->{dst}")
        hanoi(n - 1, via, dst, src, moves)
    return moves


def josephus(n, k):
    """1-based position of the survivor."""
    def zero_based(m):
        return 0 if m == 1 else (zero_based(m - 1) + k) % m
    return zero_based(n) + 1


def binary_strings_no_consecutive_ones(n, cur=""):
    if len(cur) == n:
        return [cur]
    out = binary_strings_no_consecutive_ones(n, cur + "0")
    if not cur or cur[-1] == "0":
        out += binary_strings_no_consecutive_ones(n, cur + "1")
    return out


KEYPAD = {"2": "abc", "3": "def", "4": "ghi", "5": "jkl", "6": "mno", "7": "pqrs", "8": "tuv", "9": "wxyz"}


def letter_combinations(digits):
    if not digits:
        return []
    out = []

    def go(i, cur):
        if i == len(digits):
            out.append(cur)
            return
        for ch in KEYPAD[digits[i]]:
            go(i + 1, cur + ch)

    go(0, "")
    return out


def generate_parentheses(n):
    out = []

    def go(cur, open_, close):
        if len(cur) == 2 * n:
            out.append(cur)
            return
        if open_ < n:
            go(cur + "(", open_ + 1, close)
        if close < open_:
            go(cur + ")", open_, close + 1)

    go("", 0, 0)
    return out


if __name__ == "__main__":
    assert hanoi(2) == ["A->B", "A->C", "B->C"]
    assert len(hanoi(10)) == 1023
    assert josephus(7, 3) == 4 and josephus(5, 2) == 3
    assert binary_strings_no_consecutive_ones(3) == ["000", "001", "010", "100", "101"]
    assert letter_combinations("23") == ["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"]
    assert letter_combinations("") == []
    assert generate_parentheses(3) == ["((()))", "(()())", "(())()", "()(())", "()()()"]
    print("all checks passed")
