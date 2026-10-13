package datastructures.graph.models;

public class GraphEdge {
	int source; 
	int dest;
	int weight;
	GraphEdge(int source, int dest,int weight)
    {
        this.source = source;
        this.dest = dest;
        this.weight=weight;
    }
	
	GraphEdge(int source, int dest)
    {
        this.source = source;
        this.dest = dest;
    }
}
