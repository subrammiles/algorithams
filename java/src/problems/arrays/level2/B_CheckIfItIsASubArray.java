package problems.arrays.level2;

public class B_CheckIfItIsASubArray {
	
	
//////////////////////////////////////
/////////Method1 Linear//////////////
/////////////////////////////////////
	
	private static boolean isSubArray(int[] parentArray,int[] a2) {
		int l1=parentArray.length;
		int l2=a2.length;
		int i=0,j=0;
		if(l2>l1) {
			return false;
		}
		while(i<l1 &&j<l2) {
			if(parentArray[i]==a2[j]) {
				i++;
				j++;
                // If array a2 is completely traversed
				if(j==l2) {
					return true;
				}
			}else {
				
                i = i - j + 1;
                j = 0;

			}
		}
		return false;
	}
	

	public static void main(String[] args) {
		boolean x1=isSubArray(new int[]{3, 0, 5,  0, 1, 2,3,0,5,1},new int[] {3,0,5,2});
		boolean x2=isSubArray(new int[]{1, 2, 3, 4, 5},new int[] {2,5,6});
		System.out.println(x1);
	}

}




