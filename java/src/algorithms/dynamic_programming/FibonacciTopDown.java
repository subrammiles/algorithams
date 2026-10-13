package algorithms.dynamic_programming;

import java.util.Arrays;

public class FibonacciTopDown {
	  static int n = 5;
	  static int[] myArray = new int[n + 1];
	 
	 public static int fibTopDown(int n) {
	    System.out.println("Array when n=" + n + " is =" + Arrays.toString(myArray));
	  
	  if (n == 0) return 0;
	   
	  if (n == 1) return 1;
	  
	if (myArray[n] != 0) {
	      /*In value is set,then myArray[n] value neber be 0.So return the value*/
	      return myArray[n];
	    } else {
	      myArray[n] = fibTopDown(n - 1) + fibTopDown(n - 2);
	      return myArray[n];
	    }
	  }
	  
	public static void main(String[] args) {
	    System.out.println("Result Array before function call=" + Arrays.toString(myArray));
	    System.out.println("Fib value is = " + fibTopDown(n));
	    System.out.println("Result Array before function call=" + Arrays.toString(myArray));
	  }

}
