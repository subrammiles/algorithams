package algorithms.greedy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import common.Check;

/*
 * Job sequencing with deadlines: each job takes one time unit, has a deadline and a profit.
 * Maximise profit.
 *
 * Greedy: take jobs in decreasing profit; put each into the LATEST free slot <= its deadline
 * (keeps earlier slots open for jobs with tighter deadlines).
 * Naive slot search is O(n * maxDeadline). With union-find, find(d) returns the latest free slot
 * <= d, and after using slot s we union it with s-1: near O(n log n) overall (dominated by the sort).
 */
public class D_JobSequencing {

	static class Job {
		final String id;
		final int deadline, profit;

		Job(String id, int deadline, int profit) {
			this.id = id;
			this.deadline = deadline;
			this.profit = profit;
		}
	}

	static int[] parent;

	private static int find(int x) {
		while (parent[x] != x) {
			parent[x] = parent[parent[x]];
			x = parent[x];
		}
		return x;
	}

	/* Returns the scheduled job ids in time order; profit via totalProfit(). */
	static List<String> schedule(Job[] jobs) {
		Job[] sorted = jobs.clone();
		Arrays.sort(sorted, (a, b) -> Integer.compare(b.profit, a.profit));
		int maxDeadline = 0;
		for (Job j : jobs) maxDeadline = Math.max(maxDeadline, j.deadline);
		parent = new int[maxDeadline + 1];
		for (int i = 0; i <= maxDeadline; i++) parent[i] = i; // slot 0 = "no slot left"
		String[] slots = new String[maxDeadline + 1];
		for (Job j : sorted) {
			int slot = find(j.deadline);
			if (slot > 0) {
				slots[slot] = j.id;
				parent[slot] = slot - 1; // next search from here falls through to the previous slot
			}
		}
		List<String> order = new ArrayList<>();
		for (int t = 1; t <= maxDeadline; t++) if (slots[t] != null) order.add(slots[t]);
		return order;
	}

	static int totalProfit(Job[] jobs, List<String> chosen) {
		int total = 0;
		for (Job j : jobs) if (chosen.contains(j.id)) total += j.profit;
		return total;
	}

	public static void main(String[] args) {
		Job[] jobs = { new Job("a", 2, 100), new Job("b", 1, 19), new Job("c", 2, 27), new Job("d", 1, 25), new Job("e", 3, 15) };
		List<String> order = schedule(jobs);
		Check.eq("schedule", order, List.of("c", "a", "e"));
		Check.eq("profit", totalProfit(jobs, order), 142);
		Job[] jobs2 = { new Job("x", 4, 20), new Job("y", 1, 10), new Job("z", 1, 40), new Job("w", 1, 30) };
		Check.eq("profit 2", totalProfit(jobs2, schedule(jobs2)), 60);
	}
}
