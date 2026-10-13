package problems.arrays.level3;

/*
 Given a sorted array arr[] and a number x, write a
  function that counts the occurrences of x in arr[]. 
  Expected time complexity is O(Logn) 
 
 Input: arr[] = {1, 1, 2, 2, 2, 2, 3,},   x = 2
  Output: 4 // x (or 2) occurs 4 times in arr[]
 * */
public class CountFrequencyInASortedArray {
	
//////////////////////////////////////
/////////Method1 /////////////////////
/////////////////////////////////////
	/*Given array is sorted.So we can use binary search */
	// A recursive binary search
    // function. It returns location
    // of x in given array arr[l..r]
    // is present, otherwise -1
    static int binarySearch(int arr[], int l,
                            int r, int x)
    {
        if (r < l)
            return -1;
 
        int mid = l + (r - l) / 2;
 
        // If the element is present
        // at the middle itself
        if (arr[mid] == x)
            return mid;
 
        // If element is smaller than
        // mid, then it can only be
        // present in left subarray
        if (arr[mid] > x)
            return binarySearch(arr, l,
                                mid - 1, x);
 
        // Else the element can
        // only be present in
        // right subarray
        return binarySearch(arr, mid + 1, r, x);
    }
 
    // Returns number of times x
    // occurs in arr[0..n-1]
    static int countOccurrences(int arr[],
                                int n, int x)
    {
        int ind = binarySearch(arr, 0,
                               n - 1, x);
 
        // If element is not present
        if (ind == -1)
            return 0;
 
        // Count elements on left side.
        int count = 1;
        int left = ind - 1;
        while (left >= 0 &&
               arr[left] == x)
        {
            count++;
            left--;
        }
 
        // Count elements
        // on right side.
        int right = ind + 1;
        while (right < n &&
               arr[right] == x)
        {
            count++;
            right++;
        }
 
        return count;
    }
    
//////////////////////////////////////
/////////Method2 /////////////////////
/////////////////////////////////////
    /* if x is present in arr[] then returns
    the count of occurrences of x,
    otherwise returns -1. */
 static int count(int arr[], int x, int n)
 {
   // index of first occurrence of x in arr[0..n-1]   
   int i;
    
   // index of last occurrence of x in arr[0..n-1]
   int j;
       
   /* get the index of first occurrence of x */
   i = first(arr, 0, n-1, x, n);
   
   /* If x doesn't exist in arr[] then return -1 */
   if(i == -1)
     return i;
      
   /* Else get the index of last occurrence of x.
      Note that we are only looking in the
      subarray after first occurrence */ 
   j = last(arr, i, n-1, x, n);    
      
   /* return count */
   return j-i+1;
 }
   
 /* if x is present in arr[] then returns the
    index of FIRST occurrence of x in arr[0..n-1],
    otherwise returns -1 */
 static int first(int arr[], int low, int high, int x, int n)
 {
   if(high >= low)
   {
     /*low + (high - low)/2;*/ 
     int mid = (low + high)/2; 
     if( ( mid == 0 || x > arr[mid-1]) && arr[mid] == x)
       return mid;
     else if(x > arr[mid])
       return first(arr, (mid + 1), high, x, n);
     else
       return first(arr, low, (mid -1), x, n);
   }
   return -1;
 }
   
 /* if x is present in arr[] then returns the
    index of LAST occurrence of x in arr[0..n-1],
    otherwise returns -1 */
 static int last(int arr[], int low, int high, int x, int n)
 {
   if(high >= low)
   {
     /*low + (high - low)/2;*/     
     int mid = (low + high)/2;
     if( ( mid == n-1 || x < arr[mid+1]) && arr[mid] == x )
       return mid;
     else if(x < arr[mid])
       return last(arr, low, (mid -1), x, n);
     else
       return last(arr, (mid + 1), high, x, n);     
   }
   return -1;
 }
   

 
 
    // Driver code
    public static void main(String[] args)
    {
        int arr[] = {1, 2, 2, 2, 2,
                     3, 4, 7, 8, 8};
        int n = arr.length;
        int x = 2;
        System.out.print(countOccurrences(arr, n, x));
        //OR
        //int c = count(arr, x, n);
        //System.out.println(x+" occurs "+c+" times");
    }
}
