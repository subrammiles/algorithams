package datastructures.graph.traversal;

import java.io.*;
import java.util.*;

import datastructures.graph.models.AdjacencyListGraph;

public class BFS {
	
	public static void doBFSTravel(AdjacencyListGraph graph, int currentNode) {
		/* Mark all the vertices as not visited(By default set as false)*/
		boolean visited[] = new boolean[graph.getNoOfNodes()];

		/* Create a queue for BFS*/
		LinkedList<Integer> queue = new LinkedList<Integer>();

		/* Mark the current node as visited and enqueue it*/
		visited[currentNode] = true;
		queue.add(currentNode);

		while (queue.size() != 0) {
			// Dequeue a vertex from queue and print it
			currentNode = queue.poll();
			System.out.print(currentNode + " ");

			/* Get all adjacent vertices of the 
			 * dequeued vertex currentNode*/
			/*If a adjacent has not been visited, then mark it
			 visited and enqueue it*/
			ArrayList<ArrayList<Integer>> list = graph.getList();
			ArrayList<Integer> currentNodeAdjectElements=list.get(currentNode);
			Iterator<Integer> i = currentNodeAdjectElements.listIterator();
			while (i.hasNext()) {
				int n = i.next();
				if (!visited[n]) {
					visited[n] = true;
					queue.add(n);
				}
			}
		}
	}

}