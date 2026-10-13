package problems.arrays.level3;

/*
 An array contains both positive and negative numbers in random order. Rearrange the array elements so that all negative numbers appear before all positive numbers.

Examples : 

Input: -12, 11, -13, -5, 6, -7, 5, -3, -6
Output: -12 -13 -5 -7 -3 -6 11 6 5
Note: Order of elements is not important here.
 * */
public class MoveAllNegativeNumToBeginningPositiveToEnd {
	 static void rearrange(int arr[], int n)
	    {
	        int j = 0, temp;
	        for (int i = 0; i < n; i++) {
	            if (arr[i] < 0) {
	                if (i != j) {
	                    temp = arr[i];
	                    arr[i] = arr[j];
	                    arr[j] = temp;
	                }
	                j++;
	            }
	        }
	    }
	 
	    // A utility function to print an array
	    static void printArray(int arr[], int n)
	    {
	        for (int i = 0; i < n; i++)
	            System.out.print(arr[i] + " ");
	    }
	    
	 // Function to shift all the
	 // negative elements on left side
	 static void shiftall(int[] arr, int left,
	                      int right)
	 {
	      
	     // Loop to iterate over the
	     // array from left to the right
	     while (left <= right)
	     {
	          
	         // Condition to check if the left
	         // and the right elements are
	         // negative
	         if (arr[left] < 0 && arr[right] < 0)
	             left++;
	  
	         // Condition to check if the left
	         // pointer element is positive and
	         // the right pointer element is negative
	         else if (arr[left] > 0 && arr[right] < 0)
	         {
	             int temp = arr[left];
	             arr[left] = arr[right];
	             arr[right] = temp;
	             left++;
	             right--;
	         }
	  
	         // Condition to check if both the
	         // elements are positive
	         else if (arr[left] > 0 && arr[right] > 0)
	             right--;
	         else
	         {
	             left++;
	             right--;
	         }
	     }
	 }
	  
	 // Function to print the array
	 static void display(int[] arr, int right)
	 {
	      
	     // Loop to iterate over the element
	     // of the given array
	     for(int i = 0; i <= right; ++i)
	         System.out.print(arr[i] + " ");
	          
	     System.out.println();
	 }
	 
	// a utility function to swap two elements of an array
	    public static void swap(int[] ar, int i, int j)
	    {
	        int t = ar[i];
	        ar[i] = ar[j];
	        ar[j] = t;
	    }
	 
	    // function to shilf all negative integers to the left
	    // and all positive integers to the right
	    // using Dutch National Flag Algorithm
	    public static void move(int[] ar)
	    {
	        int low = 0;
	        int high = ar.length - 1;
	        while (low <= high) {
	            if (ar[low] <= 0)
	                low++;
	            else
	                swap(ar, low, high--);
	        }
	    }
	 
	    // Driver code
	    public static void main(String args[])
	    {
	        int arr[] = { -1, 2, -3, 4, 5, 6, -7, 8, 9 };
	        int n = arr.length;
	 
	        rearrange(arr, n);
	        printArray(arr, n);
	    }
}
