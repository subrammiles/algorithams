/*
 * Time-based key-value store: per key, timestamps arrive increasing -> binary search for floor.
 */
const assert = require('node:assert/strict');

class TimeMap {
  store = new Map(); // key -> [[timestamp, value], ...]

  set(key, value, timestamp) {
    if (!this.store.has(key)) this.store.set(key, []);
    this.store.get(key).push([timestamp, value]);
  }

  get(key, timestamp) {
    const versions = this.store.get(key) ?? [];
    let lo = 0;
    let hi = versions.length; // first index with time > timestamp
    while (lo < hi) {
      const mid = (lo + hi) >> 1;
      if (versions[mid][0] <= timestamp) lo = mid + 1;
      else hi = mid;
    }
    return lo ? versions[lo - 1][1] : '';
  }
}

const kv = new TimeMap();
kv.set('foo', 'bar', 1);
assert.equal(kv.get('foo', 1), 'bar');
assert.equal(kv.get('foo', 3), 'bar');
kv.set('foo', 'bar2', 4);
assert.equal(kv.get('foo', 4), 'bar2');
assert.equal(kv.get('foo', 0), '');
assert.equal(kv.get('nope', 5), '');
console.log('all checks passed');
