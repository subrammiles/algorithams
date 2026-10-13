package algorithms.dynamic_programming;

import java.util.Arrays;

public class FibonacciBottomUp {

	

	
	  public static int fibDPBottomUp (int x) {
		    int fib[] = new int[x + 1];
		    fib[0] = 0;
		    fib[1] = 1;

		    for (int i = 2; i < x + 1; i++) {
		      fib[i] = fib[i - 1] + fib[i - 2];
		    }

		    System.out.println("Array is = " + Arrays.toString(fib));

		    return fib[x];
		  }

		  public static void main(String[] args) {
		    System.out.println("Feb for n=10 is =" + fibDPBottomUp (10));
		  }

}
