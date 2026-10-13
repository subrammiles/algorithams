package fundamentals.recursion;

public class PrintFirstNNaturalNumbers {
	
	static int  printNNatural(int n){
		
		if(n==0) {
			return 0;
		}
		System.out.println(n);
		return printNNatural(n-1);
	}

	public static void main(String[] args) {
		int x=printNNatural(10);

	}

}










