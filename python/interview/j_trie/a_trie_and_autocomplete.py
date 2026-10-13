"""Trie with autocomplete and '.' wildcard search. insert/search/starts_with O(L).

Typeahead at scale: store top-k suggestions per node, shard by prefix, cache hot prefixes.
"""


class Trie:
    def __init__(self):
        self.root = {}

    def insert(self, word):
        n = self.root
        for ch in word:
            n = n.setdefault(ch, {})
        n["$"] = True  # end-of-word marker

    def _walk(self, s):
        n = self.root
        for ch in s:
            if ch not in n:
                return None
            n = n[ch]
        return n

    def search(self, word):
        n = self._walk(word)
        return n is not None and "$" in n

    def starts_with(self, prefix):
        return self._walk(prefix) is not None

    def autocomplete(self, prefix, limit):
        out, start = [], self._walk(prefix)

        def collect(n, path):
            if len(out) == limit:
                return
            if "$" in n:
                out.append(path)
            for ch in sorted(k for k in n if k != "$"):
                collect(n[ch], path + ch)

        if start is not None:
            collect(start, prefix)
        return out

    def search_with_dot(self, pattern):
        def match(n, i):
            if i == len(pattern):
                return "$" in n
            ch = pattern[i]
            if ch == ".":
                return any(match(child, i + 1) for k, child in n.items() if k != "$")
            return ch in n and match(n[ch], i + 1)

        return match(self.root, 0)


if __name__ == "__main__":
    t = Trie()
    for w in ("apple", "app", "apply", "ape", "bat"):
        t.insert(w)
    assert t.search("app") and not t.search("ap") and t.starts_with("ap")
    assert t.autocomplete("app", 10) == ["app", "apple", "apply"]
    assert t.autocomplete("a", 2) == ["ape", "app"]
    assert t.search_with_dot("b.t") and not t.search_with_dot("a..x")
    print("all checks passed")
