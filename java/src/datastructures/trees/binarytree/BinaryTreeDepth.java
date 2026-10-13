package datastructures.trees.binarytree;

public class BinaryTreeDepth {
	
	
	
	// Function to find the sum of depths of
	// all nodes in subtree of the current node
	static int sumofdepth(MyBinaryTree root, int d)
	{
	     
	    // If NULL node then return 0
	    if (root == null)
	        return 0;
	         
	    // Recursively find the sum of
	    // depths of all nodes in the
	    // left and right subtree
	    return d + sumofdepth(root.left, d + 1) +
	              sumofdepth(root.right, d + 1);
	}
	 
	// Function to calculate the sum
	// of depth of all the subtrees
	static int sumofallsubtrees(MyBinaryTree root)
	{
	     
	    // If root is NULL return 0
	    if (root == null)
	        return 0;
	 
	    // Find the total depth sum of
	    // current node and recursively
	    return sumofdepth(root, 0) +
	           sumofallsubtrees(root.left) +
	           sumofallsubtrees(root.right);
	}
	
	static class pair
    {
        int first, second;
        public pair(int first, int second) 
        {
            this.first = first;
            this.second = second;
        }   
    }
	
	static int ans;
	
	// DFS function to calculate the sum
	// of depths of all subtrees depth sum
	static pair sumofsubtree(MyBinaryTree root)
	{
	   
	    // Store total number of node in
	    // its subtree and total sum of
	    // depth in its subtree
	    pair p = new pair(1, 0);
	 
	    // Check if left is not null
	    if (root.left != null)
	    {
	 
	        // Call recursively the DFS
	        // function for left child
	        pair ptemp
	            = sumofsubtree(root.left);
	 
	        // Increment the sum of depths
	        // by ptemp.first+p.temp.first
	        p.second += ptemp.first
	                    + ptemp.second;
	 
	        // Increment p.first by count
	        // of noded in left subtree
	        p.first += ptemp.first;
	    }
	 
	    // Check if right is not null
	    if (root.right != null)
	    {
	 
	        // Call recursively the DFS
	        // function for right child
	        pair ptemp
	            = sumofsubtree(root.right);
	 
	        // Increment the sum of depths
	        // by ptemp.first+p.temp.first
	        p.second += ptemp.first
	                    + ptemp.second;
	 
	        // Increment p.first by count of
	        // nodes in right subtree
	        p.first += ptemp.first;
	    }
	 
	    // Increment the result by total
	    // sum of depth in current subtree
	    ans += p.second;
	 
	    // Return p
	    return p;
	}

}
