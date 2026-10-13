/*
 * Token bucket and sliding window log rate limiters (time passed in explicitly).
 * Node is single-threaded, so no locks are needed inside one process; across processes
 * use a shared store (e.g. Redis + a Lua script for atomicity).
 */
const assert = require('node:assert/strict');

class TokenBucket {
  constructor(capacity, refillPerSecond, nowMs) {
    this.capacity = capacity;
    this.refillPerMs = refillPerSecond / 1000;
    this.tokens = capacity;
    this.last = nowMs;
  }

  allow(nowMs) {
    this.tokens = Math.min(this.capacity, this.tokens + (nowMs - this.last) * this.refillPerMs);
    this.last = nowMs;
    if (this.tokens >= 1) {
      this.tokens -= 1;
      return true;
    }
    return false;
  }
}

class SlidingWindowLog {
  constructor(limit, windowMs) {
    this.limit = limit;
    this.windowMs = windowMs;
    this.logs = new Map();
  }

  allow(user, nowMs) {
    if (!this.logs.has(user)) this.logs.set(user, []);
    const log = this.logs.get(user);
    while (log.length && log[0] <= nowMs - this.windowMs) log.shift();
    if (log.length < this.limit) {
      log.push(nowMs);
      return true;
    }
    return false;
  }
}

const b = new TokenBucket(3, 1, 0);
assert.ok(b.allow(0) && b.allow(0) && b.allow(0));
assert.equal(b.allow(0), false);
assert.equal(b.allow(500), false);
assert.ok(b.allow(1000));

const w = new SlidingWindowLog(2, 1000);
assert.ok(w.allow('u1', 0) && w.allow('u1', 100));
assert.equal(w.allow('u1', 900), false);
assert.ok(w.allow('u2', 900));
assert.ok(w.allow('u1', 1000));
console.log('all checks passed');
