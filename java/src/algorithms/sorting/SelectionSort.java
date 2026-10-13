package algorithms.sorting;

import java.util.Arrays;

class SelectionSort 
{ 
    void sort(int arr[]) 
    { 
        // One by one move boundary of unsorted subarray 
        for (int i = 0; i < arr.length-1; i++) 
        {   System.out.println("***** "+i+"  pass (i="+i+") ****");
            System.out.println("=>Array before comparison : "+Arrays.toString(arr)); 
            // Find the minimum element in unsorted array 
            int min_idx = i; 
            
            for (int j = i+1; j < arr.length; j++) {
            	 System.out.println("Inner Loop j = " +j+" :");
            	 System.out.println("        min_idx ="+ min_idx);
            	  if (arr[j] < arr[min_idx]) {
            		  System.out.println("        "+"arr[j]="+arr[j]+" < "+ "arr[min_idx]="+arr[min_idx]);
            		  min_idx = j; 
            		  System.out.println("        New min_idx ="+ min_idx);
            		 
            	  }
                     
            	
            }
            // Swap the found minimum element with the first 
            // element 
            int temp = arr[min_idx]; 
            arr[min_idx] = arr[i]; 
            arr[i] = temp; 
            System.out.println("=>Array after comparison : "+Arrays.toString(arr)); 
            System.out.println(""); 
        } 
    } 
  
    public static void main(String args[]) 
    { 
        SelectionSort ob = new SelectionSort(); 
        int arr[] = {20,12,10,15,2}; 
        ob.sort(arr); 
        System.out.println("Final Sorted array ="+ Arrays.toString(arr)); 
       
    } 
} 
