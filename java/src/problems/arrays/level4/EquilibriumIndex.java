package problems.arrays.level4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class EquilibriumIndex {
	
	
	/*BruteForce*/
	
	/*Method1*/
	
/*
 Use two loops. Outer loop iterates through
  all the element and inner loop finds out 
  whether the current index picked by the outer loop is
   equilibrium index or not. 
 * */
	static int useTwoLoops(int arr[], int n) {
		int i, j;
        int leftsum, rightsum;
 
        /* Check for indexes one by one until
           an equilibrium index is found */
        for (i = 0; i < n; ++i) {
 
            /* get left sum */
            leftsum = 0; 
            for (j = 0; j < i; j++)
                leftsum += arr[j];
 
            /* get right sum */
            rightsum = 0;
            for (j = i + 1; j < n; j++)
                rightsum += arr[j];
 
            /* if leftsum and rightsum are same,
               then we are done */
            if (leftsum == rightsum)
                return i;
        }
        return -1;
	}
	
	
	/*Using 2 prefix sum twice*/
	
	
	 // Function to find the equilibrium index of an array
    public static void findEquilibriumIndexM2(int A[])
    {
        // `left[i]` stores the sum of elements of subarray `A[0…i-1]`
        int left[] = new int[A.length];
 
        left[0] = 0;
 
        // traverse the array from left to right
        for (int i = 1; i < A.length; i++) {
            left[i] = left[i - 1] + A[i - 1];
        }
 
        // `right` stores the sum of elements of subarray `A[i+1…n)`
        int right = 0;
        
        System.out.println(left);
 
        // maintain a list of indices
        List<Integer> indices = new ArrayList<>();
 
        // traverse the array from right to left
        for (int i = A.length - 1; i >= 0; i--)
        {
            /* If the sum of elements of subarray `A[0…i-1]` is equal to
               the sum of elements of the subarray `A[i+1…n)` i.e.
               (A[0] + … + A[i-1]) = (A[i+1] + A[i+2] + … + A[n-1]) */
 
            if (left[i] == right) {
                indices.add(i);
            }
 
            // new right is `A[i] + (A[i+1] + A[i+2] + … + A[n-1])`
            right += A[i];
        }
 
        System.out.println("Equilibrium Index found at " + indices);
    }
    
    // Function to find the equilibrium index of an array
    public static int findEquilibriumIndexM2V2(int a[], int n)
    {
    	if (n == 1)
            return (0);
         
        int[] front = new int[n];
        int[] back = new int[n];
     
        // Taking the prefixsum from front end array
        for (int i = 0; i < n; i++)
        {
            if (i != 0)
            {
                front[i] = front[i - 1] + a[i];
            }
            else
            {
                front[i] = a[i];
            }
        }
        
        System.out.println(Arrays.toString(front));
       
        // Taking the prefixsum from back end of array
        for (int i = n - 1; i > 0; i--)
        {
            if (i <= n - 2)
            {
                back[i] = back[i + 1] + a[i];
            }
            else
            {
                back[i] = a[i];
            }
        }
        
        System.out.println(Arrays.toString(back));
         
        // Checking for equilibrium index by
        //comparing front and back sums
        for(int i = 0; i < n; i++)
        {
            if (front[i] == back[i])
            {
                return i;
            }
        }
         
        // If no equilibrium index found,then return -1
        return -1;
    }
    
    
    /*Constant extra space*/
    
    static int equilibriumM3(int arr[], int n)
    {
        int total = 0; 
        for (int i = 0; i < n; ++i) {
        	total += arr[i];
        }
        System.out.println(total);
        
        
        int leftsum = 0; 
        for (int i = 0; i < n; ++i) {
        	total -= arr[i]; // sum is now right sum for index i
 
            if (leftsum == total) {
            	System.out.println(i);
            }
               
 
            leftsum += arr[i];
        }
 
        /* If no equilibrium index found, then return 0 */
        return -1;
    }
 
    public static void main (String[] args)
    {
        int[] A = { 0, -3, 5, -4, -2, 3, 1, 0 };
        
 
        useTwoLoops(A,A.length);
        //findEquilibriumIndex(A);
        //findEquilibriumIndexM2V2(A,A.length);
        equilibriumM3(A,A.length);
        
    }

}
