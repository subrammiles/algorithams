package algorithms.greedy;


import java.util.Arrays;
public class BeingGreedyForWater {

	public static void main(String[] a) {
		int[] A= {8, 5, 4 ,3 ,2};
		int T=10;
		 int numberOfThings = 0, currentTime = 0;
		Arrays.sort(A);
		System.out.print(Arrays.toString(A));
		
		for(int i=0;i<A.length;i++) {
			currentTime=currentTime+A[i];
			if(currentTime>=T) {
				break;
			}
			numberOfThings++;	
		}
		System.out.print("numberOfThings"+numberOfThings);
		System.out.print("currentTime"+currentTime);	
	}
}