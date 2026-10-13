package algorithms.graphs.kruskal;



public class KruskalGraph {
	int V, E; // V-> no. of vertices & E->no.of edges
	Edge edge[]; // collection of all edges

	// A class to represent a graph edge
		static class Edge implements Comparable<Edge> 
		{
			int src, dest, weight;

			// Comparator function used for 
			// sorting edgesbased on their weight
			public int compareTo(Edge compareEdge)
			{
				return this.weight - compareEdge.weight;
			}
		};

		

		

		// Creates a graph with V vertices and E edges
		KruskalGraph(int v, int e)
		{
			V = v;
			E = e;
			edge = new Edge[E];
			for (int i = 0; i < e; ++i) {
				edge[i] = new Edge();
			}
				
		}

}
