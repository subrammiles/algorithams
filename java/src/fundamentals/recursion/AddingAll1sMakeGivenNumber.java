package fundamentals.recursion;

import java.util.Arrays;

public class AddingAll1sMakeGivenNumber {
	static int x=5;
	
	 static  void test(int arr[], int curr_sum, int index) {
	    	
	    	if(curr_sum==x) {
	    		return;
	    	}
	    	int k=1;
	    	arr[index]=k;
	    	curr_sum=curr_sum+k;
	    	index=index+1;
	    	test(arr,curr_sum,index);
	    	
	    	
	    }

	public static void main(String[] args) {
		int arr[] = new int [x];
		test(arr, 0, 0);
		System.out.println(Arrays.toString(arr));

	}

}
