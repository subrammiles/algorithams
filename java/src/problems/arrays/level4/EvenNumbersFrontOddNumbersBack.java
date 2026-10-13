package problems.arrays.level4;

import java.util.Arrays;

public class EvenNumbersFrontOddNumbersBack {
	
	static private void segregateEvenOdd(int[] array) {
		int i=-1;
		for(int j=0;j<array.length;j++) {
			if(array[j]%2==0) {
			i=i+1;
			swapArray(array,j,i);
			}	
		}
		System.out.println(Arrays.toString(array));
	}
	
	static void swapArray(int[] array,int i,int j) {
		int temp=array[i];
		array[i]=j;
		array[j]=temp;
	}
	
	

	public static void main(String[] args) {
		
		int[] a = new int[]{1,2,3,4,5,6,7,8,9,10};
		segregateEvenOdd(a);
		
	}

}
