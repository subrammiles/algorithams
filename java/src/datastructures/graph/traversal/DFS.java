package datastructures.graph.traversal;

import java.io.*;
import java.util.*;

import datastructures.graph.models.AdjacencyListGraph;


public class DFS {
	
	// prints all not yet visited vertices reachable from s
    public static void iterationDFS(int s,AdjacencyListGraph graph)
    {
        // Initially mark all vertices as not visited
        Vector<Boolean> visited = new Vector<Boolean>(graph.getNoOfNodes());
        for (int i = 0; i < graph.getNoOfNodes(); i++)
            visited.add(false);
 
        // Create a stack for DFS
        Stack<Integer> stack = new Stack<>();
         
        // Push the current source node
        stack.push(s);
         
        while(stack.empty() == false)
        {
            // Pop a vertex from stack and print it
            s = stack.peek();
            stack.pop();
             
            // Stack may contain same vertex twice. So
            // we need to print the popped item only
            // if it is not visited.
            if(visited.get(s) == false)
            {
                System.out.print(s + " ");
                visited.set(s, true);
            }
             
            // Get all adjacent vertices of the popped vertex s
            // If a adjacent has not been visited, then push it
            // to the stack.
            ArrayList<ArrayList<Integer>> list = graph.getList();
    		ArrayList<Integer> currentNodeAdjectElements=list.get(s);
            Iterator<Integer> itr =currentNodeAdjectElements.iterator();
             
            while (itr.hasNext())
            {
                int v = itr.next();
                if(!visited.get(v))
                    stack.push(v);
            }
             
        }
    }

	
	// A function used by DFS
	static void DFSUtil(int v, boolean visited[],AdjacencyListGraph graph)
	{
		// Mark the current node as visited and print it
		visited[v] = true;
		System.out.print(v + " ");
		/* Recur for all the vertices adjacent to this vertex*/
		ArrayList<ArrayList<Integer>> list = graph.getList();
		ArrayList<Integer> currentNodeAdjectElements=list.get(v);
		Iterator<Integer> i = currentNodeAdjectElements.listIterator();
		while (i.hasNext()) 
		{
			int n = i.next();
			if (!visited[n])
				DFSUtil(n, visited,graph);
		}
	}

	// The function to do DFS traversal.
	// It uses recursive
	// DFSUtil()
	public static void recursionDFS(int v,AdjacencyListGraph graph)
	{
		/* Mark all the vertices as  not visited(set as false by default in java)*/
		boolean visited[] = new boolean[graph.getNoOfNodes()];
		/*Call the recursive helper function to print DFS traversal*/
		DFSUtil(v, visited,graph);
	}

	
}


