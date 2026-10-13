/*
 * LFU cache in O(1): values, counts, buckets (count -> insertion-ordered Set), minCount.
 */
const assert = require('node:assert/strict');

class LFUCache {
  constructor(capacity) {
    this.capacity = capacity;
    this.values = new Map();
    this.counts = new Map();
    this.buckets = new Map();
    this.minCount = 0;
  }

  #bucket(c) {
    if (!this.buckets.has(c)) this.buckets.set(c, new Set());
    return this.buckets.get(c);
  }

  #touch(key) {
    const c = this.counts.get(key);
    this.counts.set(key, c + 1);
    const bucket = this.#bucket(c);
    bucket.delete(key);
    if (bucket.size === 0 && c === this.minCount) this.minCount++;
    this.#bucket(c + 1).add(key);
  }

  get(key) {
    if (!this.values.has(key)) return -1;
    this.#touch(key);
    return this.values.get(key);
  }

  put(key, value) {
    if (this.capacity === 0) return;
    if (this.values.has(key)) {
      this.values.set(key, value);
      this.#touch(key);
      return;
    }
    if (this.values.size === this.capacity) {
      const lowest = this.#bucket(this.minCount);
      const evict = lowest.values().next().value; // least recently used among least frequent
      lowest.delete(evict);
      this.values.delete(evict);
      this.counts.delete(evict);
    }
    this.values.set(key, value);
    this.counts.set(key, 1);
    this.#bucket(1).add(key);
    this.minCount = 1;
  }
}

const c = new LFUCache(2);
c.put(1, 1);
c.put(2, 2);
assert.equal(c.get(1), 1);
c.put(3, 3);
assert.equal(c.get(2), -1);
assert.equal(c.get(3), 3);
c.put(4, 4);
assert.equal(c.get(1), -1);
assert.equal(c.get(3), 3);
assert.equal(c.get(4), 4);
console.log('all checks passed');
