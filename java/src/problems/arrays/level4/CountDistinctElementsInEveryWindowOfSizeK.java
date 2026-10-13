package problems.arrays.level4;
import java.util.Arrays;
import java.util.HashMap;

/*
 Given an array of size n and an integer k,
  return the count of distinct numbers in all 
  windows of size k. 

Example: 

Input: arr[] = {1, 2, 1, 3, 4, 2, 3};
       k = 4
Output: 3 4 4 3

Explanation:
First window is {1, 2, 1, 3}, count of distinct numbers is 3
Second window is {2, 1, 3, 4} count of distinct numbers is 4
Third window is {1, 3, 4, 2} count of distinct numbers is 4
Fourth window is {3, 4, 2, 3} count of distinct numbers is 3
 * */
public class CountDistinctElementsInEveryWindowOfSizeK {
	// Counts distinct elements in window of size k
    static int countWindowDistinct(int win[], int k)
    {
        int dist_count = 0;
  
        // Traverse the window
        for (int i = 0; i < k; i++) {
            // Check if element arr[i] exists in arr[0..i-1]
            int j;
            for (j = 0; j < i; j++)
                if (win[i] == win[j])
                    break;
            if (j == i)
                dist_count++;
        }
        return dist_count;
    }
  
    // Counts distinct elements in all windows of size k
    static void countDistinct(int arr[], int n, int k)
    {
        // Traverse through every window
        for (int i = 0; i <= n - k; i++)
            System.out.println(countWindowDistinct(Arrays.copyOfRange(arr, i, arr.length), k));
    }
    
    
    static void countDistinct(int arr[], int k)
    {
        // Creates an empty hashMap hM
        HashMap<Integer, Integer> hM = new HashMap<Integer, Integer>();
  
        // Traverse the first window and store count
        // of every element in hash map
        for (int i = 0; i < k; i++)
            hM.put(arr[i], hM.getOrDefault(arr[i], 0) + 1);
  
        // Print count of first window
        System.out.println(hM.size());
  
        // Traverse through the remaining array
        for (int i = k; i < arr.length; i++) {
  
            // Remove first element of previous window
            // If there was only one occurrence
            if (hM.get(arr[i - k]) == 1) {
                hM.remove(arr[i - k]);
            }
  
            else // reduce count of the removed element
                hM.put(arr[i - k],  hM.get(arr[i - k]) - 1);            
  
            // Add new element of current window
            // If this element appears first time,
            // set its count as 1,
            hM.put(arr[i], hM.getOrDefault(arr[i], 0) + 1);
  
            // Print count of current window
            System.out.println(hM.size());
        }
    }
  
   
  
    // Driver method
    public static void main(String args[])
    {
        int arr[] = { 1, 2, 1, 3, 4, 2, 3 }, k = 4;
  
        countDistinct(arr, arr.length, k);
        countDistinct(arr, k);
    }
}
