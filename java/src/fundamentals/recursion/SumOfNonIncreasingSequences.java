package fundamentals.recursion;
import java.util.Arrays;

//P:Given a number x, print all possible non-increasing sequences with sum equals to x.

//Input: x = 3
//Output: 1 1 1
//        2 1
//        3
//

//Input: x = 4
//Output: 1 1 1 1
//        2 1 1
//        2 2
//        3 1
//        4

public class SumOfNonIncreasingSequences {
	
	// Utility function to print array
    // arr[0..n-1]
	
	static int x=5;
    static void printArr(int arr[], int n)
    {
        for (int i = 0; i < n; i++)
            System.out.printf("%d ", arr[i]);
             
        System.out.println("");
    }
     
    static void generateUtil( int arr[],
                     int curr_sum, int index)
    {
    	System.out.println(Arrays.toString(arr)+" "+curr_sum+" "+index);
    	
    	
    	int num = 1;
    	
        if (curr_sum == x)
        {
            printArr(arr, index);
            return;
        }
         
        while (num <= x - curr_sum &&
                             (index == 0 ||
                     num <= arr[index - 1]))
        {
             
           
            arr[index] = num;
         
            
            generateUtil(arr, curr_sum+num,
            		index + 1);

            num++;
            System.out.println("");
        }
    }
     
  
    public static void main(String[] args)
    {     
    	int arr[] = new int [x];
        generateUtil(arr, 0, 0);
    }

	

}
