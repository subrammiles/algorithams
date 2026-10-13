package fundamentals.recursion;

public class SumOfAllArrayElements {
	public static void main(String[] args) {
		
		int[] myArray=new int[5];
		myArray= new  int[]{1,2,3,4,5};
		
		int sum =recursion(myArray,0,myArray.length-1);
		System.out.println(sum);
	}
	
	
	
	private  static int recursion(int[] myArray,int sum,int index) {
		if(index < 0) {
			return sum;
		}else {
			 return recursion(myArray,sum+myArray[index],index-1);
		}
	}
}
