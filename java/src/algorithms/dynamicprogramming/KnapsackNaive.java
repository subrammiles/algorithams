package algorithms.dynamicprogramming;



import java.util.Arrays;

public class KnapsackNaive {
	/* A Naive recursive implementation
	of 0-1 Knapsack problem */
	// A utility function that returns
    // maximum of two integers
    static int max(int a, int b) 
    { 
      return (a > b) ? a : b; 
    }
 
    // Returns the maximum value that
    // can be put in a knapsack of
    // capacity W
    static int knapSack(int C, int wt[], int val[], int n)
    {
    	 System.out.println(" When n = "+ n+" val="+Arrays.toString(val)+" wt="+Arrays.toString(wt) +" C="+C);
        if (n == 0 || C == 0) {
             /* Base Case*/
            //When n=0,then we stops
             return 0;
        }
        else if (wt[n - 1] > C) {
            /*If Weight of current element is greater than the Max Capacity*/
            return knapSack(C, wt, val, n - 1);
            /*We are calling the next element*/
        }
        else {
              /*If we taken the item then we save that in temp1*/
              /*As array elements index starts with 0,and n is the length of the element,(n-1) represents the current element*/
              int temp1 = (val[n - 1]
                     + knapSack((C - wt[n - 1]), wt,
                             val, n - 1));
              /*IF we  not taken the item then we save that in temp2*/ 
              int temp2=knapSack(C, wt, val, n - 1);
             return max(temp1,temp2);
        }
    }
 
    // Driver code
    public static void main(String args[])
    {
        int val[] = new int[] { 1, 6, 10,16 };
        int wt[] = new int[] { 1, 2, 3,5 };
        int W = 7;
        int n = val.length;
        System.out.println("Result Value = "+knapSack(W, wt, val, n));
    }
	
}

