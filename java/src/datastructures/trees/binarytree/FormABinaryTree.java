package datastructures.trees.binarytree;

public class FormABinaryTree {
	
	/* Function to insert nodes in level order*/
    public static MyBinaryTree insertLevelOrder(int[] arr, MyBinaryTree root,
                                                int i)
    {
        /* Base case*/
        if (i < arr.length) {
        	MyBinaryTree temp = new MyBinaryTree(arr[i]);
            root = temp;
            /* insert left child*/
            root.left = insertLevelOrder(arr, root.left,2 * i + 1);
            /* insert right child*/
            root.right = insertLevelOrder(arr, root.right,2 * i + 2);
        }
        return root;
    }
    public static MyBinaryTree  getCompleteBinaryTreeFromAnArray(int arr[]) {
		MyBinaryTree tree = new MyBinaryTree();
		tree = insertLevelOrder(arr, tree, 0);
        return tree;
	}
 

}
