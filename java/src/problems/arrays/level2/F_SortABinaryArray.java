package problems.arrays.level2;

import java.util.Arrays;

/*
 Sort a binary array using one traversal
 
 Given a binary array, sort it using one traversal and no extra space.
Examples : 
 

Input : 1 0 0 1 0 1 0 1 1 1 1 1 1 0 0 1 1 0 1 0 0 
Output : 0 0 0 0 0 0 0 0 0 1 1 1 1 1 1 1 1 1 1 1 1
Explanation: The output is a sorted array of 0 and 1

Input : 1 0 1 0 1 0 1 0 
Output : 0 0 0 0 1 1 1 1
Explanation: The output is a sorted array of 0 and 1
 * */
public class F_SortABinaryArray {
//////////////////////////////////////
/////////Method1 Linear//////////////
/////////////////////////////////////
	
	void sort(int[] A)
    {
        // count number of 0's
        int zeros = 0;
        for (int value: A)
        {
            if (value == 0) {
                zeros++;
            }
        }
        // put 0's at the beginning
        int k = 0;
        while (zeros-- != 0) {
            A[k++] = 0;
        }
 
        // fill all remaining elements by 1
        while (k < A.length) {
            A[k++] = 1;
        }
    }
	
//////////////////////////////////////
/////////Method2 Linear//////////////
/////////////////////////////////////

	/*
	 Approach: This concept is related to partition of quick sort . 
	 In quick sort’ partition, after one scan, 
	 the left of the array is smallest and right of the array 
	 is the largest of selected pivot element.
	 * */
	static void sortBinaryArray(int a[], int n)
    {
        int j = -1;
        for (int i = 0; i < n; i++) {
 
            // if number is smaller than 1
            // then swap it with j-th number
            if (a[i] < 1) {
                j++;
                int temp = a[j];
                a[j] = a[i];
                a[i] = temp;
            }
        }
    }
	
//////////////////////////////////////
/////////Method3 Linear//////////////
/////////////////////////////////////
	
	static void method3(int[] A)
    {
        // `k` stores index of next available position
        int k = 0;
 
        // do for each element
        for (int i: A)
        {
            // if the current element is zero, put 0 at the next free
            // position in the array
            if (i == 0) {
                A[k++] = 0;
            }
            System.out.print(Arrays.toString(A));
        }
        System.out.print(Arrays.toString(A));
 
        // fill all remaining indices by 1
        for (int i = k; i < A.length; i++) {
            A[k++] = 1;
        }
        System.out.print(Arrays.toString(A));
    }

 
    /* Driver program to test above function */
    public static void main(String[] args)
    {
 
        int a[] = {1,0,1,0,1,0,0,1};
 
        int n = a.length;
 
        //sortBinaryArray(a, n);
        method3(a);
 
    }

}
