package algorithms.sorting;

import java.util.Arrays;

public class PartitionAlgorithm {
	
	// Function to partition the array on the basis of pivot element
		  static int partition(int array[], int low, int high) {
		    
		    // Select the pivot element
		    int pivot = array[high];
		    int i = (low - 1);
		    
		    System.out.println("Partition Logic starts .....");
		    System.out.println("Low ="+low+"  High="+high+"  pivot=array[high]=" +pivot+"   i="+i);

		    // Put the elements smaller than pivot on the left and 
		    // greater than pivot on the right of pivot
		    for (int j = low; j < high; j++) {
		    	 System.out.println("********* j = "+j+"**************************");
		      if (array[j] <= pivot) {
		    	  System.out.println("      array[j]="+array[j]+"  <="+ " pivot="+pivot);
		        i++;
		        int temp = array[i];
		        array[i] = array[j];
		        array[j] = temp;
		        System.out.println("      i="+i);
		        System.out.println("      array = " +Arrays.toString(array));
		      }
		    }
		    int temp = array[i + 1];
		    array[i + 1] = array[high];
		    array[high] = temp;
		    return (i + 1);
		  }
		  
		  // Driver code
		  public static void main(String args[]) {
		    int[] data = { 1, 0, 3, 5, 10, 17, 7,5,8};
		    System.out.println("Array before partition = " +Arrays.toString(data));
		    int pi = partition(data, 0, data.length-1);
		    System.out.println("Array after partition = " +Arrays.toString(data));
		
		  }


}
