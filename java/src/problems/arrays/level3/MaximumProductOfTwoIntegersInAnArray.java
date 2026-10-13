package problems.arrays.level3;

import java.util.Arrays;

/*
 Given an integer array, find the maximum
  product of two integers in it.

For example, consider array {-10, -3, 5, 6, -2}. 
The maximum product is the (-10, -3) or (5, 6) pair.
 * */
public class MaximumProductOfTwoIntegersInAnArray {
	// A naive solution to finding the maximum product of two integers
    // in an array
    public static void findMaximumProduct(int[] A)
    {
        // base case
        if (A.length < 2) {
            return;
        }
 
        int max_product = Integer.MIN_VALUE;
        int max_i = -1, max_j = -1;
 
        // consider every pair of elements
        for (int i = 0; i < A.length - 1; i++)
        {
            for (int j = i + 1; j < A.length; j++)
            {
                // update the maximum product if required
                if (max_product < A[i] * A[j])
                {
                    max_product = A[i] * A[j];
                    max_i = i;
                    max_j = j;
                }
            }
        }
 
        System.out.print("Pair is (" + A[max_i] + ", " + A[max_j] + ")");
    }
    
    
    // A naive solution to finding the maximum product of two integers
    // in an array
    public static void findMaximumProduct2(int[] A)
    {
        // `n` is the length of the array
        int n = A.length;
 
        // base case
        if (n < 2) {
            return;
        }
 
        // sort array in ascending order
        Arrays.sort(A);
 
        // choose the maximum of the following:
        // 1. Product of the first two elements or
        // 2. Product of the last two elements.
 
        if ((A[0] * A[1]) > (A[n - 1] * A[n - 2])) {
            System.out.print("Pair is (" + A[0] + ',' + A[1] + ')');
        }
        else {
            System.out.print("Pair is (" + A[n - 1] + ',' + A[n - 2] + ')');
        }
    }
    
    
 // Function to find the maximum product of two integers in an array
    public static void findMaximumProduct3(int[] A)
    {
        // to store the maximum and second maximum element in an array
        int max1 = A[0], max2 = Integer.MIN_VALUE;
 
        // to store the minimum and second minimum element in an array
        int min1 = A[0], min2 = Integer.MAX_VALUE;
 
        for (int i = 1; i < A.length; i++)
        {
            // if the current element is more than the maximum element,
            // update the maximum and second maximum element
            if (A[i] > max1)
            {
                max2 = max1;
                max1 = A[i];
            }
 
            // if the current element is less than the maximum but greater than the
            // second maximum element, update the second maximum element
            else if (A[i] > max2) {
                max2 = A[i];
            }
 
            // if the current element is less than the minimum element,
            // update the minimum and the second minimum
            if (A[i] < min1)
            {
                min2 = min1;
                min1 = A[i];
            }
 
            // if the current element is more than the minimum but less than the
            // second minimum element, update the second minimum element
            else if (A[i] < min2) {
                min2 = A[i];
            }
 
            // otherwise, ignore the element
        }
 
        // choose the maximum of the following:
        // 1. Product of the maximum and second maximum element or
        // 2. Product of the minimum and second minimum element
        if (max1 * max2 > min1 * min2) {
            System.out.print("Pair is (" + max1 + ", " + max2 + ")");
        }
        else {
            System.out.print("Pair is (" + min1 + ", " + min2 + ")");
        }
    }
 
    public static void main (String[] args)
    {
        int[] A = { -10, -3, 5, 6, -2 };
 
        findMaximumProduct(A);
    }
}
