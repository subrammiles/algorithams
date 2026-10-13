package fundamentals.recursion.types;

import java.util.Arrays;

public class TailRecursion {
	
	/*Factorial of a number*/
	static int tradationalFactorial(int number) {
		if(number==0) {
			return 1;
		}else {
			return number * tradationalFactorial(number-1);
		}
	}
	
	static int  tailFactorial(int number,int factorialValue) {
		if(number==0) {
			return 1;
		}else {
			return tailFactorial(number-1,factorialValue*number);
		}
	}
	
	/*Sum of n natural numbers*/
	static int recsum(int x) {
	    if (x == 1) {
	        return x;
	    } else {
	        return x + recsum(x - 1);
	    }
	}
	
	static int tailrecsum(int x, int running_total) {
	    if (x == 0) {
	        return running_total;
	    } else {
	        return tailrecsum(x - 1, running_total + x);
	    }
	}
	
	/*Reverse an array*/
	
	static void reverseanArrayH() {
		int[] myArray=new int[5];
		myArray= new  int[]{1,2,3,4,5};
		int[] reversedArray=reverseAnArray(myArray,0,myArray.length-1);
		System.out.println(Arrays.toString(reversedArray));
	}
	
	static int[] reverseAnArray(int[] array,int start,int end) {
		/*Recursion technique to reverse an array */
		if(start<end) {
			return array ;
		}else {
			int temp=array[end];
			array[end]=array[start];
			array[start]=temp;
			return reverseAnArray(array,start+1,end-1);
		}
	}


	public static void main(String args[]) {
		
		int x=tailFactorial(5,5);
		int y=tailrecsum(5,0);
		System.out.println(y);
		
	}
	

}
