package fundamentals.recursion.types;

public class TreeRecursion {
	
	static int fib(int n)
	{
	   // Base Case
	   if (n <= 1) {
		   return n;
	   }
	   int x=fib(n-1);
	   int y=fib(n-2);
	   return  x+y ;
	}
	
	static void fun(int n)
	{
	    if (n > 0) {
	         System.out.println(" "+ n);

	        // Calling once
	        fun(n - 1);
	 
	        // Calling twice
	        fun(n - 1);
	    }
	}


 
	public static void main(String[] args) {
		fib(5);
		fun(3);
	}

}
