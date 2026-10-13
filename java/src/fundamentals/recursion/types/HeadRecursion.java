package fundamentals.recursion.types;

public class HeadRecursion {
	
	/*Program to print n natural numbers*/
	static void ordinaryRecursion(int n) {
		System.out.println(n);
		if(n==1) {
			return;
		}else {
			 ordinaryRecursion(n-1);
		}
		
	}
	
	static void headRecursion(int n) {
		//To print 1 to 5
		if(n>0) 
		 {
			headRecursion(n-1);
			 System.out.println(n);
		}	
	}
	
	static void headRecursion2(int n,int max) {
		//To print 5 to 1
		if(n>0) 
		 {
			headRecursion2(n-1,max);
			
			System.out.println(max+1-n);
		}	
	}
	
	static void tailRecursion(int n) {
		if(n==1) {
			return;
		}else {
			System.out.println(n);
			 ordinaryRecursion(n-1);
		}
		
	}

	public static void main(String[] args) {
		System.out.println("Ordinarry Recursion");
		ordinaryRecursion(5);
		System.out.println("head Recursion1");
		headRecursion(5);
		System.out.println("head Recursion Dec");
		headRecursion2(5,5);
		System.out.println("Tail Recursion");
		tailRecursion(5);
	}

}
