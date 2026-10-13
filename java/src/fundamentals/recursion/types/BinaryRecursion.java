package fundamentals.recursion.types;

public class BinaryRecursion {
	
	
	public static int binaryRecursiveSum( int[] data, int low, int high )
    {  
       if( low>high )
         return 0;
       else if( low == high )
         return data[low];
       else
       {
         int mid = (low+high)/2;
         return binaryRecursiveSum(data, low, mid)+binaryRecursiveSum(data, mid+1, high);
       }
    } 
	
public static void main(String args[]) {
	
	
		
		
	int[] data =  {1,2,3,4,5,6,7,8};
	   
    int result = binaryRecursiveSum(data, 0, data.length-1);

    System.out.println(result); 
		
	}

}
