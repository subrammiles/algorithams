/*
 * Randomized set with O(1) insert/remove/getRandom: array + Map value -> index, swap-with-last removal.
 */
const assert = require('node:assert/strict');

class RandomizedSet {
  values = [];
  index = new Map();

  insert(val) {
    if (this.index.has(val)) return false;
    this.index.set(val, this.values.length);
    this.values.push(val);
    return true;
  }

  remove(val) {
    if (!this.index.has(val)) return false;
    const i = this.index.get(val);
    const last = this.values.pop();
    this.index.delete(val);
    if (i < this.values.length) {
      this.values[i] = last;
      this.index.set(last, i);
    }
    return true;
  }

  getRandom() {
    return this.values[Math.floor(Math.random() * this.values.length)];
  }
}

const s = new RandomizedSet();
assert.ok(s.insert(1));
assert.equal(s.remove(2), false);
assert.ok(s.insert(2) && s.insert(3));
assert.equal(s.insert(2), false);
assert.ok(s.remove(1));
const seen = Array.from({ length: 1000 }, () => s.getRandom());
assert.ok(!seen.includes(1));
assert.ok(seen.filter((x) => x === 2).length > 300 && seen.filter((x) => x === 3).length > 300);
console.log('all checks passed');
