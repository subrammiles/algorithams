package algorithms.sorting;

import java.util.Arrays;

public class BubbleSort {
    static void bubbleSort(int arr[]) 
    { 
        int i, j, temp; 
        boolean swapped=false; 
     /* Run loops two times: one for walking throught the array
         and the other for comparison*/
        for (i = 0; i < (arr.length - 1); i++)  
        {    System.out.println("*********** "+i+"th pass (i="+i+") **********");
            swapped = false; 
            System.out.println("=>Array before comparison : "+Arrays.toString(arr)); 
            for (j = 0; j < (arr.length - i - 1); j++)  
            {   System.out.println("Inner Loop j = " +j+" :");
                if (arr[j] > arr[j + 1])  
                { System.out.println("        "+"arr[j]="+arr[j]+">"+ "arr[j+1]="+arr[j+1]);
                    // swap arr[j] and arr[j+1] 
                    temp = arr[j]; 
                    arr[j] = arr[j + 1]; 
                    arr[j + 1] = temp; 
                    System.out.println("     Array after swap " +Arrays.toString(arr)); 
                    swapped = true; 
                } 
            } 
            
            System.out.println("<=Array after comparison : "+Arrays.toString(arr)); 
            System.out.println(""); 
    /*IF no two elements were  swapped by inner loop, then break */
            if (swapped == false) 
                break; 
        } 
    } 
    public static void main(String args[]) 
    { 
        int arr[] = { -2,45, 0,11,-9  }; 
        bubbleSort(arr); 
        System.out.println("Final Sorted array: "+Arrays.toString(arr)); 
    }

}
