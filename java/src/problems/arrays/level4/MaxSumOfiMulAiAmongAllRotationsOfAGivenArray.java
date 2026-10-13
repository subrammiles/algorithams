package problems.arrays.level4;

/*
 Q. Maximum sum of i*arr[i] among all rotations of a given array
Input: arr[] = {8, 3, 1, 2}
Output: 29
Explanation: Lets look at all the rotations,
{8, 3, 1, 2} = 8*0 + 3*1 + 1*2 + 2*3 = 11
{3, 1, 2, 8} = 3*0 + 1*1 + 2*2 + 8*3 = 29
{1, 2, 8, 3} = 1*0 + 2*1 + 8*2 + 3*3 = 27
{2, 8, 3, 1} = 2*0 + 8*1 + 3*2 + 1*3 = 17

Input: arr[] = {3, 2, 1}
Output: 7
Explanation: Lets look at all the rotations,
{3, 2, 1} = 3*0 + 2*1 + 1*2 = 4
{2, 1, 3} = 2*0 + 1*1 + 3*2 = 7
{1, 3, 2} = 1*0 + 3*1 + 2*2 = 7

 * */
public class MaxSumOfiMulAiAmongAllRotationsOfAGivenArray {

	int maxSum(int arr[], int n) {
		// Initialize result
		int res = Integer.MIN_VALUE;

		// Consider rotation beginning with i for all possible values of i.
		for (int i = 0; i < n; i++) {

			// Initialize sum of current rotation
			int curr_sum = 0;
			// Compute sum of all values. We don't actually rotation the array, but compute
			// sum by finding ndexes when arr[i] is first element
			for (int j = 0; j < n; j++) {
				int index = (i + j) % n;
				curr_sum += j * arr[index];
			}

			// Update result if required
			res = Math.max(res, curr_sum);
		}

		return res;
	}

	int maxSum2(int arr[], int n) {
		// Compute sum of all array elements
		int cum_sum = 0;
		for (int i = 0; i < n; i++)
			cum_sum += arr[i];

		// Compute sum of i*arr[i] for
		// initial configuration.
		int curr_val = 0;
		for (int i = 0; i < n; i++)
			curr_val += i * arr[i];

		// Initialize result
		int res = curr_val;

		// Compute values for other iterations
		for (int i = 1; i < n; i++) {
			// Compute next value using previous
			// value in O(1) time
			int next_val = curr_val - (cum_sum - arr[i - 1]) + arr[i - 1] * (n - 1);

			// Update current value
			curr_val = next_val;

			// Update result if required
			res = Math.max(res, next_val);
		}

		return res;
	}
	
	
	int maxSum3(int arr[], int n)
	{
	    int sum = 0;
	    int i;
	    int pivot = findPivot(arr, n);
	 
	    // difference in pivot and index of
	    // last element of array
	    int diff = n - 1 - pivot;
	    for(i = 0; i < n; i++)
	    {
	        sum= sum + ((i + diff) % n) * arr[i];
	    }
	    return sum;
	}
	 
	// function to find pivot
	int findPivot(int arr[], int n)
	{
	    int i;
	    for(i = 0; i < n; i++)
	    {
	        if(arr[i] > arr[(i + 1) % n])
	            return i;
	    }
	    return 0;
	}


}
