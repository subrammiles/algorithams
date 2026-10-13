/*
 * Huffman coding: merge the two least frequent trees with a min-heap. Test the total cost,
 * not exact codes (ties can be broken differently).
 */
const assert = require('node:assert/strict');
const { Heap } = require('../../lib/heap');

function buildTree(freq) {
  let order = 0; // tie-breaker for deterministic results
  const heap = new Heap((a, b) => a.freq - b.freq || a.order - b.order);
  for (const sym of [...freq.keys()].sort()) heap.push({ sym, freq: freq.get(sym), order: order++ });
  while (heap.size > 1) {
    const a = heap.pop();
    const b = heap.pop();
    heap.push({ freq: a.freq + b.freq, order: order++, left: a, right: b });
  }
  return heap.pop();
}

function codes(tree) {
  const out = new Map();
  const assign = (n, prefix) => {
    if (!n.left) return out.set(n.sym, prefix || '0');
    assign(n.left, `${prefix}0`);
    assign(n.right, `${prefix}1`);
  };
  assign(tree, '');
  return out;
}

function decode(bits, tree) {
  let out = '';
  let n = tree;
  for (const b of bits) {
    n = b === '0' ? n.left : n.right;
    if (!n.left) {
      out += n.sym;
      n = tree;
    }
  }
  return out;
}

const freq = new Map([...'abcdef'].map((c, i) => [c, [5, 9, 12, 13, 16, 45][i]]));
const c = codes(buildTree(freq));
assert.equal([...freq].reduce((sum, [s, f]) => sum + f * c.get(s).length, 0), 224);
assert.equal(c.get('f').length, 1);
const all = [...c.values()];
assert.ok(all.every((x) => all.every((y) => x === y || !y.startsWith(x))), 'prefix free');
const text = 'abracadabra';
const textFreq = new Map();
for (const ch of text) textFreq.set(ch, (textFreq.get(ch) ?? 0) + 1);
const tree = buildTree(textFreq);
const bits = [...text].map((ch) => codes(tree).get(ch)).join('');
assert.equal(decode(bits, tree), text);
assert.ok(bits.length < 8 * text.length);
console.log('all checks passed');
