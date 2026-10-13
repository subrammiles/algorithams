package algorithms.dynamicprogramming;



import java.util.Arrays;

public class BottomUpKnapsack {
	 // Declare the table dynamically
	static int val[] = { 60, 100, 120 };   
    static int wt[] = { 10, 20, 30 };  
    static int W = 50;  
    static int N = val.length;  
    static int max(int a, int b) 
    { 
      return (a > b) ? a : b; 
    }
 

    static int knapSack(int W, int wt[], int val[], int n) {
    	  int i,
    	  w;
    	  int K[][] = new int[n + 1][W + 1];

    	  // Build table K[][] in bottom up manner
    	  for (i = 0; i <= n; i++) {
    	    for (w = 0; w <= W; w++) {
    	      if (i == 0 || w == 0) K[i][w] = 0;
    	      else if (wt[i - 1] <= w) K[i][w] = max(val[i - 1] + K[i - 1][w - wt[i - 1]], K[i - 1][w]);
    	      else K[i][w] = K[i - 1][w];
    	    }
    	  }
    	  
    	  System.out.println("Storage Array = ");
          for(int[] row : K) {
              printRow(row);
          }

    	  return K[n][W];
    	}
   
     
    // Driver Code 
    public static void main(String [] args)
    {       
        System.out.println("Result is " +knapSack(W, wt, val, N)); 
    } 
    
    public static void printRow(int[] row) {
        for (int i : row) {
            System.out.print(i);
            System.out.print("\t");
        }
        System.out.println();
    }

    }

