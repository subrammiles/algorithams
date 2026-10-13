package algorithms.divide_and_conquer;


import java.util.*;
import java.lang.*;
import static java.lang.Math.min;
import static java.lang.StrictMath.abs;

public class ClosestPair {

    private static Random randomGenerator;  // for random numbers

    public static class Point implements Comparable<Point> {  

        public long x, y;

        // Constructor
        public Point(long x, long y) {
            this.x = x;
            this.y = y;
        }

        public int compareTo(Point p) {
            // compare this and p and there are three results: >0, ==0, or <0
            if (this.x == p.x) {
                    if (this.y == p.y)
                        return 0;
                    else 
                        return (this.y > p.y)? 1 : -1; 
                }
            else
                    return (this.x > p.x)? 1 : -1;
        }

        public String toString() {
            return " ("+Long.toString(this.x)+","+Long.toString(this.y)+")";
        }

        public double distance(Point p) {
            long dx = (this.x - p.x);
            long dy = (this.y - p.y);
            return Math.sqrt(dx*dx + dy*dy);
        }
    }

    public static Point[] plane;        

        public static Point[] T;

        public static Point[] Y;

    public static int N;   // number of points in the plane

    public static void main(String[] args) {

        // Read in the Size of a maze
        Scanner scan = new Scanner(System.in);         
        try {        
            System.out.println("How many points in your plane? ");
            N = scan.nextInt();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        scan.close();

        // Create plane of N points.
        plane = new Point[N];
                Y = new Point[N];
                T = new Point[N];
        randomGenerator = new Random();

        for (int i = 0; i < N; ++i) { 
            long x = randomGenerator.nextInt(N<<6);
            long y = randomGenerator.nextInt(N<<6);
            plane[i] = new Point(x, y);
        }
        Arrays.sort(plane); // sort points according to compareTo.
        for (int i = 1; i < N; ++i)  // make all x's distinct.
            if (plane[i-1].x >= plane[i].x) plane[i].x = plane[i-1].x + 1;  

                    //for (int i = 1; i < N; i++)
                      //      if (plane[i-1].y >= plane[i].y) plane[i].y = plane[i-1].y + 1;
                          //  
                            //    
        System.out.println(N + " points are randomly created.");        
        System.out.println("The first two points are"+plane[0]+" and"+plane[1]);
        System.out.println("The distance of the first two points is "+plane[0].distance(plane[1]));


                long start = System.currentTimeMillis();
        // Compute the minimal distance of any pair of points by exhaustive search.
        double min1 = minDisSimple();
                long end = System.currentTimeMillis();
        System.out.println("The distance of the two closest points by minDisSimple is "+min1);
                System.out.println("The running time for minDisSimple is "+(end-start)+" mms");
        // Compute the minimal distance of any pair of points by divide-and-conquer
        long start1 = System.currentTimeMillis();
                double min2 = minDisDivideConquer(0, N-1);
                long end1 = System.currentTimeMillis();

        System.out.println("The distance of the two closest points by misDisDivideConquer is "+min2);
                System.out.println("The running time for misDisDivideConquer is "+(end1-start1)+" mms");

        }      

    static double minDisSimple() {
        // A straightforward method for computing the distance 
        // of the two closest points in plane[0..N-1].

        // to be completed
            double midDis = Double.POSITIVE_INFINITY;
        for (int i = 0; i < N - 1; i++) {
                for (int j = i + 1; j < N; j++) {
                   if (plane[i].distance(plane[j]) < midDis){
                       midDis = plane[i].distance(plane[j]);
                   }            
                }
            }
        return midDis;

        }


        static void exchange(int i, int j) {
        Point x = plane[i];
        plane[i] = plane[j];
        plane[j] = x;
        }

    static double minDisDivideConquer(int low, int high) {
            // Initialize necessary values
            double minIntermediate;
            double minmin;
            double minDis;

        if (high == low+1) { // two points
                if (plane[low].y > plane[high].y) exchange(low, high);
                return plane[low].distance(plane[high]);
        } 
            else if (high == low+2) { // three points
            // sort these points by y-coordinate
            if (plane[low].y > plane[high].y) exchange(low, high);
            if (plane[low].y > plane[low+1].y) exchange(low, low+1);
            else if (plane[low+1].y > plane[high].y) exchange(low+1, high);
            // compute pairwise distances
            double d1 = plane[low].distance(plane[high]);
            double d2 = plane[low].distance(plane[low+1]);
            double d3 = plane[low+1].distance(plane[high]);
            return ((d1 < d2)? ((d1 < d3)? d1 : d3) : (d2 < d3)? d2 : d3);  // return min(d1, d2, d3)
        } else {  // 4 or more points: Divide and conquer
                int mid = (high + low)/2;
                double lowerPartMin = minDisDivideConquer(low,mid);
                double upperPartMin = minDisDivideConquer(mid+1,high);
                minIntermediate = min(lowerPartMin, upperPartMin);
                int k = 0;
                double x0 = plane[mid].x;
                for(int i = 1; i < N; i++){
                    if(abs(plane[i].x-x0) <= minIntermediate){
                        k++;
                        T[k] = plane[i];
                    }
                }
                minmin = 2 * minIntermediate;
                for (int i = 1; i < k-1; i++){
                    for(int j = i + 1; j < min(i+7,k);j++){
                        double distance0 = abs(T[i].distance(T[j]));
                        if(distance0 < minmin){
                            minmin = distance0;
                        }
                    }
                }
                minDis = min(minmin, minIntermediate);
            }
            return minDis;
        }
    }
