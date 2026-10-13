package datastructures.graph.models;

import java.util.ArrayList;

public class AdjacencyListGraph {
	int numberofVertices;
    ArrayList<ArrayList<Integer>> arrayOfLinkedLists;
    
    public AdjacencyListGraph(int numberofVertices ) {
    	this.numberofVertices=numberofVertices;
    	arrayOfLinkedLists = new ArrayList<ArrayList<Integer>>(numberofVertices);
    	for (int i = 0; i < numberofVertices; i++) {
    		arrayOfLinkedLists.add(new ArrayList<Integer>());
    	}      
    }
    
    public ArrayList<ArrayList<Integer>> getList() {
    	return arrayOfLinkedLists;
    }
    public int getNoOfNodes() {
    	return arrayOfLinkedLists.size();
    }
    public int getNoEdges() {
    	return arrayOfLinkedLists.size();
    }
 
    public  void addEdge(int source, int distination) {
    	arrayOfLinkedLists.get(source).add(distination);
    	arrayOfLinkedLists.get(distination).add(source);
    }
    
 // Print the graph
    public  void printGraph() {
      for (int i = 0; i < arrayOfLinkedLists.size(); i++) {
        System.out.println("\nVertex " + i + ":");
        for (int j = 0; j < arrayOfLinkedLists.get(i).size(); j++) {
          System.out.print(" -> " + arrayOfLinkedLists.get(i).get(j));
        }
        System.out.println();
      }
    }

}
