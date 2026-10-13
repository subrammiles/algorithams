package fundamentals.recursion;

public class SumOfAllElementsInTheSet {
	
    static void sum(int[] arr, int l, int r, int sum)
    {
        if (l > r) {
            System.out.print(sum + " ");
            return;
        }  
        sum(arr, l + 1, r, sum + arr[l]);
    }
 
    // Driver code
    public static void main(String[] args)
    {
        int[] arr = { 5, 4, 3 };
        int n = arr.length;
 
        sum(arr, 0, n - 1, 0);
    }

}
