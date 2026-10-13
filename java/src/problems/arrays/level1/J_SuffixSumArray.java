package problems.arrays.level1;


/*
 { 10, 4, 16, 20 } 
 Ouput={50,40,36,20}
 * */
public class J_SuffixSumArray {
	// Fills suffix sum array
    static void fillSuffixSum(int arr[], int n,int prefixSum[])
    {
        prefixSum[n-1] = arr[n-1];
        // Adding present element
        // with previous element
        for (int i = n-2; i >= 0; i--) {
        	 prefixSum[i] = prefixSum[i + 1] + arr[i];
        }     
    }
 
    // Driver code
    public static void main(String[] args)
    {
        int arr[] = { 10, 4, 16, 20 };
        int n = arr.length;
        int prefixSum[] = new int[n];
 
        fillSuffixSum(arr, n, prefixSum);
 
        for (int i = 0; i < n; i++)
            System.out.print(prefixSum[i] + " ");
        System.out.println("");
    }
}
