package problems.arrays.level4;


/*
 Given an array that contains both positive and negative integers, find the product of the maximum product subarray. Expected Time complexity is O(n) and only O(1) extra space can be used.

Examples:

Input: arr[] = {6, -3, -10, 0, 2}
Output:   180  // The subarray is {6, -3, -10}

Input: arr[] = {-1, -3, -10, 0, 60}
Output:   60  // The subarray is {60}

Input: arr[] = {-2, -40, 0, -2, -3}
Output:   80  // The subarray is {-2, -40}
 * */
public class MaximumProductSubarray {
	/* Returns the product of max product subarray.*/
    static int maxSubarrayProduct(int arr[])
    {
        // Initializing result
        int result = arr[0];
        int n = arr.length;
 
        for (int i = 0; i < n; i++)
        {
            int mul = arr[i];
            // traversing in current subarray
            for (int j = i + 1; j < n; j++)
            {
                // updating result every time
                // to keep an eye over the
                // maximum product
                result = Math.max(result, mul);
                mul *= arr[j];
            }
            // updating the result for (n-1)th index.
            result = Math.max(result, mul);
        }
        return result;
    }
    
 // Utility functions to get
    // minimum of two integers
    static int min(int x, int y) {
      return x < y ? x : y;
    }
 
    // Utility functions to get
    // maximum of two integers
    static int max(int x, int y) {
      return x > y ? x : y;
    }
 
    /* Returns the product of
    max product subarray.
    Assumes that the given
    array always has a subarray
    with product more than 1 */
    static int maxSubarrayProduct2(int arr[])
    {
        int n = arr.length;
        // max positive product
        // ending at the current
        // position
        int max_ending_here = 1;
 
        // min negative product
        // ending at the current
        // position
        int min_ending_here = 1;
 
        // Initialize overall max product
        int max_so_far = 0;
        int flag = 0;
 
        /* Traverse through the array. Following
        values are maintained after the ith iteration:
        max_ending_here is always 1 or some positive product
                        ending with arr[i]
        min_ending_here is always 1 or some negative product
                        ending with arr[i] */
        for (int i = 0; i < n; i++)
        {
            /* If this element is positive, update
               max_ending_here. Update min_ending_here only
               if min_ending_here is negative */
            if (arr[i] > 0)
            {
                max_ending_here = max_ending_here * arr[i];
                min_ending_here
                    = min(min_ending_here * arr[i], 1);
                flag = 1;
            }
 
            /* If this element is 0, then the maximum
            product cannot end here, make both
            max_ending_here and min_ending _here 0
            Assumption: Output is alway greater than or
            equal to 1. */
            else if (arr[i] == 0)
            {
                max_ending_here = 1;
                min_ending_here = 1;
            }
 
            /* If element is negative. This is tricky
            max_ending_here can either be 1 or positive.
            min_ending_here can either be 1 or negative.
            next min_ending_here will always be prev.
            max_ending_here * arr[i]
            next max_ending_here will be 1 if prev
            min_ending_here is 1, otherwise
            next max_ending_here will be
                        prev min_ending_here * arr[i] */
            else {
                int temp = max_ending_here;
                max_ending_here
                    = max(min_ending_here * arr[i], 1);
                min_ending_here = temp * arr[i];
            }
 
            // update max_so_far, if needed
            if (max_so_far < max_ending_here)
                max_so_far = max_ending_here;
        }
 
        if (flag == 0 && max_so_far == 0)
            return 0;
        return max_so_far;
    }
 
 // Function to find maximum product subarray
    static int maxProduct(int arr[], int n)
    {
         
        // Variables to store maximum and minimum
        // product till ith index.
        int minVal = arr[0];
        int maxVal = arr[0];
     
        int maxProduct = arr[0];
     
        for (int i = 1; i < n; i++)
        {
     
            // When multiplied by -ve number,
            // maxVal becomes minVal
            // and minVal becomes maxVal.
            if (arr[i] < 0)
            {
                int temp = maxVal;
                maxVal = minVal;
                minVal =temp;
             
            }
     
            // maxVal and minVal stores the
            // product of subarray ending at arr[i].
            maxVal = Math.max(arr[i], maxVal * arr[i]);
            minVal = Math.min(arr[i], minVal * arr[i]);
     
            // Max Product of array.
            maxProduct = Math.max(maxProduct, maxVal);
        }
     
        // Return maximum product found in array.
        return maxProduct;
    }
 
    // Driver Code
    public static void main(String[] args)
    {
        int arr[] = { 1, -2, -3, 0, 7, -8, -2 };
        System.out.println("Maximum Sub array product is "
                           + maxSubarrayProduct(arr));
    }
}
