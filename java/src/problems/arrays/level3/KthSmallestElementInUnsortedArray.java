package problems.arrays.level3;

import java.util.Arrays;

public class KthSmallestElementInUnsortedArray {
	int kthSmallest(Integer[] arr, int k)
    {
        // Sort the given array
        Arrays.sort(arr);
        // Return k'th element in
        // the sorted array
        return arr[k - 1];
    }

}
