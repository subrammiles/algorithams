package interview.s_math;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import interview.Check;

/*
 * Small but frequently asked building blocks.
 *
 * Math:
 *  - gcd (Euclid) O(log min(a,b)); lcm = a / gcd * b (divide first to avoid overflow).
 *  - Fast power by squaring with modulo, O(log n). Used for modular inverse, hashing, matrix power.
 *  - Sieve of Eratosthenes O(n log log n).
 * Matrix:
 *  - Rotate image 90 degrees clockwise in place = transpose + reverse each row.
 *  - Spiral order with four shrinking boundaries.
 * Randomized:
 *  - Reservoir sampling: uniform random pick from a stream of unknown length, O(1) memory.
 *    The i-th item (1-based) replaces the choice with probability 1/i.
 */
public class A_MathMatrixRandomized {

	static long gcd(long a, long b) {
		return b == 0 ? a : gcd(b, a % b);
	}

	static long modPow(long base, long exp, long mod) {
		long result = 1;
		base %= mod;
		while (exp > 0) {
			if ((exp & 1) == 1) result = result * base % mod;
			base = base * base % mod;
			exp >>= 1;
		}
		return result;
	}

	static List<Integer> sieve(int n) {
		boolean[] composite = new boolean[n + 1];
		List<Integer> primes = new ArrayList<>();
		for (int i = 2; i <= n; i++) {
			if (composite[i]) continue;
			primes.add(i);
			for (long j = (long) i * i; j <= n; j += i) composite[(int) j] = true;
		}
		return primes;
	}

	static void rotate(int[][] m) {
		int n = m.length;
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				int t = m[i][j];
				m[i][j] = m[j][i];
				m[j][i] = t;
			}
		}
		for (int[] row : m) {
			for (int l = 0, r = n - 1; l < r; l++, r--) {
				int t = row[l];
				row[l] = row[r];
				row[r] = t;
			}
		}
	}

	static List<Integer> spiralOrder(int[][] m) {
		List<Integer> out = new ArrayList<>();
		int top = 0, bottom = m.length - 1, left = 0, right = m[0].length - 1;
		while (top <= bottom && left <= right) {
			for (int c = left; c <= right; c++) out.add(m[top][c]);
			top++;
			for (int r = top; r <= bottom; r++) out.add(m[r][right]);
			right--;
			if (top <= bottom) {
				for (int c = right; c >= left; c--) out.add(m[bottom][c]);
				bottom--;
			}
			if (left <= right) {
				for (int r = bottom; r >= top; r--) out.add(m[r][left]);
				left++;
			}
		}
		return out;
	}

	static int reservoirSample(Iterable<Integer> stream, Random random) {
		int chosen = 0, i = 0;
		for (int x : stream) {
			i++;
			if (random.nextInt(i) == 0) chosen = x;
		}
		return chosen;
	}

	public static void main(String[] args) {
		Check.eq("gcd", gcd(84, 36), 12L);
		Check.eq("modPow", modPow(2, 30, 1_000_000_007), 73741817L);
		Check.eq("sieve", sieve(30), List.of(2, 3, 5, 7, 11, 13, 17, 19, 23, 29));
		int[][] m = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };
		rotate(m);
		Check.eq("rotate", m, new int[][] { { 7, 4, 1 }, { 8, 5, 2 }, { 9, 6, 3 } });
		Check.eq("spiral", spiralOrder(new int[][] { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 } }),
				List.of(1, 2, 3, 4, 8, 12, 11, 10, 9, 5, 6, 7));
		List<Integer> stream = List.of(0, 1, 2, 3, 4);
		int[] hits = new int[5];
		Random random = new Random(3);
		for (int t = 0; t < 50_000; t++) hits[reservoirSample(stream, random)]++;
		for (int h : hits) Check.isTrue("roughly uniform (" + h + ")", h > 9_000 && h < 11_000);
	}
}
