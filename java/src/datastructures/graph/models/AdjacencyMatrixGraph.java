package datastructures.graph.models;

public class AdjacencyMatrixGraph {
	private boolean adjMatrix[][];
    private int numberofVertices;
    

    public AdjacencyMatrixGraph(int numberofVertices) {
       // Initialize the matrix
      this.numberofVertices = numberofVertices;
      adjMatrix = new boolean[numberofVertices][numberofVertices];
    }

    // Add edges
    public void addEdge(int i, int j) {
      adjMatrix[i][j] = true;
      adjMatrix[j][i] = true;
    }

    // Remove edges
    public void removeEdge(int i, int j) {
      adjMatrix[i][j] = false;
      adjMatrix[j][i] = false;
    }

    // Print the matrix
    public String toString() {
      StringBuilder s = new StringBuilder();
      for (int i = 0; i < numberofVertices; i++) {
        s.append(i + ": ");
        for (boolean j : adjMatrix[i]) {
          s.append((j ? 1 : 0) + " ");
        }
        s.append("\n");
      }
      return s.toString();
    }

}
