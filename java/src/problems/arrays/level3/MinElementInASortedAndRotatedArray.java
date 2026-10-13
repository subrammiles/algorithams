package problems.arrays.level3;

/*
 A sorted array is rotated at some unknown point, find the minimum element in it. 
The following solution assumes that all elements are distinct.
Input: {5, 6, 1, 2, 3, 4}
Output: 1

Input: {1, 2, 3, 4}
Output: 1

 * */
public class MinElementInASortedAndRotatedArray {
	
	
	
	int findMin1(int arr[], int low, int high)
    {
        // This condition is needed to handle the case when array
        // is not rotated at all
        if (high < low)  return arr[0];
 
        // If there is only one element left
        if (high == low) return arr[low];
 
        // Find mid
        int mid = low + (high - low)/2; /*(low + high)/2;*/
 
        // Check if element (mid+1) is minimum element. Consider
        // the cases like {3, 4, 5, 1, 2}
        if (mid < high && arr[mid+1] < arr[mid])
            return arr[mid+1];
 
        // Check if mid itself is minimum element
        if (mid > low && arr[mid] < arr[mid - 1])
            return arr[mid];
 
        // Decide whether we need to go to left half or right half
        if (arr[high] > arr[mid])
            return findMin1(arr, low, mid-1);
        return findMin1(arr, mid+1, high);
    }
	
	/*handle duplicates*/
	int findMin2(int arr[],int low, int high)
	{
	    while(low < high)
	    {
	        int mid = low + (high - low) / 2;
	        if (arr[mid] == arr[high])
	            high--;
	             
	        else if(arr[mid] > arr[high])
	            low = mid + 1;
	        else
	            high = mid;
	    }
	    return arr[high];
	}


}
