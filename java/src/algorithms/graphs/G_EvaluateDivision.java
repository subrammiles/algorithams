package algorithms.graphs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.Check;

/*
 * Evaluate Division: given a / b = 2.0 and b / c = 3.0, answer queries like a / c.
 *
 * Weighted union-find: every variable stores its parent and weight = value(x) / value(parent).
 *  - find(x) compresses the path and multiplies the weights on the way up, so afterwards
 *    weight[x] = x / root.
 *  - union(a, b, a/b): attach root(a) under root(b) with weight chosen so a / b holds:
 *      weight[rootA] = (a/b) * weight[b] / weight[a]
 *  - query a / c = weight[a] / weight[c] if they share a root, else -1.
 * Near O(1) per operation. (Alternative: BFS/DFS over a weighted graph per query, O(V + E) each.)
 */
public class G_EvaluateDivision {

	private final Map<String, String> parent = new HashMap<>();
	private final Map<String, Double> weight = new HashMap<>(); // x / parent(x)

	private String find(String x) {
		String p = parent.get(x);
		if (!p.equals(x)) {
			String root = find(p);
			weight.put(x, weight.get(x) * weight.get(p)); // x/p * p/root = x/root
			parent.put(x, root);
		}
		return parent.get(x);
	}

	private void add(String x) {
		if (!parent.containsKey(x)) {
			parent.put(x, x);
			weight.put(x, 1.0);
		}
	}

	private void union(String a, String b, double value) {
		add(a);
		add(b);
		String ra = find(a), rb = find(b);
		if (!ra.equals(rb)) {
			parent.put(ra, rb);
			weight.put(ra, value * weight.get(b) / weight.get(a));
		}
	}

	static double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
		G_EvaluateDivision uf = new G_EvaluateDivision();
		for (int i = 0; i < values.length; i++) uf.union(equations.get(i).get(0), equations.get(i).get(1), values[i]);
		double[] out = new double[queries.size()];
		for (int i = 0; i < out.length; i++) {
			String a = queries.get(i).get(0), b = queries.get(i).get(1);
			if (!uf.parent.containsKey(a) || !uf.parent.containsKey(b) || !uf.find(a).equals(uf.find(b))) out[i] = -1;
			else out[i] = uf.weight.get(a) / uf.weight.get(b);
		}
		return out;
	}

	public static void main(String[] args) {
		double[] answers = calcEquation(List.of(List.of("a", "b"), List.of("b", "c")), new double[] { 2.0, 3.0 },
				List.of(List.of("a", "c"), List.of("b", "a"), List.of("a", "e"), List.of("a", "a"), List.of("x", "x")));
		double[] expected = { 6.0, 0.5, -1.0, 1.0, -1.0 };
		for (int i = 0; i < expected.length; i++) Check.near("query " + i, answers[i], expected[i]);
		double[] chain = calcEquation(List.of(List.of("a", "b"), List.of("c", "d"), List.of("b", "c")), new double[] { 1.5, 2.5, 5.0 },
				List.of(List.of("a", "d"), List.of("d", "a")));
		Check.near("joined chains a/d", chain[0], 18.75);
		Check.near("joined chains d/a", chain[1], 1 / 18.75);
	}
}
