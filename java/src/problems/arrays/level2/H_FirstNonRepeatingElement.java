package problems.arrays.level2;

import java.util.HashMap;
import java.util.Map;

/*
 Find the first non-repeating element in a given array of integers.

Examples:

Input : -1 2 -1 3 2
Output : 3
Explanation : The first number that does not 
repeat is : 3

Input : 9 4 9 6 7 4
Output : 6
 * */
public class H_FirstNonRepeatingElement {
	static int firstNonRepeating(int arr[], int n)
    {
        for (int i = 0; i < n; i++) {
            int j;
            for (j = 0; j < n; j++)
                if (i != j && arr[i] == arr[j])
                    break;
            if (j == n)
                return arr[i];
        }
 
        return -1;
    }
	
	static int firstNonRepeating2(int arr[], int n)
    {
        // Insert all array elements in hash
        // table
 
        Map<Integer, Integer> m = new HashMap<>();
        for (int i = 0; i < n; i++) {
            if (m.containsKey(arr[i])) {
                m.put(arr[i], m.get(arr[i]) + 1);
            }
            else {
                m.put(arr[i], 1);
            }
        }
        // Traverse array again and return
        // first element with count 1.
        for (int i = 0; i < n; i++)
            if (m.get(arr[i]) == 1)
                return arr[i];
        return -1;
    }
	
	static void firstNonRepeating3(int arr[], int n)
    {
        // Insert all array elements in hash
        // table
        Map<Integer, Integer> m = new HashMap<>();
        for (int i = 0; i < n; i++) {
            if (m.containsKey(arr[i])) {
                m.put(arr[i], m.get(arr[i]) + 1);
            }
            else {
                m.put(arr[i], 1);
            }
        }
 
        // Traverse through map only and
        // using for-each loop for iteration over Map.entrySet()
        for (Map.Entry<Integer, Integer> x : m.entrySet())
            if (x.getValue() == 1)
                System.out.print(x.getKey() + " ");
    }
 
 
    // Driver code
    public static void main(String[] args)
    {
 
        int arr[] = { 9, 4, 9, 6, 7, 4 };
        int n = arr.length;
 
        System.out.print(firstNonRepeating(arr, n));
    }
}
