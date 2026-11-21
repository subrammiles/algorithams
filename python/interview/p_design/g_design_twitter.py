"""Design Twitter (full explanations and scaling follow-ups in the Java version).

Fan-out on read: each user keeps (time, tweet_id) in posting order; the feed is a k-way merge of
the user's and followees' lists with a max-heap, stopping after 10. post/follow O(1), feed O(f + 10 log f).
"""
import heapq
from collections import defaultdict
from itertools import count

FEED_SIZE = 10


class Twitter:
    def __init__(self):
        self._clock = count()
        self._tweets = defaultdict(list)  # user -> [(time, tweet_id)] oldest first
        self._following = defaultdict(set)

    def post_tweet(self, user_id, tweet_id):
        self._tweets[user_id].append((next(self._clock), tweet_id))

    def get_news_feed(self, user_id):
        heap = []
        for u in self._following[user_id] | {user_id}:
            if self._tweets.get(u):
                i = len(self._tweets[u]) - 1
                time, tid = self._tweets[u][i]
                heap.append((-time, tid, u, i))
        heapq.heapify(heap)
        feed = []
        while heap and len(feed) < FEED_SIZE:
            _, tid, u, i = heapq.heappop(heap)
            feed.append(tid)
            if i > 0:
                time, older = self._tweets[u][i - 1]
                heapq.heappush(heap, (-time, older, u, i - 1))
        return feed

    def follow(self, follower_id, followee_id):
        if follower_id != followee_id:
            self._following[follower_id].add(followee_id)

    def unfollow(self, follower_id, followee_id):
        self._following[follower_id].discard(followee_id)


if __name__ == "__main__":
    t = Twitter()
    t.post_tweet(1, 5)
    assert t.get_news_feed(1) == [5]
    t.follow(1, 2)
    t.post_tweet(2, 6)
    assert t.get_news_feed(1) == [6, 5]
    t.unfollow(1, 2)
    assert t.get_news_feed(1) == [5]
    t.unfollow(1, 1)
    assert t.get_news_feed(1) == [5]
    assert t.get_news_feed(42) == []

    busy = Twitter()
    for i in range(8):
        busy.post_tweet(1, 100 + i)
    for i in range(8):
        busy.post_tweet(2, 200 + i)
    busy.follow(1, 2)
    assert busy.get_news_feed(1) == [207, 206, 205, 204, 203, 202, 201, 200, 107, 106]
    print("all checks passed")
