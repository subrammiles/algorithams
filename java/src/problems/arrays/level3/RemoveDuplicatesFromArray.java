package problems.arrays.level3;

import java.util.Arrays;

public class RemoveDuplicatesFromArray {
	
	/*Using extra space-temporal array*/
	
	public static int usingTemporalArray(int arr[], int n){  
        if (n==0 || n==1){  
            return n;  
        }  
        int[] temp = new int[n];  
        int j = 0;  
        for (int i=0; i<n-1; i++){  
            if (arr[i] != arr[i+1]){  
                temp[j++] = arr[i];  
            }  
         }  
        temp[j++] = arr[n-1];     
        // Changing original array  
        for (int i=0; i<j; i++){  
            arr[i] = temp[i];  
        }  
        return j;  
    }  
	
	static int usingSeperateIndex(int arr[], int n)
    {
		/*Constant extra space*/
	       if (n==0 || n==1){  
	            return n;  
	        }    
	        int j = 0;//for next element  
	        for (int i=0; i < n-1; i++){  
	            if (arr[i] != arr[i+1]){  
	                arr[j++] = arr[i];  
	            }  
	        }  
	        arr[j++] = arr[n-1];  
	       
      
        System.out.print(Arrays.toString(arr));
        return j;   
        
    }
	
	public static void main(String[] args) {
		test();
	}
	
	static void test() {
		/*Iteration*/
		int[] myArray=new int[5];
		myArray= new  int[]{1,1,2,2,2};
		usingSeperateIndex(myArray,myArray.length);
		
	}


}
