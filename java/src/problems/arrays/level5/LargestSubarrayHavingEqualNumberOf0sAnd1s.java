package problems.arrays.level5;

import java.util.HashMap;
import java.util.Map;

/*
 Find the largest subarray having an equal number of 0’s and 1’s
 Input:  { 0, 0, 1, 0, 1, 0, 0 }
 Output: Largest subarray is { 0, 1, 0, 1 } or { 1, 0, 1, 0 }
 * */
public class LargestSubarrayHavingEqualNumberOf0sAnd1s {
	
	/* Brute force */
	/*
	 * If you replace 0's with -1 then total sum must be zero for equal number of
	 * 1's and zeros
	 */
	static int method1(int arr[], int n) {
		int sum = 0;
        int maxsize = -1, startindex = 0;
        int endindex = 0;
        for (int i = 0; i < n - 1; i++) {
            sum = (arr[i] == 0) ? -1 : 1;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] == 0)
                    sum += -1;
                else
                    sum += 1;
                if (sum == 0 && maxsize < j - i + 1) {
                    maxsize = j - i + 1;
                    startindex = i;
                }
            }
        }
        endindex = startindex + maxsize - 1;
        if (maxsize == -1)
            System.out.println("No such subarray");
        else
            System.out.println(startindex + " to " + endindex);
 
        return maxsize;
	}
	

    static int method2(int arr[], int n)
    {
        HashMap<Integer, Integer> hM
            = new HashMap<Integer, Integer>();
 
        int sum = 0;
        int max_len = 0;
        int ending_index = -1;
        int start_index = 0;
 
        for (int i = 0; i < n; i++) {
            arr[i] = (arr[i] == 0) ? -1 : 1;
        }
        System.out.println(arr.toString());
        
 
        for (int i = 0; i < n; i++) {

            sum += arr[i];
 
            if (sum == 0) {
                max_len = i + 1;
                ending_index = i;
            }
 
            if (hM.containsKey(sum)) {
                if (max_len < i - hM.get(sum)) {
                    max_len = i - hM.get(sum);
                    ending_index = i;
                }
            }
            else 
                hM.put(sum, i);
        }
 
        int start = ending_index - max_len + 1;
        System.out.println(start + " to " + ending_index);
 
        return max_len;
    }
    
    static int method3(int[] arr, int n) {
        Map < Integer, Integer > m = new HashMap < > ();
        int mx = 0, cnt = 0;
        for (int i = 0; i < arr.length; i++) {
            cnt += (arr[i] == 1) ? 1 : -1;
            if (cnt == 0)
                mx = Math.max(mx, i + 1);
            if (m.containsKey(cnt)) {
                mx = Math.max(mx, i - m.get(cnt));
            } else
                m.put(cnt, i);
        }
        System.out.println(mx + " to ");
        return mx;
    }

	
	

	public static void main(String[] args) {
		int arr[] = {1,0,1,1,0};
		int size = arr.length;
		//method1(arr, size);
		method3(arr,size);
	}
}
