package algorithms.greedy;


/*
 * You are given an array A of integers, where each element indicates 
 * the time a thing takes for completion. You want to calculate the 
 * maximum number of things that you can do in the limited time that you have.*/
import java.util.Arrays;
public class MaxTasksInMinTime {

	public static void main(String[] a) {
		int[] A= {5,3,4,1,2};
		int T=6;
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
