package fundamentals.recursion.types;

public class Nested {
	
	static int fun(int n)
	{
	    if (n > 100)
	        return n - 10;
	 
	    /* A recursive function passing parameter as a recursive call or recursion inside the recursion */
	    return fun(fun(n + 11));
	}


	public static void main(String[] args) {
		
		int  r = fun(95);
		   System.out.print("  "+ r);

		// TODO Auto-generated method stub

	}

}
