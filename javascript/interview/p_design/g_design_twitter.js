/*
 * Design Twitter (full explanations and scaling follow-ups in the Java version).
 * Fan-out on read: per-user tweet lists stamped with a global clock; the feed is a k-way merge with
 * a max-heap that stops after 10. post/follow O(1), feed O(f + 10 log f).
 */
const assert = require('node:assert/strict');
const { Heap } = require('../../lib/heap');

const FEED_SIZE = 10;

class Twitter {
  #clock = 0;
  #tweets = new Map(); // user -> [[time, tweetId]] oldest first
  #following = new Map();

  postTweet(userId, tweetId) {
    if (!this.#tweets.has(userId)) this.#tweets.set(userId, []);
    this.#tweets.get(userId).push([this.#clock++, tweetId]);
  }

  getNewsFeed(userId) {
    const heap = new Heap((a, b) => b.time - a.time);
    const sources = new Set(this.#following.get(userId) ?? []);
    sources.add(userId);
    for (const user of sources) {
      const list = this.#tweets.get(user);
      if (!list?.length) continue;
      const i = list.length - 1;
      heap.push({ time: list[i][0], id: list[i][1], user, i });
    }
    const feed = [];
    while (heap.size && feed.length < FEED_SIZE) {
      const { id, user, i } = heap.pop();
      feed.push(id);
      if (i > 0) {
        const [time, older] = this.#tweets.get(user)[i - 1];
        heap.push({ time, id: older, user, i: i - 1 });
      }
    }
    return feed;
  }

  follow(followerId, followeeId) {
    if (followerId === followeeId) return;
    if (!this.#following.has(followerId)) this.#following.set(followerId, new Set());
    this.#following.get(followerId).add(followeeId);
  }

  unfollow(followerId, followeeId) {
    this.#following.get(followerId)?.delete(followeeId);
  }
}

const t = new Twitter();
t.postTweet(1, 5);
assert.deepEqual(t.getNewsFeed(1), [5]);
t.follow(1, 2);
t.postTweet(2, 6);
assert.deepEqual(t.getNewsFeed(1), [6, 5]);
t.unfollow(1, 2);
assert.deepEqual(t.getNewsFeed(1), [5]);
t.unfollow(1, 1);
assert.deepEqual(t.getNewsFeed(1), [5]);
assert.deepEqual(t.getNewsFeed(42), []);

const busy = new Twitter();
for (let i = 0; i < 8; i++) busy.postTweet(1, 100 + i);
for (let i = 0; i < 8; i++) busy.postTweet(2, 200 + i);
busy.follow(1, 2);
assert.deepEqual(busy.getNewsFeed(1), [207, 206, 205, 204, 203, 202, 201, 200, 107, 106]);
console.log('all checks passed');
