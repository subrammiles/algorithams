package problems.arrays.level1;

import java.util.Arrays;

/*Reverse a given array*/
public class C_ReverseAnArray {
	
	static int[] reverse2(int[] array,int start,int end) {
		if(start<end) {
			return array ;
		}else {
			int temp=array[end];
			array[end]=array[start];
			array[start]=temp;
			return reverse2(array,start+1,end-1);
		}
	}
	
	static int[] reverseAnArray(int[] array) {
		/*Two pointer technique to reverse an array */
		int h=array.length-1;
		for(int i=0;i<=array.length/2;i++) {
			int temp=array[h];
			array[h]=array[i];
			array[i]=temp;
			h=h-1;
		}
		System.out.println(Arrays.toString(array));
		return array;
	}
	
	public static void main(String[] args) {
		test();
	}
	
	static void test() {
		/*Iteration*/
		int[] myArray=new int[5];
		myArray= new  int[]{1,2,3,4,5};
		reverseAnArray(myArray);
		/*Recursion*/
		int[] reversedArray=reverse2(myArray,0,myArray.length-1);
		System.out.println(Arrays.toString(reversedArray));
	}
}
