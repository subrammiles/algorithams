package problems.arrays.level5;

import java.util.Arrays;
import java.util.HashMap;

/*
 Given an array of integers, 
 find anyone combination of four elements 
 in the array whose sum is equal to a given value X.
 
 Input: array = {10, 2, 3, 4, 5, 9, 7, 8} 
       X = 23 
Output: 3 5 7 8
Sum of output is equal to 23, 
i.e. 3 + 5 + 7 + 8 = 23.


Input: array = {1, 2, 3, 4, 5, 9, 7, 8}
       X = 16 
Output: 1 3 5 7
Sum of output is equal to 16, 
i.e. 1 + 3 + 5 + 7 = 16.

 * */
public class Find4ElementsThatSumToAGivenValue {
	//////////////////////////////////////
	/////////Method1/////////////////////
	/////////////////////////////////////
	
	/* The following structure is needed to store pair sums in Aux[]*/
	static class PairSum
	{     
	    // Index (int A[]) of first element in pair
	    public int first;
	    // Index of second element in pair
	    public int sec;
	    // Sum of the pair
	    public int sum;
	}
	 
	// Function to check if two given pairs
	// have any common element or not
	static boolean noCommon(PairSum a, PairSum b)
	{
	    if (a.first == b.first || a.first == b.sec ||
	        a.sec == b.first || a.sec == b.sec)
	        return false;     
	    return true;
	}
	 
	// Following function is needed for sorting
	// pairSum array
	static int Compare(PairSum a, PairSum b)
	{
	    return (a.sum - b.sum);
	}
	 
	// The function finds four
	// elements with given sum X
	static void method1(int[] myArr, int sum)
	{
	    int i, j;
	    int length = myArr.length;
	 
	    // Create an auxiliary array to
	    // store all pair sums
	    int size = (length * (length - 1)) / 2;
	    PairSum[] aux = new PairSum[size];
	 
	    // Generate all possible pairs
	    // from A[] and store sums
	    // of all possible pairs in aux[]
	    int k = 0;
	    for(i = 0; i < length - 1; i++)
	    {
	        for(j = i + 1; j < length; j++)
	        {
	            aux[k] = new PairSum();
	            aux[k].sum = myArr[i] + myArr[j];
	            aux[k].first = i;
	            aux[k].sec = j;
	            k++;
	        }
	    }
	 
	    // Sort the aux[] array using
	    // library function for sorting
	    Arrays.sort(aux);
	 
	    // Now start two index variables
	    // from two corners of array
	    // and move them toward each other.
	    i = 0;
	    j = size - 1;
	    while (i < size && j >= 0)
	    {
	        if ((aux[i].sum + aux[j].sum == sum) &&
	            noCommon(aux[i], aux[j]))
	        {
	            String output = myArr[aux[i].first] + ", " +
	                            myArr[aux[i].sec] + ", " +
	                            myArr[aux[j].first] + ", " +
	                            myArr[aux[j].sec];
	            System.out.println(output);
	            return;
	        }
	        else if (aux[i].sum + aux[j].sum < sum)
	            i++;
	        else
	            j--;
	    }
	}
	 
	
//////////////////////////////////////
/////////Method2/////////////////////
///////Hashing Based Solution[O(n2)]////	
/////////////////////////////////////
	
	static class pair {
        int first, second;
        public pair(int first, int second)
        {
            this.first = first;
            this.second = second;
        }
    }
	
    static void method2(int arr[], int n, int X)
    {
        // Store sums of all pairs in a hash table
        HashMap<Integer, pair> mp = new HashMap<Integer, pair>();
        for (int i = 0; i < (arr.length - 1); i++) {
        	 for (int j = i + 1; j < n; j++) {
        		 mp.put(arr[i] + arr[j], new pair(i, j));
        	 }        
        }
        
        System.out.println(mp);
           
 
        /* Traverse through all pairs and search
         for X - (current pair sum).*/
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                int sum = arr[i] + arr[j];
 
                // If X - sum is present in hash table,
                if (mp.containsKey(X - sum)) {
 
                    // Making sure that all elements are
                    // distinct array elements and an
                    // element is not considered more than
                    // once.
                    pair p = mp.get(X - sum);
                    if (p.first != i && p.first != j
                        && p.second != i && p.second != j) {
                        System.out.print(
                            arr[i] + ", " + arr[j] + ", "
                            + arr[p.first] + ", "
                            + arr[p.second]);
                        return;
                    }
                }
            }
        }
    }
	 
	// Driver code
	static public void main(String[] args)
	{
	    int[] arr = { 10, 2, 3, 4, 5, 9, 7, 8 };
	    int X = 23;
	    // Function call
	    method2(arr,arr.length, X);
	}
	
}
