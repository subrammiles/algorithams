package problems.arrays.level1;


/*
 Given an array of integers, print a sum triangle from 
 it such that the first level has all array elements. 
 From then, at each level number of elements is one less than 
 the previous level and elements at the level is be the Sum of
  consecutive two elements in the previous level. 
           [48]
         [20, 28] 
        [8, 12, 16] 
        [3, 5, 7, 9] 
      [1, 2, 3, 4, 5] 
 
Exp:

 [48]
        [20, 28] -->(20 + 28 = 48)
        [8, 12, 16] -->(8 + 12 = 20, 12 + 16 = 28)
        [3, 5, 7, 9] -->(3 + 5 = 8, 5 + 7 = 12, 7 + 9 = 16)
        [1, 2, 3, 4, 5] -->(1 + 2 = 3, 2 + 3 = 5, 3 + 4 = 7, 4 + 5 = 9)
 
 * */
import java.util.Arrays;

/*
 Given an array of integers, print a sum triangle 
  from it such that the first level has all array elements. 
  From then, at each level number of elements is one less than the
   previous level and elements at the level is be the Sum of 
   consecutive two elements in the previous level.  
Input : A = {1, 2, 3, 4, 5}
Output : [48]
         [20, 28] 
         [8, 12, 16] 
         [3, 5, 7, 9] 
         [1, 2, 3, 4, 5] 

Explanation :
Here,   [48]
        [20, 28] -->(20 + 28 = 48)
        [8, 12, 16] -->(8 + 12 = 20, 12 + 16 = 28)
        [3, 5, 7, 9] -->(3 + 5 = 8, 5 + 7 = 12, 7 + 9 = 16)
        [1, 2, 3, 4, 5] -->(1 + 2 = 3, 2 + 3 = 5, 3 + 4 = 7, 4 + 5 = 9)  
 
 * */

public class H_SumTriangleFromAnArray {
	
	public static void main(String[] args) {
		test();
	}
	
	static void test() {
		/*Iteration*/
		int[] myArray=new int[5];
		myArray= new  int[]{1,2,3,4,5};
		printSumTraingle(myArray);
		
	}
	
	static void printSumTraingle(int[] A) {
		

        // Base case
        if (A.length < 1)
            return;
   
        // Creating new array which contains the
        // Sum of consecutive elements in
        // the array passes as parameter.
        int[] temp = new int[A.length - 1];
        for (int i = 0; i < A.length - 1; i++)
        {
            int x = A[i] + A[i + 1];
            temp[i] = x;
        }
   
        // Make a recursive call and pass
        // the newly created array
        printSumTraingle(temp);
   
        // Print current array in the end so
        // that smaller arrays are printed first
        System.out.println(Arrays.toString(A));
	}
}
