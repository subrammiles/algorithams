package datastructures.graph.data;

import datastructures.graph.models.AdjacencyListGraph;
import datastructures.graph.models.AdjacencyMatrixGraph;
import datastructures.graph.traversal.BFS;
import datastructures.graph.traversal.DFS;

public class SampleGraphs {
	
	public static  AdjacencyMatrixGraph getAnAdjacencyMatrixGraph1() {
		AdjacencyMatrixGraph g = new AdjacencyMatrixGraph(4);
	    g.addEdge(0, 1);
	    g.addEdge(0, 2);
	    g.addEdge(1, 2);
	    g.addEdge(2, 0);
	    g.addEdge(2, 3);
	    return g;
	    
	}
	public static  AdjacencyListGraph getAnAdjacencyListGraph1() {
		AdjacencyListGraph g = new AdjacencyListGraph(4);
		    // Add edges
		    g.addEdge(0, 1);
		    g.addEdge(0, 2);
		    g.addEdge(0, 3);
		    g.addEdge(1, 2);
	    return g;
	    
	}
	
	public static  AdjacencyListGraph getAnAdjacencyListGraph2() {
		AdjacencyListGraph g = new AdjacencyListGraph(7);
		//Graph1.png in assets
		    // Add edges
		    g.addEdge(0, 1);
		    g.addEdge(0, 3);
		    g.addEdge(1, 0);
		    g.addEdge(1, 5);
		    g.addEdge(1, 2);
		    g.addEdge(1, 3);
		    g.addEdge(1, 6);
		    g.addEdge(2, 5);
		    g.addEdge(2, 3);
		    g.addEdge(2, 4);
		    g.addEdge(3, 4);
		    g.addEdge(4, 6);
		    
	    return g;
	    
	}
	
	
	public static void main(String[] args) {
		AdjacencyMatrixGraph g =getAnAdjacencyMatrixGraph1();
		//System.out.print(g.toString());
		AdjacencyListGraph g2=getAnAdjacencyListGraph2();
		//g2.printGraph();
		DFS.iterationDFS(0,g2);
		System.out.println();
		DFS.recursionDFS(0,g2);
	}

}
