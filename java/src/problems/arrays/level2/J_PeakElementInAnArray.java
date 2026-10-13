package problems.arrays.level2;

public class J_PeakElementInAnArray {
	static int findPeak(int arr[], int n)
	{
	     
	    // First or last element is peak element
	    if (n == 1)
	      return 0;
	    if (arr[0] >= arr[1])
	        return 0;
	    if (arr[n - 1] >= arr[n - 2])
	        return n - 1;
	 
	    // Check for every other element
	    for(int i = 1; i < n - 1; i++)
	    {
	         
	        // Check if the neighbors are smaller
	        if (arr[i] >= arr[i - 1] &&
	            arr[i] >= arr[i + 1])
	            return i;
	    }
	    return 0;
	}
	
	// A binary search based function
    // that returns index of a peak element
    static int findPeakUtil(
        int arr[], int low, int high, int n)
    {
        // Find index of middle element
        // (low + high)/2
        int mid = low + (high - low) / 2;
 
        // Compare middle element with its
        // neighbours (if neighbours exist)
        if ((mid == 0 || arr[mid - 1] <= arr[mid])
            && (mid == n - 1 || arr[mid + 1] <= arr[mid]))
            return mid;
 
        // If middle element is not peak
        // and its left neighbor is
        // greater than it, then left half
        // must have a peak element
        else if (mid > 0 && arr[mid - 1] > arr[mid])
            return findPeakUtil(arr, low, (mid - 1), n);
 
        // If middle element is not peak
        // and its right neighbor
        // is greater than it, then right
        // half must have a peak
        // element
        else
            return findPeakUtil(
                arr, (mid + 1), high, n);
    }
 
    // A wrapper over recursive function
    // findPeakUtil()
    static int findPeakRecur(int arr[], int n)
    {
        return findPeakUtil(arr, 0, n - 1, n);
    }
 
    // Driver method
    
    
 // A binary search based function
    // that returns index of a peak element
    static int findPeakUtilIterative(int arr[], int low, int high, int n) {
      int l = low;
      int r = high;
      int mid = 0;
      while (l <= r)
      {
   
        // finding mid by binary right shifting.
        mid = (l + r) >> 1;
   
        // first case if mid is the answer
        if ((mid == 0 || arr[mid - 1] <= arr[mid] &&
             (mid == n - 1 || arr[mid + 1] <= arr[mid])))
          break;
   
        // if we have to perform left recursion
        if (mid > 0 && arr[mid - 1] > arr[mid])
          r = mid - 1;
   
        // else right recursion.
        else
          l = mid + 1;
      }
      return mid;
    }
   
    // A wrapper over recursive function findPeakUtil()
    static int findPeakIterative(int arr[], int n) {
      return findPeakUtilIterative(arr, 0, n - 1, n);
    }
	 
	// Driver Code
	public static void main(String[] args)
	{
	    int arr[] = { 1, 3, 20, 4, 1, 0 };
	    int n = arr.length;
	    System.out.println(
	            "Index of a peak point is " + findPeak(arr, n));
	     
	    System.out.print("Index of a peak point is " +
	                     findPeak(arr, n));
	}
}
