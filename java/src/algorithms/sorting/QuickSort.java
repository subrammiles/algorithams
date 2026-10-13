package algorithms.sorting;

import java.util.Arrays;

public class QuickSort {
	
	// Function to partition the array on the basis of pivot element
	  int partition(int array[], int low, int high) {
	    
	    // Select the pivot element
	    int pivot = array[high];
	    int i = (low - 1);

	    // Put the elements smaller than pivot on the left and 
	    // greater than pivot on the right of pivot
	    for (int j = low; j < high; j++) {
	      if (array[j] <= pivot) {
	        i++;
	        int temp = array[i];
	        array[i] = array[j];
	        array[j] = temp;
	      }
	    }
	    int temp = array[i + 1];
	    array[i + 1] = array[high];
	    array[high] = temp;
	    return (i + 1);
	  }

	  void quickSort(int array[], int low, int high) {
	    if (low < high) {
	    	System.out.println("***** Low="+low+"    "+"High="+high+"  ******");
	      System.out.println("Array before Partition  =" +Arrays.toString(array));
	      // Select pivot position and put all the elements smaller 
	      // than pivot on left and greater than pivot on right
	      int pi = partition(array, low, high);
	      System.out.println("pi =" +pi);
	      System.out.println("Array after Partition  =" +Arrays.toString(array));
	      
	      // Sort the elements on the left of pivot
	      quickSort(array, low, pi - 1);

	      // Sort the elements on the right of pivot
	      quickSort(array, pi + 1, high);
	    }
	  }

	  // Driver code
	  public static void main(String args[]) {
	    int[] data = { 9, -3, 5, 2, 6, 8, -6,1,3 };
	    int size = data.length;
	    QuickSort qs = new QuickSort();
	    qs.quickSort(data, 0, size - 1);
	    System.out.println("Sorted Array in Ascending Order: ");
	    System.out.println(Arrays.toString(data));
	  }

}
