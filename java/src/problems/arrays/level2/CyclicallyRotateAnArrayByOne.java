package problems.arrays.level2;

/*
 Given an array, cyclically rotate the array clockwise by one
Input:  arr[] = {1, 2, 3, 4, 5}
Output: arr[] = {5, 1, 2, 3, 4}

 * */
public class CyclicallyRotateAnArrayByOne {
	static int arr[] = new int[]{1, 2, 3, 4, 5};
    
    // Method for rotation
    static void rotate()
    {
       int x = arr[arr.length-1], i;
       for (i = arr.length-1; i > 0; i--)
          arr[i] = arr[i-1];
       arr[0] = x;
    }
    
    void rotate2()
    {
       int i = 0, j = arr.length - 1;
       while(i != j)
       {
         int temp = arr[i];
         arr[i] = arr[j];
         arr[j] = temp;
         i++;
       }
    }


}
