package datastructures.disjointset;

//Java Program for union-find algorithm to detect cycle in a graph
import java.util.*;
import java.lang.*;
import java.io.*;

class DisjointSetDetectCycle
{
 int V, E;    // V-> no. of vertices & E->no.of edges
 Edge edge[]; // /collection of all edges

 class Edge
 {
     int src, dest;
 };

 // Creates a graph with V vertices and E edges
 DisjointSetDetectCycle(int v,int e)
 {
     V = v;
     E = e;
     edge = new Edge[E];
     for (int i=0; i<e; ++i)
         edge[i] = new Edge();
 }

 // A utility function to find the subset of an element i
 int find(int parent[], int i)
 {
     if (parent[i] == -1)
         return i;
     return find(parent, parent[i]);
 }

 // A utility function to do union of two subsets
 void Union(int parent[], int x, int y)
 {
     parent[x] = y;
 }
 



 // The main function to check whether a given graph
 // contains cycle or not
 static int isCycle( DisjointSetDetectCycle graph)
 {
     // Allocate memory for creating V subsets
     int parent[] = new int[graph.V];

     // Initialize all subsets as single element sets.
     for (int i=0; i<graph.V; ++i) {
    	 parent[i]=-1;
    	 /*Initially vertice's parent is itself.So parent[i] value is -1*/
     }
     
     System.out.println("INdex =                        " +"0   1    2");
     
     System.out.println("parrent array Initial Value  =" +Arrays.toString(parent));
     
     
        

     // Iterate through all edges of graph, find subset of both
     // vertices of every edge, if both subsets are same, then
     // there is cycle in graph.
     for (int i = 0; i < graph.E; ++i)
     {
    	 
         int x = graph.find(parent, graph.edge[i].src);
         System.out.println("x =" +x);
         //Return current edge source vetex's root node
         int y = graph.find(parent, graph.edge[i].dest);
       //Return current edge destination  vetex's root node
         System.out.println("y =" +y);

         if (x == y) {
        	 //Means source node and dist node are in same set
        	 return 1;
         }
             

         graph.Union(parent, x, y);
         System.out.println("parrent array is  =" +Arrays.toString(parent));
        // System.out.println("parrent of x  =" +parent[i]);
         
     }
     return 0;
 }

 // Driver Method
 public static void main (String[] args)
 {
     /* Let us create the following graph 
     0 
     | \ 
     |  \ 
     1---2 */
     int V = 3, E = 3;
     DisjointSetDetectCycle graph1 = new DisjointSetDetectCycle(V, E);

     // add edge 0-1
     graph1.edge[0].src = 0;
     graph1.edge[0].dest = 1;

     // add edge 1-2
     graph1.edge[1].src = 1;
     graph1.edge[1].dest = 2;

     // add edge 0-2
     graph1.edge[2].src = 0;
     graph1.edge[2].dest = 2;

     if (isCycle(graph1)==1)
         System.out.println( "graph 1 contains cycle" );
     else
         System.out.println( "graph 1 doesn't contain cycle" );
     
     /* Let us create the following graph 
     0 
     | \ 
     |  \ 
     1   2 */
     
     
     DisjointSetDetectCycle graph2 = new DisjointSetDetectCycle(3, 2);

     // add edge 0-1
     graph2.edge[0].src = 0;
     graph2.edge[0].dest = 1;

     // add edge 1-2
     graph2.edge[1].src = 1;
     graph2.edge[1].dest = 2;

     

     if (isCycle(graph2)==1)
         System.out.println( "graph 2 contains cycle" );
     else
         System.out.println( "graph 2 doesn't contain cycle" );
 }
 }

