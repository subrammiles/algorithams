/*
 * Trie with autocomplete and '.' wildcard search.
 */
const assert = require('node:assert/strict');

class Trie {
  root = new Map();

  insert(word) {
    let n = this.root;
    for (const ch of word) {
      if (!n.has(ch)) n.set(ch, new Map());
      n = n.get(ch);
    }
    n.set('$', true);
  }

  #walk(s) {
    let n = this.root;
    for (const ch of s) {
      n = n.get(ch);
      if (!n) return null;
    }
    return n;
  }

  search(word) {
    return this.#walk(word)?.has('$') ?? false;
  }

  startsWith(prefix) {
    return this.#walk(prefix) !== null;
  }

  autocomplete(prefix, limit) {
    const out = [];
    const collect = (n, path) => {
      if (out.length === limit) return;
      if (n.has('$')) out.push(path);
      for (const ch of [...n.keys()].filter((k) => k !== '$').sort()) collect(n.get(ch), path + ch);
    };
    const start = this.#walk(prefix);
    if (start) collect(start, prefix);
    return out;
  }

  searchWithDot(pattern) {
    const match = (n, i) => {
      if (i === pattern.length) return n.has('$');
      if (pattern[i] === '.') return [...n].some(([k, child]) => k !== '$' && match(child, i + 1));
      return n.has(pattern[i]) && match(n.get(pattern[i]), i + 1);
    };
    return match(this.root, 0);
  }
}

const t = new Trie();
['apple', 'app', 'apply', 'ape', 'bat'].forEach((w) => t.insert(w));
assert.ok(t.search('app'));
assert.equal(t.search('ap'), false);
assert.ok(t.startsWith('ap'));
assert.deepEqual(t.autocomplete('app', 10), ['app', 'apple', 'apply']);
assert.deepEqual(t.autocomplete('a', 2), ['ape', 'app']);
assert.ok(t.searchWithDot('b.t'));
assert.equal(t.searchWithDot('a..x'), false);
console.log('all checks passed');
