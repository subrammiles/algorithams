package problems.arrays.level2;

public class C_FindDuplicateElementInAnArray {
	
	
	 // Function to find a duplicate element in a limited range array
    public static int findDuplicate(int[] nums)
    {
        int duplicate = -1;
 
        // do for each array element
        for (int i: nums)
        {
            // get the value of the current element
            int val = (i < 0) ? -i : i;
 
            // make element at index `val-1` negative if it is positive
            if (nums[val-1] >= 0) {
                nums[val-1] = -nums[val-1];
            }
            else {
                // if the element is already negative, it is repeated
                duplicate = val;
                break;
            }
        }
 
        // restore the original array before returning
        for (int i = 0; i < nums.length; i++)
        {
            // make negative elements positive
            if (nums[i] < 0) {
                nums[i] = -nums[i];
            }
        }
 
        // return duplicate element
        return duplicate;
    }
    
     static void  findDuplicate2(int arr[], int size)
    {
        int i;
        System.out.println("The repeating elements are : ");
         
        for (i = 0; i < size; i++) {
        	 System.out.println(" i=.......... "+i);
            int j = Math.abs(arr[i]);
            
            System.out.println(" j absolate =.......... "+j);
            
            if (arr[j] >= 0) {
            	System.out.println(j + "is  not reapting");
            	System.out.println(arr[j]);
                arr[j] = -arr[j];
                
            }
            else
            { System.out.print(j + "is reapting");}
        }
    }
     
     static int usingXOR(int[] nums) {
    	 int xor = 0;
    	 
         // take xor of all array elements
         for (int value: nums) {
             xor ^= value;
             System.out.println(xor);
             
         }
  
         // take xor of numbers from 1 to `n-1`
         for (int i = 1; i <= nums.length - 1; i++) {
             xor ^= i;
         }
  
         // same elements will cancel each other as a ^ a = 0,
         // 0 ^ 0 = 0 and a ^ 0 = a
  
         // `xor` will contain the missing number
         return xor;
     }
 
   
 
    public static void main (String[] args)
    {
        // input array contains `n` numbers between 1 and `n-1`
        // with one duplicate, where `n` is the array's length
        int[] nums = { 1, 2, 3, 4, 2 };
 
       // System.out.println("The duplicate element is " + findDuplicate(nums));
        
        
        //Methosd 2
        int arr[] = { 1, 2, 3, 1, 3, 6, 6 };
        int arr_size = arr.length;
 
        findDuplicate2(arr, arr_size);
        
        System.out.println("The duplicate element is " + findDuplicate(nums));
        usingXOR(nums);
        
    }

}
