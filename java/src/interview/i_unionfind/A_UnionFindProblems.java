package interview.i_unionfind;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import interview.Check;

/*
 * Union-Find (Disjoint Set Union) with path compression + union by rank:
 * near-constant amortised time per operation, O(alpha(n)).
 *
 * Use it when the question is about connectivity that only ever GROWS:
 *  1. Redundant Connection - first edge whose endpoints are already connected forms the cycle.
 *  2. Accounts Merge - union every email of an account with its first email, then group by root.
 *  3. Number of Islands II (online), Kruskal's MST, "are x and y friends" queries.
 * It cannot efficiently handle deletions (use offline reverse processing or a different structure).
 */
public class A_UnionFindProblems {

	static class DSU {
		private final int[] parent, rank;
		int components;

		DSU(int n) {
			parent = new int[n];
			rank = new int[n];
			components = n;
			for (int i = 0; i < n; i++) parent[i] = i;
		}

		int find(int x) {
			while (parent[x] != x) {
				parent[x] = parent[parent[x]]; // path halving
				x = parent[x];
			}
			return x;
		}

		boolean union(int a, int b) {
			int ra = find(a), rb = find(b);
			if (ra == rb) return false;
			if (rank[ra] < rank[rb]) {
				int t = ra;
				ra = rb;
				rb = t;
			}
			parent[rb] = ra;
			if (rank[ra] == rank[rb]) rank[ra]++;
			components--;
			return true;
		}
	}

	static int[] findRedundantConnection(int[][] edges) {
		DSU dsu = new DSU(edges.length + 1);
		for (int[] e : edges) if (!dsu.union(e[0], e[1])) return e;
		return new int[0];
	}

	static List<List<String>> accountsMerge(List<List<String>> accounts) {
		Map<String, Integer> emailId = new HashMap<>();
		Map<String, String> emailName = new HashMap<>();
		for (List<String> acc : accounts) {
			for (int i = 1; i < acc.size(); i++) {
				emailId.putIfAbsent(acc.get(i), emailId.size());
				emailName.put(acc.get(i), acc.get(0));
			}
		}
		DSU dsu = new DSU(emailId.size());
		for (List<String> acc : accounts) {
			for (int i = 2; i < acc.size(); i++) dsu.union(emailId.get(acc.get(1)), emailId.get(acc.get(i)));
		}
		Map<Integer, TreeSet<String>> groups = new HashMap<>();
		for (String email : emailId.keySet()) {
			groups.computeIfAbsent(dsu.find(emailId.get(email)), k -> new TreeSet<>()).add(email);
		}
		List<List<String>> result = new ArrayList<>();
		for (TreeSet<String> emails : groups.values()) {
			List<String> row = new ArrayList<>();
			row.add(emailName.get(emails.first()));
			row.addAll(emails);
			result.add(row);
		}
		result.sort((a, b) -> a.get(1).compareTo(b.get(1)));
		return result;
	}

	public static void main(String[] args) {
		Check.eq("redundant", findRedundantConnection(new int[][] { { 1, 2 }, { 1, 3 }, { 2, 3 } }), new int[] { 2, 3 });
		DSU dsu = new DSU(5);
		dsu.union(0, 1);
		dsu.union(3, 4);
		Check.eq("components", dsu.components, 3);
		List<List<String>> accounts = Arrays.asList(
				Arrays.asList("John", "johnsmith@mail.com", "john_newyork@mail.com"),
				Arrays.asList("John", "johnsmith@mail.com", "john00@mail.com"),
				Arrays.asList("Mary", "mary@mail.com"),
				Arrays.asList("John", "johnnybravo@mail.com"));
		Check.eq("accounts merge", accountsMerge(accounts), Arrays.asList(
				Arrays.asList("John", "john00@mail.com", "john_newyork@mail.com", "johnsmith@mail.com"),
				Arrays.asList("John", "johnnybravo@mail.com"),
				Arrays.asList("Mary", "mary@mail.com")));
	}
}
