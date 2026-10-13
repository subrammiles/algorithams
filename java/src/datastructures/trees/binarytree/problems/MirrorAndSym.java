package datastructures.trees.binarytree.problems;

import java.util.LinkedList;
import java.util.Queue;

import datastructures.trees.binarytree.MyBinaryTree;

public class MirrorAndSym {
	
	public static MyBinaryTree getMirrorTree(MyBinaryTree root){
		MyBinaryTree mroot = mirror(root);
		return mroot;
     }
 
	private static MyBinaryTree mirror(MyBinaryTree node)
    {
        if (node == null)
            return node;
        /* do the subtrees */
        MyBinaryTree left = mirror(node.left);
        MyBinaryTree right = mirror(node.right);
        /* swap the left and right pointers */
        node.left = right;
        node.right = left;
        return node;
    }
	
	public static MyBinaryTree getMirrorOfBTIteration(MyBinaryTree root)
	{
	    if (root == null)
	        return root;
	 
	    Queue<MyBinaryTree> q = new LinkedList<>();
	    q.add(root);
	 
	    // Do BFS. While doing BFS, keep swapping
	    // left and right children
	    while (q.size() > 0)
	    {
	        // pop top node from queue
	    	MyBinaryTree curr = q.peek();
	        q.remove();
	 
	        // swap left child with right child
	        MyBinaryTree temp = curr.left;
	        curr.left = curr.right;
	        curr.right = temp;;
	 
	        // push left and right children
	        if (curr.left != null)
	            q.add(curr.left);
	        if (curr.right != null)
	            q.add(curr.right);
	    }
	    
	    return root;
	}
	
	
	// recursive function to find mirror of Node
	static int findMirrorOfANodeRec(int target, MyBinaryTree left, MyBinaryTree right)
	{
	    /* if any of the Node is none then Node itself
	    and decendent have no mirror, so return
	    none, no need to further explore! */
	    if (left==null || right==null)
	        return 0;
	 
	    /* if left Node is target Node, then return
	    right's key (that is mirror) and vice
	    versa */
	    if (left.value == target)
	        return right.value;
	 
	    if (right.value == target)
	        return left.value;
	 
	    // first recur external Nodes
	    int mirror_val = findMirrorOfANodeRec(target, left.left, right.right);
	    if (mirror_val != 0)
	        return mirror_val;
	 
	    // if no mirror found, recur internal Nodes
	    return findMirrorOfANodeRec(target, left.right, right.left);
	}
	 
	// interface for mirror search
	static int findMirrorOfANode(MyBinaryTree root, int target)
	{
	    if (root == null)
	        return 0;
	    if (root.value == target)
	        return target;
	    return findMirrorOfANodeRec(target, root.left, root.right);
	}
	
	
	boolean isTreeSymmetricRect(MyBinaryTree node1, MyBinaryTree node2)
    {
        // if both trees are empty,
        //  then they are mirror image
        if (node1 == null && node2 == null)
            return true;
  
        // For two trees to be mirror images, the following
        // three conditions must be true 1 - Their root
        // node's key must be same 2 - left subtree of left
        // tree and right subtree
        //      of right tree have to be mirror images
        // 3 - right subtree of left tree and left subtree
        //      of right tree have to be mirror images
        if (node1 != null && node2 != null
            && node1.value == node2.value) {
        	
        	boolean b1=isTreeSymmetricRect(node1.left, node2.right);
        	boolean b2=isTreeSymmetricRect(node1.right, node2.left);
        	return (b1&&b2);
        }
            
  
        // if none of the above conditions is true then
        // root1 and root2 are not mirror images
        return false;
    }
  
    // returns true if the tree is symmetric i.e
    // mirror image of itself
   public  boolean isTreeSymmetric(MyBinaryTree root)
    {
        // check if tree is mirror of itself
        return isTreeSymmetricRect(root, root);
    }
    
    public boolean isTreeSymmetricIteration(MyBinaryTree root)
    {
        /* This allows adding null elements to the queue */
        Queue<MyBinaryTree> q = new LinkedList<MyBinaryTree>();
 
        /* Initially, add left and right nodes of root */
        q.add(root.left);
        q.add(root.right);
 
        while (!q.isEmpty())
        {
            /* remove the front 2 nodes to
              check for equality */
        	MyBinaryTree tempLeft = q.remove();
        	MyBinaryTree tempRight = q.remove();
 
            /* if both are null, continue and check
               for further elements */
            if (tempLeft==null && tempRight==null)
                continue;
 
            /* if only one is null---inequality, return false */
            if ((tempLeft==null && tempRight!=null) ||
                (tempLeft!=null && tempRight==null))
                return false;
 
            /* if both left and right nodes exist, but
               have different values-- inequality,
               return false*/
            if (tempLeft.value != tempRight.value)
                return false;
 
            /* Note the order of insertion of elements
               to the queue :
               1) left child of left subtree
               2) right child of right subtree
               3) right child of left subtree
               4) left child of right subtree */
            q.add(tempLeft.left);
            q.add(tempRight.right);
            q.add(tempLeft.right);
            q.add(tempRight.left);
        }
 
        /* if the flow reaches here, return true*/
        return true;
    }

	
	
	

	public static void main(String[] args) {
		

	}

}
