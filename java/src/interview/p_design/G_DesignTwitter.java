package interview.p_design;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import common.Check;

/*
 * Problem: Design Twitter.
 *   postTweet(userId, tweetId)
 *   getNewsFeed(userId) -> the 10 most recent tweet ids from the user and everyone they follow
 *   follow(followerId, followeeId) / unfollow(followerId, followeeId)
 *
 * Approach: "pull on read" (fan-out on read).
 *   - Each user keeps their own tweets in posting order, stamped with a global counter.
 *   - The feed is a k-way merge (same as B_MergeKSortedLists): put each followee's newest tweet in a
 *     max-heap by time, pop 10 times, and after each pop push that user's next older tweet.
 *   post O(1), follow/unfollow O(1), feed O(f + 10 log f) for f followees.
 *
 * Edge cases interviewers check: following yourself, unfollowing yourself (not allowed - you always
 * see your own tweets), unfollowing someone you don't follow, users who never posted.
 *
 * Senior follow-ups (this is the classic system-design warm-up):
 *  - Fan-out on WRITE: push each tweet into every follower's precomputed feed (Redis list).
 *    Reads become O(1), but a celebrity with 100M followers makes writes explode.
 *  - Hybrid (what Twitter did): fan-out on write for normal users, pull celebrities' tweets at read
 *    time and merge them in.
 *  - Global counter -> Snowflake ids (timestamp + machine + sequence) so ids sort by time across shards.
 *  - Keep only the newest N tweets per user in memory; older ones come from storage with pagination
 *    (cursor = last tweet id seen, not page number).
 */
public class G_DesignTwitter {

	private static final int FEED_SIZE = 10;

	private int clock = 0;
	private final Map<Integer, List<int[]>> tweets = new HashMap<>(); // user -> [time, tweetId] oldest first
	private final Map<Integer, Set<Integer>> following = new HashMap<>();

	void postTweet(int userId, int tweetId) {
		tweets.computeIfAbsent(userId, k -> new ArrayList<>()).add(new int[] { clock++, tweetId });
	}

	List<Integer> getNewsFeed(int userId) {
		// heap entries: [time, tweetId, user, index into that user's list]
		PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> Integer.compare(b[0], a[0]));
		Set<Integer> sources = new HashSet<>(following.getOrDefault(userId, Set.of()));
		sources.add(userId);
		for (int u : sources) {
			List<int[]> list = tweets.get(u);
			if (list == null || list.isEmpty()) continue;
			int i = list.size() - 1;
			heap.offer(new int[] { list.get(i)[0], list.get(i)[1], u, i });
		}
		List<Integer> feed = new ArrayList<>();
		while (!heap.isEmpty() && feed.size() < FEED_SIZE) {
			int[] top = heap.poll();
			feed.add(top[1]);
			int i = top[3] - 1;
			if (i >= 0) {
				int[] older = tweets.get(top[2]).get(i);
				heap.offer(new int[] { older[0], older[1], top[2], i });
			}
		}
		return feed;
	}

	void follow(int followerId, int followeeId) {
		if (followerId != followeeId) following.computeIfAbsent(followerId, k -> new HashSet<>()).add(followeeId);
	}

	void unfollow(int followerId, int followeeId) {
		Set<Integer> set = following.get(followerId);
		if (set != null) set.remove(followeeId);
	}

	public static void main(String[] args) {
		G_DesignTwitter t = new G_DesignTwitter();
		t.postTweet(1, 5);
		Check.eq("own tweet", t.getNewsFeed(1), List.of(5));
		t.follow(1, 2);
		t.postTweet(2, 6);
		Check.eq("after follow", t.getNewsFeed(1), List.of(6, 5));
		t.unfollow(1, 2);
		Check.eq("after unfollow", t.getNewsFeed(1), List.of(5));
		t.unfollow(1, 1); // no-op: you always see yourself
		Check.eq("unfollow self ignored", t.getNewsFeed(1), List.of(5));
		Check.eq("user with no tweets", t.getNewsFeed(42), List.of());

		G_DesignTwitter busy = new G_DesignTwitter();
		for (int i = 0; i < 8; i++) busy.postTweet(1, 100 + i);
		for (int i = 0; i < 8; i++) busy.postTweet(2, 200 + i);
		busy.follow(1, 2);
		Check.eq("feed keeps only the 10 newest", busy.getNewsFeed(1),
				List.of(207, 206, 205, 204, 203, 202, 201, 200, 107, 106));
	}
}
