package algorithms.greedy;

public class FittingShelvesProblem {
    static void minSpacePreferLarge(int wall, int m, int n)
    {
        // For simplicity, Assuming m is always smaller than n
        // initializing output variables
        int num_m = 0, num_n = 0, min_empty = wall;
 
        // p and q are no of shelves of length m and n
        // rem is the empty space
        int p = wall/m, q = 0, rem=wall%m;
          num_m=p;
          num_n=q;
          min_empty=rem;
        while (wall >= n) {
            q += 1;
            wall = wall - n;
            // place as many shelves of length m
            // in the remaining part
            p = wall / m;
            rem = wall % m;
 
            // update output variablse if curr
            // min_empty <= overall empty
            if (rem <= min_empty) {
                num_m = p;
                num_n = q;
                min_empty = rem;
            }
 
            // place one more shelf of length n
            q += 1;
            wall = wall - n;
        }
        System.out.println(num_m + " " + num_n + " " + min_empty);
    }
 
    // Driver Code
    public static void main(String[] args)
    {
        int wall = 24, m = 3, n = 5;
        minSpacePreferLarge(wall, m, n);
 
        wall = 24;
        m = 4;
        n = 7;
        minSpacePreferLarge(wall, m, n);
    }
}
