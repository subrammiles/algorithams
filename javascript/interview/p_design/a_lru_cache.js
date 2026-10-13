/*
 * LRU cache. JS Map keeps insertion order, so delete + set moves a key to the "newest" end and
 * map.keys().next() is the oldest - a complete O(1) LRU in a few lines. The manual
 * hash map + doubly linked list version is what interviewers usually want you to write.
 */
const assert = require('node:assert/strict');

class LRUCache {
  constructor(capacity) {
    this.capacity = capacity;
    this.map = new Map();
    this.head = { prev: null, next: null };
    this.tail = { prev: this.head, next: null };
    this.head.next = this.tail;
  }

  #unlink(n) {
    n.prev.next = n.next;
    n.next.prev = n.prev;
  }

  #addFront(n) {
    n.prev = this.head;
    n.next = this.head.next;
    this.head.next.prev = n;
    this.head.next = n;
  }

  get(key) {
    const n = this.map.get(key);
    if (!n) return -1;
    this.#unlink(n);
    this.#addFront(n);
    return n.value;
  }

  put(key, value) {
    let n = this.map.get(key);
    if (n) {
      n.value = value;
      this.#unlink(n);
      this.#addFront(n);
      return;
    }
    if (this.map.size === this.capacity) {
      const lru = this.tail.prev;
      this.#unlink(lru);
      this.map.delete(lru.key);
    }
    n = { key, value };
    this.map.set(key, n);
    this.#addFront(n);
  }
}

class MapLRU {
  constructor(capacity) {
    this.capacity = capacity;
    this.map = new Map();
  }

  get(key) {
    if (!this.map.has(key)) return -1;
    const v = this.map.get(key);
    this.map.delete(key);
    this.map.set(key, v); // move to newest
    return v;
  }

  put(key, value) {
    this.map.delete(key);
    this.map.set(key, value);
    if (this.map.size > this.capacity) this.map.delete(this.map.keys().next().value); // oldest
  }
}

for (const Cache of [LRUCache, MapLRU]) {
  const c = new Cache(2);
  c.put(1, 1);
  c.put(2, 2);
  assert.equal(c.get(1), 1);
  c.put(3, 3);
  assert.equal(c.get(2), -1);
  c.put(4, 4);
  assert.equal(c.get(1), -1);
  assert.equal(c.get(3), 3);
  assert.equal(c.get(4), 4);
}
console.log('all checks passed');
