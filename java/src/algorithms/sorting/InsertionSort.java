package algorithms.sorting;

import java.util.Arrays;

public class InsertionSort {
	 /*Function to sort array using insertion sort*/
		static void  doInsertionsort(int arr[])
	    {
	        for (int i = 1; i < arr.length; ++i) {
	            int key = arr[i];
	            int j = i - 1;
	            System.out.println("key = "+key +" i = "+i + "    j= "+j+"   Array = "+Arrays.toString(arr));
	            /* Move elements of arr[0..i-1], that are
	               greater than key, to one position ahead
	               of their current position */
	            while (j >= 0 && arr[j] > key) {
	            	System.out.println("Entered into while loop  ");
	                arr[j + 1] = arr[j];
	                j = j - 1;
	            }
	            System.out.println("         i = "+i +"    j= "+j+"   Array = "+Arrays.toString(arr));
	            arr[j + 1] = key;
	            System.out.println("*********End of Loop*******");
	        }
	        System.out.println("Sorted Array"+Arrays.toString(arr));
	    }
	 
	    public static void main(String args[])
	    {
	        int arr[] = {9,5,1,4,3};
	        doInsertionsort(arr);
	    }
}
