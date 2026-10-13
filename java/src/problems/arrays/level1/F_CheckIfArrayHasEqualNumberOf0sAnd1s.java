package problems.arrays.level1;


/*
 { 1, 0, 0, 1, 0, 1 }
 Number of 1's=3
 Number of 0's=3
 * */
public class F_CheckIfArrayHasEqualNumberOf0sAnd1s {
	
	static boolean method1(int[] a,int n) {
		/*Replace all 0s with -1 then total sum should be zero */
		int sum=0;
		for(int i=0;i<n;i++) {
			if(a[i]==0) {
				a[i]=-1;
			}
			sum=sum+a[i];
		}
		 return sum==0 ?  true:  false;
	}

	
	public static void main(String[] args)
    {
        int arr[] = { 1, 0, 0, 1, 0, 1 };
        int size = arr.length;
        boolean r=method1(arr, size);
        System.out.println(r); 
    }
}
