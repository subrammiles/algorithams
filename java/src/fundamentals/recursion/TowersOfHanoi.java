package fundamentals.recursion;

/*Tower of Hanoi*/
import java.util.*;
import java.io.*;
import java.math.*;

class TowersOfHanoi
{

	
	static void towerOfHanoi(int n, char from_rod, char to_rod, char helper_rod)
    {
		 System.out.println("************ n= "+n+"  ****************");
		 System.out.println("Called Recursion  "+"From "+from_rod+" To  "+ to_rod);
        if (n == 1)
        {
            System.out.println("Take disk 1 from rod " +  from_rod + " to rod " + to_rod);
            return;
        }
        towerOfHanoi(n-1, from_rod, helper_rod, to_rod);
        System.out.println("Take disk " + n + " from rod " +  from_rod + " to rod " + to_rod);
        towerOfHanoi(n-1, helper_rod, to_rod, from_rod);
        System.out.println("BreakPoint at End : From "+helper_rod+" To "+ to_rod);
        
    }


public static void main(String args[]) 
{ 
	int n = 3; // Number of disks 
	towerOfHanoi(n, 'A', 'B', 'C'); // A, B and C are names of rods 
} 
}


