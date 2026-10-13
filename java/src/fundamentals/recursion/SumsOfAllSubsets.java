package fundamentals.recursion;


//Given an array of integers, print sums of all subsets in it. Output sums can be printed in any order.
//
//Examples : 
//
//Input : arr[] = {2, 3}
//Output: 0 2 3 5
//
//Input : arr[] = {2, 4, 5}
//Output : 0 2 4 5 6 7 9 11

//There are total 2^n subsets. For every element, we consider two choices, we include it in a subset and we don’t include it in a subset. Below is recursive solution based on this idea.  

public class SumsOfAllSubsets {
	
	// Prints sums of all
    // subsets of arr[l..r]
    static void subsetSums(int[] arr, int l, int r, int sum)
    {
    	 System.out.println(" l= "+l+ " r = "+r  +" sum ="+sum);
 
        // Print current subset
        if (l > r) {
            System.out.println("Sum "+sum + " ");
            return;
        }
 
        // Subset including arr[l]
        subsetSums(arr, l + 1, r, sum + arr[l]);
 
        System.out.println(" Recursion of remiing");
        // Subset excluding arr[l]
        subsetSums(arr, l + 1, r, sum);
    }
 
    // Driver code
    public static void main(String[] args)
    {
        int[] arr = { 5, 4, 3 };
        int n = arr.length;
 
        subsetSums(arr, 0, n - 1, 0);
    }

}
