package datastructures.trees.binarytree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import datastructures.trees.binarytree.problems.BinaryTreePaths;

public class BinaryTreeHelper {

	///////////////////////////////////////
	////////////*Height *//////////////////
	///////////////////////////////////////
	public static int getHeightOfABinaryTree(MyBinaryTree tree){
		if(tree==null) {
			return 0;
		}
		return 1+Math.max(getHeightOfABinaryTree(tree.left),getHeightOfABinaryTree(tree.right));
	}
	
	public static int heightofABinaryTreeIteration(MyBinaryTree root)
    {
        // empty tree has a height of 0
        if (root == null) {
            return 0;
        }
 
        // create an empty queue and enqueue the root node
        Queue<MyBinaryTree> queue = new ArrayDeque<>();
        queue.add(root);
 
        MyBinaryTree front = null;
        int height = 0;
 
        // loop till queue is empty
        while (!queue.isEmpty())
        {
            // calculate the total number of nodes at the current level
            int size = queue.size();
 
            // process each node of the current level and enqueue their
            // non-empty left and right child
            while (size-- > 0)
            {
                front = queue.poll();
 
                if (front.left != null) {
                    queue.add(front.left);
                }
 
                if (front.right != null) {
                    queue.add(front.right);
                }
            }
 
            // increment height by 1 for each level
            height++;
        }
 
        return height;
    }
	
	boolean isBalanced(MyBinaryTree node)
    {
        int lh; /* for height of left subtree */
 
        int rh; /* for height of right subtree */
 
        /* If tree is empty then return true */
        if (node == null)
            return true;
 
        /* Get the height of left and right sub trees */
        lh = getHeightOfABinaryTree(node.left);
        rh = getHeightOfABinaryTree(node.right);
 
        if (Math.abs(lh - rh) <= 1
            && isBalanced(node.left)
            && isBalanced(node.right))
            return true;
 
        /* If we reach here then tree is not height-balanced */
        return false;
    }
	
	
	//////////////////////////////////////
	////////////*Depth*///////////////////
	/////////////////////////////////////
	
	static int findTheDepthOfLeftMostLeaf(MyBinaryTree node)
	{
	   int d = 0;
	   while (node != null)
	   {
	     d++;
	     node = node.left;
	    }
	    return d;
	}
	 static int findTheDepthOfARigthMostLeaf(MyBinaryTree node) {
		 int d=0;
		 while(node!=null) {
			 d=d+1;
			 node=node.right; 
		 }
		 return d;
				 
	 }
	 
	 /*Number of Node*/
	 
     static int  getNumberOfNodesInBinaryTree(MyBinaryTree node) {
    	 if(node==null) {
    		 return 0;
    	 }
    	 return 1+getNumberOfNodesInBinaryTree(node.left)+getNumberOfNodesInBinaryTree(node.right);
    	 
     }
     
     
  // Recursive function to find level of Node 'ptr' in
     // a binary tree
     int getlevelOfANode(MyBinaryTree node, MyBinaryTree ptr, int lev)
     {
         // base cases
         if (node == null)
             return 0;
  
         if (node == ptr)
             return lev;
  
         // Return level if Node is present in left subtree
         int l = getlevelOfANode(node.left, ptr, lev + 1);
         if (l != 0)
             return l;
  
         // Else search in right subtree
         return getlevelOfANode(node.right, ptr, lev + 1);
     }
     
  // Recursive function to check if two Nodes are
     // siblings
     boolean areTwoNodesSibling(MyBinaryTree node, MyBinaryTree a, MyBinaryTree b)
     {
         // Base case
         if (node == null)
             return false;
  
         return ((node.left == a && node.right == b) ||
                 (node.left == b && node.right == a) ||
                 areTwoNodesSibling(node.left, a, b) ||
                 areTwoNodesSibling(node.right, a, b));
     }
     
     
     // Returns 1 if a and b are cousins, otherwise 0
     boolean isCousin(MyBinaryTree node, MyBinaryTree a, MyBinaryTree b)
     {
         // 1. The two Nodes should be on the same level
         //       in the binary tree.
         // 2. The two Nodes should not be siblings (means
         //    that they should not have the same parent
         //    Node).
    	 
    	 int l1=getlevelOfANode(node, a, 1);
    	 int l2=getlevelOfANode(node, b, 1);
    	 boolean areSiblings=areTwoNodesSibling(node, a, b);
         return (l1==l2)&&(!areSiblings);
     }
     
     
  // Method to calculate the diameter
     //This tahes O(n^2)
     int diameter(MyBinaryTree root)
     {
         // base case if tree is empty
         if (root == null)
             return 0;
         // get the height of left and right sub-trees
         int lheight = getHeightOfABinaryTree(root.left);
         int rheight = getHeightOfABinaryTree(root.right);
         // get the diameter of left and right sub-trees
         int ldiameter = diameter(root.left);
         int rdiameter = diameter(root.right);
         /* Return max of following three
           1) Diameter of left subtree
           2) Diameter of right subtree
           3) Height of left subtree + height of right subtree + 1
          */
         return Math.max(lheight + rheight + 1,
                         Math.max(ldiameter, rdiameter));
     }
     
     /*Optimized diameter*/
     // A utility class to pass heigh object
     class Height {
         int h;
     }
  // define height =0 globally and  call
     // diameterOpt(root,height) from main
     int diameterOpt(MyBinaryTree root, Height height)
     {
         // lh --> Height of left subtree
         // rh --> Height of right subtree
         Height lh = new Height(), rh = new Height();
  
         if (root == null) {
             height.h = 0;
             return 0; // diameter is also 0
         }
 /*
         ldiameter  --> diameter of left subtree
         rdiameter  --> Diameter of right subtree
         Get the heights of left and right subtrees in lh and rh.
         And store the returned values in ldiameter and ldiameter*/
           int ldiameter = diameterOpt(root.left, lh);
           int rdiameter = diameterOpt(root.right, rh);
  
         // Height of current node is max of heights of left
         // and right subtrees plus 1
         height.h = Math.max(lh.h, rh.h) + 1;
  
         return Math.max(lh.h + rh.h + 1,
                         Math.max(ldiameter, rdiameter));
     }
  
     
     int getDiameter(MyBinaryTree root)
     {
         Height height = new Height();
         return diameterOpt(root, height);
     }
     
  
     
     
	/*Complete BT*/
	
	static boolean isFullBinaryTree(MyBinaryTree root) {
		if(root==null) {
			return true;
		}
		if(root.left==null && root.right==null) {
			return true;
		}
		if(root.left!=null && root.right!=null) {
			return isFullBinaryTree(root.left) && isFullBinaryTree(root.right);
		}		
		return false;
	}
	
	static boolean isFullBinaryTreeIteration(MyBinaryTree root) {
		// if tree is empty
	    if (root == null) {
	    	return true;
	    }
	     
	 // queue used for level order traversal
	    Queue<MyBinaryTree> queue = new LinkedList<MyBinaryTree> ();
	    queue.add(root);
	    while(!queue.isEmpty()) {
	    	MyBinaryTree frontNode=queue.peek();
	    	queue.remove();
	    	if(frontNode.left==null&&frontNode.right==null) {
	    		continue;
	    	}
	    	if(frontNode.left==null || frontNode.right==null) {
	    		return false;
	    	}
	    	queue.add(frontNode.left);
	    	queue.add(frontNode.right);
	    }
	    return true;   
	}
	
	
	static boolean isPerfectRec(MyBinaryTree root, int d, int level)
	{
	    // An empty tree is perfect
	    if (root == null)
	        return true;
	 
	    // If leaf node, then its depth must be same as
	    // depth of all other leaves.
	    if (root.left == null && root.right == null)
	        return (d == level+1);
	 
	    // If internal node and one child is empty
	    if (root.left == null || root.right == null)
	        return false;
	 
	    // Left and right subtrees must be perfect.
	    return isPerfectRec(root.left, d, level+1) && isPerfectRec(root.right, d, level+1);
	}
	
	static boolean isPerfectBinaryTree(MyBinaryTree tree) {
		//Time complexity: O(n) 
		int d=findTheDepthOfLeftMostLeaf(tree);
		//or
		d=findTheDepthOfARigthMostLeaf(tree);
		boolean b=isPerfectRec(tree,d,0);
		
		return true;
	}
	static boolean isPerfectBinaryTree2(MyBinaryTree tree) {
		int length = getNumberOfNodesInBinaryTree(tree);
				  return (length & (length+1)) == 0;
	}

	// Function to check if the given tree is perfect
	static boolean checkPerfectTreeIteration(MyBinaryTree root)
	{
	    Queue<MyBinaryTree> q = new LinkedList<MyBinaryTree>();
	 
	    // add the root node
	    q.add(root);
	 
	    // Flag to check if leaf nodes have been found
	    int flag = 0;
	 
	    while (q.size() > 0)
	    {
	    	MyBinaryTree temp = q.peek();
	        q.remove();
	 
	        // If current node has both left and right child
	        if (temp.left != null && temp.right != null)
	        {
	            // If a leaf node has already been found
	            // then return false
	            if (flag == 1)
	                return false;
	 
	            // If a leaf node has not been discovered yet
	            // add the left and right child in the queue
	            else
	            {
	                q.add(temp.left);
	                q.add(temp.right);
	            }
	        }
	 
	        // If a leaf node is found mark flag as one
	        else if (temp.left == null && temp.right == null)
	        {
	            flag = 1;
	        }
	 
	        // If the current node has only one child
	        // then return false
	        else if (temp.left == null || temp.right == null)
	            return false;
	    }
	 
	    // If the given tree is perfect return true
	    return true;
	}
	
	static boolean isCompleteBT1(MyBinaryTree root)
    {
        // Base Case: An empty tree is complete Binary Tree
        if(root == null) {
            return true;
        }
        Queue<MyBinaryTree> queue =new LinkedList<>();
        boolean flag = false;
        queue.add(root);
        while(!queue.isEmpty())
        {
        	MyBinaryTree temp_node = queue.remove();
            /* Check if left child is present*/
            if(temp_node.left != null)
            {  
                if(flag == true) {
                    return false;
                }    
                queue.add(temp_node.left);
            }
            else {
            	// If this a non-full node, set the flag as true
                flag = true;
            }
            /* Check if right child is present*/
            if(temp_node.right != null)
            {   
                if(flag == true) {
                    return false;
                }
                queue.add(temp_node.right); 
            } 
            else {
            	 // If this a non-full node, set the flag as true
                flag = true;
            }
        }
         // If we reach here, then the tree is complete Binary Tree
        return true;
    }
	
	static boolean isCompleteBT2(MyBinaryTree root)
    {
        // Base Case: An empty tree is complete Binary Tree
        if(root == null) {
            return true;
        }
        Queue<MyBinaryTree> queue =new LinkedList<>();
        boolean flag = false;
        queue.add(root);
        while(!queue.isEmpty())
        {
        	MyBinaryTree temp_node = queue.remove();
            if(temp_node==null) {
            	 // If we have seen a NULL node,
                // we set the flag to true
            	flag = true ;
            }else {
            	 // If that NULL node
                // is not the last node
                // then return false
                if(flag == true){
                    return false ;
                }
                // Push both nodes
                // even if there are null
                queue.add(temp_node.left) ;           
                queue.add(temp_node.right) ;
            }
        }
         // If we reach here, then the tree is complete Binary Tree
        return true;
    }
	
	static boolean isSkewedBT(MyBinaryTree root)
	{
	    // check if node is null or is a leaf node
	    if (root == null || (root.left == null &&
	                        root.right == null))
	        return true;
	 
	    // check if node has two children if
	    // yes, return false
	    if (root.left!=null && root.right!=null)
	        return false;
	    if (root.left!=null)
	        return isSkewedBT(root.left);
	    return isSkewedBT(root.right);
	}
	
	/*LCA*/
	 private List<Integer> lcaPath1 = new ArrayList<>();
	 private List<Integer> lcaPath2 = new ArrayList<>();
	// Finds the path from root node to given root of the tree.
	 int findLCA(int n1, int n2,MyBinaryTree root) {
		 lcaPath1.clear();
		 lcaPath2.clear();
	     return findLCAInternal(root, n1, n2);
	 }

	 private int findLCAInternal(MyBinaryTree root, int n1, int n2) {
		 boolean b1=BinaryTreePaths.findPathFromRootToGivenNode(root, n1, lcaPath1);
		 boolean b2=BinaryTreePaths.findPathFromRootToGivenNode(root, n2, lcaPath2);
	     if (!b1 || !b2) {
	         /*n1 or n2 element  is missing from tree*/
	         return -1;
	     }

	     int i;
	     for (i = 0; i < lcaPath1.size() && i < lcaPath2.size(); i++) {
	         if (!lcaPath1.get(i).equals(lcaPath2.get(i)))
	             break;
	     }
	     return lcaPath1.get(i-1);
	 }
	 
	 static boolean v1 = false, v2 = false;
	 
	    // This function returns pointer to LCA of two given
	    // values n1 and n2.
	    // v1 is set as true by this function if n1 is found
	    // v2 is set as true by this function if n2 is found
	 MyBinaryTree findLCAUtil(MyBinaryTree node, int n1, int n2)
	    {
	        // Base case
	        if (node == null)
	            return null;
	         
	        //Store result in temp, in case of key match so that we can search for other key also.
	        MyBinaryTree temp=null;
	 
	        // If either n1 or n2 matches with root's key, report the presence
	        // by setting v1 or v2 as true and return root (Note that if a key
	        // is ancestor of other, then the ancestor key becomes LCA)
	        if (node.value == n1)
	        {
	            v1 = true;
	            temp = node;
	        }
	        if (node.value == n2)
	        {
	            v2 = true;
	            temp = node;
	        }
	 
	        // Look for keys in left and right subtrees
	        MyBinaryTree left_lca = findLCAUtil(node.left, n1, n2);
	        MyBinaryTree right_lca = findLCAUtil(node.right, n1, n2);
	 
	        if (temp != null)
	            return temp;
	 
	        // If both of the above calls return Non-NULL, then one key
	        // is present in once subtree and other is present in other,
	        // So this node is the LCA
	        if (left_lca != null && right_lca != null)
	            return node;
	 
	        // Otherwise check if left subtree or right subtree is LCA
	        return (left_lca != null) ? left_lca : right_lca;
	    }
	 
	    // Finds lca of n1 and n2 under the subtree rooted with 'node'
	 MyBinaryTree findLCA2(int n1, int n2,MyBinaryTree root)
	    {
	        // Initialize n1 and n2 as not visited
	        v1 = false;
	        v2 = false;
	 
	        // Find lca of n1 and n2 using the technique discussed above
	        MyBinaryTree lca = findLCAUtil(root, n1, n2);
	 
	        // Return LCA only if both n1 and n2 are present in tree
	        if (v1 && v2)
	            return lca;
	 
	        // Else return NULL
	        return null;
	    }

	
	// Function to check if a given node is a leaf node or not
	    public static boolean isLeaf(MyBinaryTree node) {
	        return (node.left == null && node.right == null);
	    }



         
}
