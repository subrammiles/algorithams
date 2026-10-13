package algorithms.greedy;

//Java program to solve fractional Knapsack Problem
import java.util.Arrays;
import java.util.Comparator;

//Greedy approach
public class FractionalKnapSack {
	// function to get maximum value
	private static double getMaxValue(ItemCost[] itemCost, int capacity) {

		double totalValue = 0d;
		for (ItemCost i : itemCost) {
			int curWt = (int) i.wt;
			int curVal = (int) i.val;

			if (capacity - curWt >= 0) {
	         /*If current item weight  is less than current capacatiy*/
				capacity = capacity - curWt;
				totalValue += curVal;
			} else {
				/*If current item weight Greater  current capacatiy*/
				/* item cant be picked whole*/
				double fraction = ((double) capacity / (double) curWt);
				totalValue += (curVal * fraction);
				capacity = (int) (capacity - (curWt * fraction));
				break;
			}
		}
		return totalValue;
	}

	// item value class
	static class ItemCost {
		Double cost;
		double wt, val, index;

		// item value function
		@SuppressWarnings("deprecation")
		public ItemCost(int wt, int val, int ind) {
			this.wt = wt;
			this.val = val;
			this.index = ind;
			cost = new Double((double) val / (double) wt);
		}
	}

	// Driver code
	public static void main(String[] args) {
		int[] wt = { 10, 40, 20, 30 };
		int[] val = { 60, 40, 100, 120 };
		int capacity = 50;

		ItemCost[] itemCost = new ItemCost[wt.length];
		for (int i = 0; i < itemCost.length; i++) {
			itemCost[i] = new ItemCost(wt[i], val[i], i);
		}

		// sorting items by value;
		Arrays.sort(itemCost, new Comparator<ItemCost>() {
			@Override
			public int compare(ItemCost o1, ItemCost o2) {
				return o2.cost.compareTo(o1.cost);
			}
		});

		double maxValue = getMaxValue(itemCost,capacity);

		// Function call
		System.out.println("Maximum value we can obtain = " + maxValue);
	}
}
