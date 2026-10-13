/*
 * Word Search II: trie of words + one backtracking DFS per cell, pruning finished branches.
 */
const assert = require('node:assert/strict');

function findWords(board, words) {
  const root = new Map();
  for (const w of words) {
    let n = root;
    for (const ch of w) {
      if (!n.has(ch)) n.set(ch, new Map());
      n = n.get(ch);
    }
    n.set('$', w);
  }
  const rows = board.length;
  const cols = board[0].length;
  const found = [];
  const dfs = (r, c, parent) => {
    const ch = board[r][c];
    const node = parent.get(ch);
    if (node.has('$')) {
      found.push(node.get('$'));
      node.delete('$');
    }
    board[r][c] = '#';
    for (const [dr, dc] of [[1, 0], [-1, 0], [0, 1], [0, -1]]) {
      const nr = r + dr;
      const nc = c + dc;
      if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && node.has(board[nr][nc])) dfs(nr, nc, node);
    }
    board[r][c] = ch;
    if (node.size === 0) parent.delete(ch); // prune
  };
  for (let r = 0; r < rows; r++) for (let c = 0; c < cols; c++) if (root.has(board[r][c])) dfs(r, c, root);
  return found.sort();
}

const board = ['oaan', 'etae', 'ihkr', 'iflv'].map((row) => [...row]);
assert.deepEqual(findWords(board, ['oath', 'pea', 'eat', 'rain']), ['eat', 'oath']);
console.log('all checks passed');
