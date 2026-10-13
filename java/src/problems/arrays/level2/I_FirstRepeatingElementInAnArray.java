package problems.arrays.level2;

import java.util.HashSet;

/*
 Given an array of integers, find the first repeating element in it. We need to find the element that occurs more than once and whose index of first occurrence is smallest. 

Examples: 

Input:  arr[] = {10, 5, 3, 4, 3, 5, 6}
Output: 5 [5 is the first element that repeats]

Input:  arr[] = {6, 10, 5, 4, 9, 120, 4, 6, 10}
Output: 6 [6 is the first element that repeats]
 * */
public class I_FirstRepeatingElementInAnArray {
	// This function prints the first repeating element in arr[]
	static void printFirstRepeating(int arr[]) {
		// Initialize index of first repeating element
		int min = -1;

		// Creates an empty hashset
		HashSet<Integer> set = new HashSet<>();

		// Traverse the input array from right to left
		for (int i = arr.length - 1; i >= 0; i--) {
			// If element is already in hash set, update min
			if (set.contains(arr[i]))
				min = i;

			else // Else add element to hash set
				set.add(arr[i]);
		}

		// Print the result
		if (min != -1)
			System.out.println("The first repeating element is " + arr[min]);
		else
			System.out.println("There are no repeating elements");
	}
	
	/*Method2*/
	// This function prints the
	  // first repeating element in arr[]
	  static void printFirstRepeating(int[] arr, int n)
	  {
	 
	    // This will set k=1, if any
	    // repeating element found
	    int k = 0;
	 
	    // max = maximum from (all elements & n)
	    int max = n;
	    for (int i = 0; i < n; i++)
	      if (max < arr[i])
	        max = arr[i];
	 
	    // Array a is for storing
	    // 1st time occurrence of element
	    // initialized by 0
	    int[] a = new int[max + 1];
	 
	    // Store 1 in array b
	    // if element is duplicate
	    // initialized by 0
	    int[] b = new int[max + 1];
	    for (int i = 0; i < n; i++)
	    {
	 
	      // Duplicate element found
	      if (a[arr[i]] != 0)
	      {
	        b[arr[i]] = 1;
	        k = 1;
	        continue;
	      }
	      else
	        // storing 1st occurrence of arr[i]
	        a[arr[i]] = i+1;
	    }
	 
	    if (k == 0)
	      System.out.println("No repeating element found");
	    else
	    {
	      int min = max + 1;
	 
	      // trace array a & find repeating element
	      // with min index
	      for (int i = 0; i < max + 1; i++)
	        if (a[i] != 0 && min > a[i] && b[i] != 0)
	          min = a[i];
	      System.out.print(arr[min-1]);
	    }
	    System.out.println();
	  }
	  
	  public static int firstRepeated(int[] arr, int n)
	    {
	        int max = 0;
	        for (int x = 0; x < n; x++) {
	            if (arr[x] > max) {
	                max = arr[x];
	            }
	        }
	        int temp[]
	            = new int[max + 1]; // the idea is to use
	                                // temporary array as hashmap
	       // Arrays.fill(temp, 0);
	 
	        for (int x = 0; x < n; x++) {
	            int num = arr[x];
	            temp[num]++;
	        }
	 
	        for (int x = 0; x < n; x++) {
	            int num = arr[x];
	            if (temp[num] > 1) {
	                return x;
	            }
	        }
	 
	        return -1; // if no repeating element found
	    }

	// Driver method to test above method
	public static void main(String[] args) throws java.lang.Exception {
		int arr[] = { 10, 5, 3, 4, 3, 5, 6 };
		printFirstRepeating(arr);
		int n = arr.length;
		printFirstRepeating(arr, n);
	}
}
