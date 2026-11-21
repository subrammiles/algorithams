/*
 * Word ladder with bidirectional BFS (expand the smaller frontier), plus plain BFS for comparison.
 */
const assert = require('node:assert/strict');

const ALPHABET = 'abcdefghijklmnopqrstuvwxyz';

function* neighbours(word) {
  for (let i = 0; i < word.length; i++) {
    for (const c of ALPHABET) if (c !== word[i]) yield word.slice(0, i) + c + word.slice(i + 1);
  }
}

function ladderLength(begin, end, wordList) {
  const words = new Set(wordList);
  if (!words.has(end)) return 0;
  let front = new Set([begin]);
  let back = new Set([end]);
  const visited = new Set();
  for (let steps = 1; front.size && back.size; steps++) {
    if (front.size > back.size) [front, back] = [back, front];
    const next = new Set();
    for (const w of front) {
      for (const cand of neighbours(w)) {
        if (back.has(cand)) return steps + 1;
        if (words.has(cand) && !visited.has(cand)) {
          visited.add(cand);
          next.add(cand);
        }
      }
    }
    front = next;
  }
  return 0;
}

// Plain one-directional BFS kept for comparison.
function ladderLengthBfs(begin, end, wordList) {
  const dict = new Set(wordList);
  let queue = [begin];
  for (let steps = 1; queue.length; steps++) {
    const next = [];
    for (const word of queue) {
      if (word === end) return steps;
      for (const cand of neighbours(word)) {
        if (dict.delete(cand)) next.push(cand); // delete = mark visited
      }
    }
    queue = next;
  }
  return 0;
}

const words = ['hot', 'dot', 'dog', 'lot', 'log', 'cog'];
assert.equal(ladderLength('hit', 'cog', words), 5);
assert.equal(ladderLengthBfs('hit', 'cog', words), 5);
assert.equal(ladderLengthBfs('hit', 'cog', words.slice(0, -1)), 0);
assert.equal(ladderLength('hit', 'cog', words.slice(0, -1)), 0);
console.log('all checks passed');
