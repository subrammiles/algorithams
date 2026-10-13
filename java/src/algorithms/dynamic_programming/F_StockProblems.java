package algorithms.dynamic_programming;

import common.Check;

/*
 * Best Time to Buy and Sell Stock - one framework solves all six variants: a STATE MACHINE.
 * At the end of each day you are either holding a share or not:
 *     hold' = max(hold, cash - price)        (keep holding, or buy today)
 *     cash' = max(cash, hold + price - fee)  (keep cash, or sell today)
 * Variants only change the constraints:
 *  - once: buying must use the initial cash 0               -> hold = max(hold, -price)
 *  - unlimited transactions                                  -> the recurrences above
 *  - at most k transactions: one (hold, cash) pair per transaction number, O(n*k)
 *    (if k >= n/2 it is the unlimited case)
 *  - cooldown: buying uses the cash from TWO days ago
 *  - fee: subtract it when selling
 * Being able to derive these from one model (instead of memorising six solutions) is the senior signal.
 */
public class F_StockProblems {

	static int maxProfitOnce(int[] prices) {
		int minPrice = Integer.MAX_VALUE, best = 0;
		for (int p : prices) {
			minPrice = Math.min(minPrice, p);
			best = Math.max(best, p - minPrice);
		}
		return best;
	}

	static int maxProfitUnlimited(int[] prices) {
		int hold = Integer.MIN_VALUE / 2, cash = 0;
		for (int p : prices) {
			int newHold = Math.max(hold, cash - p);
			cash = Math.max(cash, hold + p);
			hold = newHold;
		}
		return cash;
	}

	static int maxProfitK(int k, int[] prices) {
		if (k >= prices.length / 2) return maxProfitUnlimited(prices);
		int[] hold = new int[k + 1], cash = new int[k + 1]; // index = transaction number (counted at buy)
		java.util.Arrays.fill(hold, Integer.MIN_VALUE / 2);
		for (int p : prices) {
			for (int t = k; t >= 1; t--) {
				cash[t] = Math.max(cash[t], hold[t] + p);
				hold[t] = Math.max(hold[t], cash[t - 1] - p);
			}
		}
		return cash[k];
	}

	static int maxProfitTwoTransactions(int[] prices) {
		return maxProfitK(2, prices);
	}

	static int maxProfitCooldown(int[] prices) {
		int hold = Integer.MIN_VALUE / 2, cash = 0, cashTwoDaysAgo = 0;
		for (int p : prices) {
			int newHold = Math.max(hold, cashTwoDaysAgo - p);
			cashTwoDaysAgo = cash;
			cash = Math.max(cash, hold + p);
			hold = newHold;
		}
		return cash;
	}

	static int maxProfitFee(int[] prices, int fee) {
		int hold = Integer.MIN_VALUE / 2, cash = 0;
		for (int p : prices) {
			int newHold = Math.max(hold, cash - p);
			cash = Math.max(cash, hold + p - fee);
			hold = newHold;
		}
		return cash;
	}

	public static void main(String[] args) {
		int[] prices = { 7, 1, 5, 3, 6, 4 };
		Check.eq("once", maxProfitOnce(prices), 5);
		Check.eq("unlimited", maxProfitUnlimited(prices), 7);
		Check.eq("two transactions", maxProfitTwoTransactions(new int[] { 3, 3, 5, 0, 0, 3, 1, 4 }), 6);
		Check.eq("k = 2", maxProfitK(2, new int[] { 3, 2, 6, 5, 0, 3 }), 7);
		Check.eq("k large -> unlimited", maxProfitK(100, prices), 7);
		Check.eq("cooldown", maxProfitCooldown(new int[] { 1, 2, 3, 0, 2 }), 3);
		Check.eq("fee", maxProfitFee(new int[] { 1, 3, 2, 8, 4, 9 }, 2), 8);
		Check.eq("falling prices", maxProfitOnce(new int[] { 7, 6, 4, 3, 1 }), 0);
	}
}
