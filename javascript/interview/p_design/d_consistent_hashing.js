/*
 * Consistent hashing ring with virtual nodes; binary search for the first node clockwise.
 */
const assert = require('node:assert/strict');
const { createHash } = require('node:crypto');

const hash = (s) => createHash('md5').update(s).digest().readUInt32LE(0);

class ConsistentHashRing {
  constructor(virtualNodes = 100) {
    this.virtualNodes = virtualNodes;
    this.positions = []; // sorted
    this.owner = new Map();
  }

  addNode(node) {
    for (let i = 0; i < this.virtualNodes; i++) {
      const h = hash(`${node}#${i}`);
      this.owner.set(h, node);
      this.positions.push(h);
    }
    this.positions.sort((a, b) => a - b);
  }

  nodeFor(key) {
    const h = hash(key);
    let lo = 0;
    let hi = this.positions.length;
    while (lo < hi) {
      const mid = (lo + hi) >> 1;
      if (this.positions[mid] < h) lo = mid + 1;
      else hi = mid;
    }
    return this.owner.get(this.positions[lo % this.positions.length]); // wrap around
  }
}

const ring = new ConsistentHashRing();
['cache-A', 'cache-B', 'cache-C'].forEach((n) => ring.addNode(n));
const keys = Array.from({ length: 10_000 }, (_, i) => `user:${i}`);
const before = new Map(keys.map((k) => [k, ring.nodeFor(k)]));
const load = {};
for (const node of before.values()) load[node] = (load[node] ?? 0) + 1;
console.log('load with 3 nodes:', load);
assert.ok(Object.values(load).every((c) => c > 2000 && c < 5000));
ring.addNode('cache-D');
const moved = keys.filter((k) => ring.nodeFor(k) !== before.get(k));
console.log(`moved after adding a 4th node: ${moved.length} of ${keys.length}`);
assert.ok(moved.every((k) => ring.nodeFor(k) === 'cache-D'));
assert.ok(moved.length < keys.length / 2);
console.log('all checks passed');
