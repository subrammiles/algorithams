package algorithms.graphs.kruskal;

//Java program for Kruskal's algorithm to 
//find Minimum Spanning Tree of a given 
//connected, undirected and weighted graph
import java.util.*;

import algorithms.graphs.kruskal.KruskalGraph.Edge;

import java.lang.*;
import java.io.*;




class Kruskal {
	int vertices, edges;
	Edge edge[];
	
	
	// A class to represent a subset for 
			// union-find
			static class Subset 
			{
				int parent, rank;
			};
	
	
	  // Applying Krushkal Algorithm
	  static void  applyKruskalAlgo(int vertices,KruskalGraph kruskalGraph) {
	    
		// Sorting the edges
		    Arrays.sort(kruskalGraph.edge);
		
		Edge result[] = new Edge[vertices];
	    int e = 0;
	    int i = 0;
	    for (i = 0; i < vertices; ++i) {
	    	 result[i] = new Edge();
	    }
	     
	    
	    Subset subsets[] = new Subset[vertices];
	    for (i = 0; i < vertices; ++i) {
	    	 subsets[i] = new Subset();
	    }
	     

	    for (int v = 0; v < vertices; ++v) {
	      subsets[v].parent = v;
	      subsets[v].rank = 0;
	    }
	    i = 0;
	    while (e < vertices - 1) {
	      Edge next_edge = new Edge();
	      next_edge = kruskalGraph.edge[i++];
	      int x = find(subsets, next_edge.src);
	      int y = find(subsets, next_edge.dest);
	      if (x != y) {
	        result[e++] = next_edge;
	        Union(subsets, x, y);
	      }
	    }
	    for (i = 0; i < e; ++i)
	      System.out.println(result[i].src + " - " + result[i].dest + ": " + result[i].weight);
	  }
	  
	  
	  static int  find(Subset subsets[], int i) {
		    if (subsets[i].parent != i)
		      subsets[i].parent = find(subsets, subsets[i].parent);
		    return subsets[i].parent;
		  }

		  static void Union(Subset subsets[], int x, int y) {
		    int xroot = find(subsets, x);
		    int yroot = find(subsets, y);

		    if (subsets[xroot].rank < subsets[yroot].rank)
		      subsets[xroot].parent = yroot;
		    else if (subsets[xroot].rank > subsets[yroot].rank)
		      subsets[yroot].parent = xroot;
		    else {
		      subsets[yroot].parent = xroot;
		      subsets[xroot].rank++;
		    }
		  }

	  public static void main(String[] args) {
	    int vertices = 6; // Number of vertices
	    int edges = 8; // Number of edges
	    KruskalGraph G = new KruskalGraph(vertices, edges);
	    
	    //At Vertex 0

	    G.edge[0].src = 0;
	    G.edge[0].dest = 1;
	    G.edge[0].weight = 4;

	    G.edge[1].src = 0;
	    G.edge[1].dest = 2;
	    G.edge[1].weight = 4;

	    //At vertex 1
	    G.edge[2].src = 1;
	    G.edge[2].dest = 2;
	    G.edge[2].weight = 2;

	    
	    //At vertex 2
	    
	    G.edge[3].src = 2;
	    G.edge[3].dest = 3;
	    G.edge[3].weight = 3;

	    G.edge[4].src = 2;
	    G.edge[4].dest = 5;
	    G.edge[4].weight = 2;

	    G.edge[5].src = 2;
	    G.edge[5].dest = 4;
	    G.edge[5].weight = 4;

	    
	    //at vertex 3
	    G.edge[6].src = 3;
	    G.edge[6].dest = 4;
	    G.edge[6].weight = 3;

	    
	    //at vertex 3
	    G.edge[7].src = 5;
	    G.edge[7].dest = 4;
	    G.edge[7].weight = 3;
	    System.out.println("Graph Defination");
	    System.out.println("W ="+G.edge[0].weight +" S ="+G.edge[0].src +" D="+G.edge[0].dest);
	    System.out.println("W ="+G.edge[1].weight +" S ="+G.edge[1].src +" D="+G.edge[1].dest);
	    System.out.println("W ="+G.edge[2].weight +" S ="+G.edge[2].src +" D="+G.edge[2].dest);
	    System.out.println("W ="+G.edge[3].weight +" S ="+G.edge[3].src +" D="+G.edge[3].dest);
	    System.out.println("W ="+G.edge[4].weight +" S ="+G.edge[4].src +" D="+G.edge[4].dest);
	    System.out.println("W ="+G.edge[5].weight +" S ="+G.edge[5].src +" D="+G.edge[5].dest);
	    System.out.println("W ="+G.edge[6].weight +" S ="+G.edge[6].src +" D="+G.edge[6].dest);
	    
	    applyKruskalAlgo(vertices,G);
	  }
	  
}

