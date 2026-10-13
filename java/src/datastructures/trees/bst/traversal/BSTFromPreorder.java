package datastructures.trees.bst.traversal;

import java.util.Stack;

import datastructures.trees.binarytree.MyBinaryTree;

public class BSTFromPreorder {
	
	
	
	static int index = 0;
	 
    // A recursive function to construct Full from pre[].
    // preIndex is used to keep track of index in pre[].
	static MyBinaryTree constructTreeUtil(int pre[],int low, int high, int size)
    {
 
        // Base case
        if (index >= size || low > high) {
            return null;
        }
 
        // The first node in preorder traversal is root. So
        // take the node at preIndex from pre[] and make it
        // root, and increment preIndex
        MyBinaryTree root = new MyBinaryTree(pre[index]);
        index = index + 1;
 
        // If the current subarray has only one element, no
        // need to recur
        if (low == high) {
            return root;
        }
 
        // Search for the first element greater than root
        int i;
        for (i = low; i <= high; ++i) {
            if (pre[i] > root.value) {
                break;
            }
        }
 
        // Use the index of element found in preorder to
        // divide preorder array in two parts. Left subtree
        // and right subtree
        root.left = constructTreeUtil(pre, index, i - 1, size);
        root.right = constructTreeUtil(pre, i,high, size);
 
        return root;
    }
 
    // The main function to construct BST from given
    // preorder traversal. This function mainly uses
    // constructTreeUtil()
	static MyBinaryTree constructTree(int pre[], int size)
    {
        return constructTreeUtil(pre, 0, size - 1, size);
    }
	
	// A recursive function to construct BST from pre[].
    // preIndex is used to keep track of index in pre[].
	static MyBinaryTree constructTreeUtil(int pre[],
                           int key, int min, int max,
                           int size)
    {
 
        // Base case
        if (index >= size) {
            return null;
        }
 
        MyBinaryTree root = null;
 
        // If current element of pre[] is in range, then
        // only it is part of current subtree
        if (key > min && key < max) {
 
            // Allocate memory for root of this
            // subtree and increment *preIndex
            root = new MyBinaryTree(key);
            index = index + 1;
 
            if (index < size) {
 
                // Construct the subtree under root
                // All nodes which are in range {min .. key}
                // will go in left subtree, and first such
                // node will be root of left subtree.
                root.left = constructTreeUtil(
                    pre,  pre[index], min,
                    key, size);
            }
            if (index < size) {
                // All nodes which are in range {key..max}
                // will go in right subtree, and first such
                // node will be root of right subtree.
                root.right = constructTreeUtil(
                    pre,  pre[index], key,
                    max, size);
            }
        }
 
        return root;
    }
	
	static MyBinaryTree constructTree2(int pre[], int size)
	    {
	       
	        return constructTreeUtil(pre, pre[0],
	                                 Integer.MIN_VALUE,
	                                 Integer.MAX_VALUE, size);
	    }
 
    // A utility function to print inorder traversal of a
    // Binary Tree
    static void printInorder(MyBinaryTree node)
    {
        if (node == null) {
            return;
        }
        printInorder(node.left);
        System.out.print(node.value + " ");
        printInorder(node.right);
    }
    
    
    /*Iteration*/
 // The main function that constructs BST from pre[]
    static MyBinaryTree constructTreeIteration(int pre[], int size) {
 
        // The first element of pre[] is always root
    	MyBinaryTree root = new MyBinaryTree(pre[0]);
 
        Stack<MyBinaryTree> s = new Stack<MyBinaryTree>();
 
        // Push root
        s.push(root);
 
        // Iterate through rest of the size-1 items of given preorder array
        for (int i = 1; i < size; ++i) {
        	MyBinaryTree temp = null;
 
            /* Keep on popping while the next value is greater than
             stack's top value. */
            while (!s.isEmpty() && pre[i] > s.peek().value) {
                temp = s.pop();
            }
 
            // Make this greater value as the right child
            // and push it to the stack
            if (temp != null) {
                temp.right = new MyBinaryTree(pre[i]);
                s.push(temp.right);
            }
             
            // If the next value is less than the stack's top
            // value, make this value as the left child of the
            // stack's top node. Push the new node to stack
            else {
                temp = s.peek();
                temp.left = new MyBinaryTree(pre[i]);
                s.push(temp.left);
            }
        }
        return root;
    }
 
    // Driver code
    public static void main(String[] args)
    {
    	MyBinaryTree tree = new MyBinaryTree();
        int pre[] = new int[] { 10, 5, 1, 7, 40, 50 };
        int size = pre.length;
        MyBinaryTree root = constructTreeIteration(pre, size);
        printInorder(root);
        
    }

}
