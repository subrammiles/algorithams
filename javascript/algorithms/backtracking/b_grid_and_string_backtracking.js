/*
 * Word search, palindrome partitioning, restore IP addresses.
 */
const assert = require('node:assert/strict');

function exist(board, word) {
  const search = (i, r, c) => {
    if (i === word.length) return true;
    if (board[r]?.[c] !== word[i]) return false;
    board[r][c] = '#';
    const found = search(i + 1, r + 1, c) || search(i + 1, r - 1, c) || search(i + 1, r, c + 1) || search(i + 1, r, c - 1);
    board[r][c] = word[i];
    return found;
  };
  return board.some((row, r) => row.some((_, c) => search(0, r, c)));
}

function partition(s) {
  const out = [];
  const isPal = (t) => t === [...t].reverse().join('');
  const go = (start, cur) => {
    if (start === s.length) return out.push([...cur]);
    for (let end = start + 1; end <= s.length; end++) {
      const piece = s.slice(start, end);
      if (isPal(piece)) go(end, [...cur, piece]);
    }
  };
  go(0, []);
  return out;
}

function restoreIpAddresses(s) {
  const out = [];
  const go = (start, parts) => {
    const remaining = s.length - start;
    const partsLeft = 4 - parts.length;
    if (remaining < partsLeft || remaining > 3 * partsLeft) return;
    if (partsLeft === 0) return out.push(parts.join('.'));
    for (let len = 1; len <= 3 && start + len <= s.length; len++) {
      const part = s.slice(start, start + len);
      if ((part.length > 1 && part[0] === '0') || Number(part) > 255) continue;
      go(start + len, [...parts, part]);
    }
  };
  go(0, []);
  return out;
}

const board = ['ABCE', 'SFCS', 'ADEE'].map((row) => [...row]);
assert.ok(exist(board, 'ABCCED'));
assert.ok(exist(board, 'SEE'));
assert.equal(exist(board, 'ABCB'), false);
assert.deepEqual(partition('aab'), [['a', 'a', 'b'], ['aa', 'b']]);
assert.deepEqual(restoreIpAddresses('25525511135'), ['255.255.11.135', '255.255.111.35']);
assert.deepEqual(restoreIpAddresses('0000'), ['0.0.0.0']);
assert.deepEqual(restoreIpAddresses('101023'), ['1.0.10.23', '1.0.102.3', '10.1.0.23', '10.10.2.3', '101.0.2.3']);
console.log('all checks passed');
