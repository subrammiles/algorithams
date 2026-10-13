/*
 * Union-find: redundant connection, accounts merge.
 */
const assert = require('node:assert/strict');

class DSU {
  constructor(n) {
    this.parent = Array.from({ length: n }, (_, i) => i);
    this.rank = new Array(n).fill(0);
    this.components = n;
  }

  find(x) {
    while (this.parent[x] !== x) {
      this.parent[x] = this.parent[this.parent[x]];
      x = this.parent[x];
    }
    return x;
  }

  union(a, b) {
    let ra = this.find(a);
    let rb = this.find(b);
    if (ra === rb) return false;
    if (this.rank[ra] < this.rank[rb]) [ra, rb] = [rb, ra];
    this.parent[rb] = ra;
    if (this.rank[ra] === this.rank[rb]) this.rank[ra]++;
    this.components--;
    return true;
  }
}

function findRedundantConnection(edges) {
  const dsu = new DSU(edges.length + 1);
  return edges.find(([u, v]) => !dsu.union(u, v)) ?? [];
}

function accountsMerge(accounts) {
  const id = new Map();
  const owner = new Map();
  for (const [name, ...emails] of accounts) {
    for (const e of emails) {
      if (!id.has(e)) id.set(e, id.size);
      owner.set(e, name);
    }
  }
  const dsu = new DSU(id.size);
  for (const [, first, ...rest] of accounts) for (const e of rest) dsu.union(id.get(first), id.get(e));
  const groups = new Map();
  for (const [e, i] of id) {
    const root = dsu.find(i);
    if (!groups.has(root)) groups.set(root, []);
    groups.get(root).push(e);
  }
  return [...groups.values()]
    .map((emails) => [owner.get(emails[0]), ...emails.sort()])
    .sort((a, b) => (a[1] < b[1] ? -1 : 1));
}

assert.deepEqual(findRedundantConnection([[1, 2], [1, 3], [2, 3]]), [2, 3]);
const accounts = [
  ['John', 'johnsmith@mail.com', 'john_newyork@mail.com'],
  ['John', 'johnsmith@mail.com', 'john00@mail.com'],
  ['Mary', 'mary@mail.com'],
  ['John', 'johnnybravo@mail.com'],
];
assert.deepEqual(accountsMerge(accounts), [
  ['John', 'john00@mail.com', 'john_newyork@mail.com', 'johnsmith@mail.com'],
  ['John', 'johnnybravo@mail.com'],
  ['Mary', 'mary@mail.com'],
]);
console.log('all checks passed');
