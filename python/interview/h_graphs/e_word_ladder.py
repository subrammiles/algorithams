"""Word ladder: BFS on an implicit graph; neighbours by trying 26 letters per position.

Bidirectional BFS (always expand the smaller frontier) explores ~2 * b^(d/2) instead of b^d.
"""
from collections import deque
from string import ascii_lowercase


def neighbours(word):
    for i in range(len(word)):
        for c in ascii_lowercase:
            if c != word[i]:
                yield word[:i] + c + word[i + 1:]


def ladder_length(begin, end, word_list):
    words = set(word_list)
    if end not in words:
        return 0
    front, back, visited, steps = {begin}, {end}, set(), 1
    while front and back:
        if len(front) > len(back):
            front, back = back, front
        nxt = set()
        for w in front:
            for cand in neighbours(w):
                if cand in back:
                    return steps + 1
                if cand in words and cand not in visited:
                    visited.add(cand)
                    nxt.add(cand)
        front, steps = nxt, steps + 1
    return 0


def ladder_length_bfs(begin, end, word_list):
    words = set(word_list)
    queue, steps = deque([begin]), 1
    while queue:
        for _ in range(len(queue)):
            w = queue.popleft()
            if w == end:
                return steps
            for cand in neighbours(w):
                if cand in words:
                    words.remove(cand)
                    queue.append(cand)
        steps += 1
    return 0


if __name__ == "__main__":
    words = ["hot", "dot", "dog", "lot", "log", "cog"]
    assert ladder_length("hit", "cog", words) == 5
    assert ladder_length_bfs("hit", "cog", words) == 5
    assert ladder_length("hit", "cog", words[:-1]) == 0
    print("all checks passed")
