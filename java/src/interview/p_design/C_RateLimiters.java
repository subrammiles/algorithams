package interview.p_design;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

import interview.Check;

/*
 * Rate limiter - a favourite senior "low level design" question.
 * Time is passed in explicitly (millis) so the logic is deterministic and testable.
 *
 * 1. Token bucket: tokens refill at a steady rate up to capacity; each request takes one.
 *    Allows short bursts, smooth average. Used by AWS, Stripe. O(1) memory per user.
 * 2. Sliding window log: store request timestamps; drop ones older than the window.
 *    Exact, but O(limit) memory per user.
 * 3. (Mention) Fixed window counter: cheap but allows 2x bursts at window edges.
 *    Sliding window counter: weighted mix of current + previous window, good approximation.
 *
 * Distributed discussion: central store (Redis INCR + EXPIRE or a Lua script for atomicity),
 * per-node local limits, clock skew, what to return (HTTP 429 + Retry-After).
 */
public class C_RateLimiters {

	static class TokenBucket {
		private final long capacity;
		private final double refillPerMilli;
		private double tokens;
		private long lastRefill;

		TokenBucket(long capacity, double refillPerSecond, long nowMillis) {
			this.capacity = capacity;
			this.refillPerMilli = refillPerSecond / 1000.0;
			this.tokens = capacity;
			this.lastRefill = nowMillis;
		}

		synchronized boolean allow(long nowMillis) {
			tokens = Math.min(capacity, tokens + (nowMillis - lastRefill) * refillPerMilli);
			lastRefill = nowMillis;
			if (tokens >= 1) {
				tokens -= 1;
				return true;
			}
			return false;
		}
	}

	static class SlidingWindowLog {
		private final int limit;
		private final long windowMillis;
		private final Map<String, Deque<Long>> logs = new HashMap<>();

		SlidingWindowLog(int limit, long windowMillis) {
			this.limit = limit;
			this.windowMillis = windowMillis;
		}

		synchronized boolean allow(String user, long nowMillis) {
			Deque<Long> log = logs.computeIfAbsent(user, k -> new ArrayDeque<>());
			while (!log.isEmpty() && log.peekFirst() <= nowMillis - windowMillis) log.pollFirst();
			if (log.size() < limit) {
				log.offerLast(nowMillis);
				return true;
			}
			return false;
		}
	}

	public static void main(String[] args) {
		TokenBucket bucket = new TokenBucket(3, 1, 0); // burst 3, 1 token per second
		Check.isTrue("burst 1", bucket.allow(0));
		Check.isTrue("burst 2", bucket.allow(0));
		Check.isTrue("burst 3", bucket.allow(0));
		Check.eq("burst exhausted", bucket.allow(0), false);
		Check.eq("half second later", bucket.allow(500), false);
		Check.isTrue("one second later", bucket.allow(1000));

		SlidingWindowLog log = new SlidingWindowLog(2, 1000);
		Check.isTrue("u1 t=0", log.allow("u1", 0));
		Check.isTrue("u1 t=100", log.allow("u1", 100));
		Check.eq("u1 t=900 blocked", log.allow("u1", 900), false);
		Check.isTrue("u2 independent", log.allow("u2", 900));
		Check.isTrue("u1 t=1000 window slid", log.allow("u1", 1000));
	}
}
