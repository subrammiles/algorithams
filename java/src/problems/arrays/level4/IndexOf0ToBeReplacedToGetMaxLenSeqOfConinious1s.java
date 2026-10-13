package problems.arrays.level4;

/*
 Find the index of 0 to be replaced to get the maximum
  length sequence of continuous ones
  { 0, 0, 1, 0, 1, 1, 1, 0, 1, 1 }. We need to replace index 7 
  {1,1,1,1,0} replace 4th index
 * */
public class IndexOf0ToBeReplacedToGetMaxLenSeqOfConinious1s {

	/* BF method n2 time complex approch */
	static int method1(int[] a) {

		return -1;
	}

	static int method2(int[] A) {
		//Example 
		// {1,1,1,1,0}
		int max_count = 0; // stores maximum number of 1's (including 0)
		int max_index = -1; // stores index of 0 to be replaced

		int prev_zero_index = -1; // stores index of previous zero
		int count = 0; // stores current count of zeros

		// consider each index `i` in the array
		for (int i = 0; i < A.length; i++) {
			// if the current element is 1
			if (A[i] == 1) {
				count++;
			}
			// if the current element is 0
			else {
				// reset count to 1 + number of ones to the left of current 0
				count = i - prev_zero_index;

				// update `prev_zero_index` to the current index
				prev_zero_index = i;
			}

			// update maximum count and index of 0 to be replaced if required
			if (count > max_count) {
				max_count = count;
				max_index = prev_zero_index;
			}
		}

		// return index of 0 to be replaced or -1 if the array contains all 1's
		return max_index;
	}
	
	private int  method3(int[] A) {
        int left = 0;       // represents the window's starting index
        int count = 0;      // stores the total number of zeros in the current window
        int max_count = 0;  // stores the maximum number of 1's (including 0)
 
        int max_index = -1;         // stores the index of 0 to be replaced
        int prev_zero_index = -1;   // stores the index of previous zero
 
        // maintain a window `[left…i]` containing at most one zero
        for (int i = 0; i < A.length; i++)
        {
            // if the current element is 0, update `prev_zero_index` and
            // increase zeros count in the current window by 1
            if (A[i] == 0)
            {
                prev_zero_index = i;
                count++;
            }
 
            // the window becomes unstable if the total number of zeros in it becomes 2
            if (count == 2)
            {
                // remove elements from the window's left side till
                // we found a zero
                while (A[left] != 0) {
                    left++;
                }
 
                // remove the leftmost 0 so that window becomes stable again
                left++;
 
                // decrement count as 0 is removed
                count = 1;
            }
 
            // when we reach here, window `[left…i]` contains only at most one zero; update the maximum count and index of 0 to be replaced if required
 
            if (i - left + 1 > max_count)
            {
                max_count = i - left + 1;
                max_index = prev_zero_index;
            }
        }
 
        // return index of 0 to be replaced or -1 if the array contains all 1's
        return max_index;

	}

	public static void main(String[] args) {
		int[] A = { 0, 0, 1, 0, 1, 1, 1, 0, 1, 1 };
		int[] B = { 1, 1, 1, 1, 0, 0 };

		int index = method2(B);

		if (index != -1) {
			System.out.print("Index to be replaced is " + index);
		} else {
			System.out.print("Invalid input");
		}
	}

}
