package problems.arrays.level4;

import java.util.HashSet;
import java.util.Set;

/*
  nums[] = { 5, 6, 8, 2, 4, 6, 9 }
k = 4

 * */
public class FindDuplicatesWithInARangeK {

	
	static boolean hasDuplicate(int[] nums, int k)
    {
        // create an empty set to store elements within range `k`
        Set<Integer> window = new HashSet<>();
        // traverse the array
        for (int i = 0; i < nums.length; i++)
        {
            // if the current element already exists in the window,
            // then it repeats within range of `k`
            if (window.contains(nums[i])) {
                return true;
            }
 
            // add the current element to the window
            window.add(nums[i]);
 
            // remove the element at k'th range from the current element
            if (i >= k) {
                window.remove(nums[i - k]);
            }
        }
 
        // we reach here when no element repeats within range `k`
        return false;
    }
	
	public static void main(String[] a) {
		
		hasDuplicate(new int[] {5,6,8,2,4,7,9},4);
	}

}
