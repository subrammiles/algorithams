package datastructures.trees.binarytree.traversal;

import datastructures.trees.binarytree.BinaryTreeHelper;
import datastructures.trees.binarytree.MyBinaryTree;

public class BTLevelOrder {
	
	 
    /* function to print level order traversal of tree*/
    public static void printLevelOrder(MyBinaryTree root)
    {
        int h = BinaryTreeHelper.getHeightOfABinaryTree(root);
        int i;
        for (i = 1; i <= h; i++) {
        	printCurrentLevel(root, i);
        }      
    }
 
  
    /* Print nodes at the current level */
    static void printCurrentLevel(MyBinaryTree root, int level)
    {
        if (root == null)
            return;
        if (level == 1)
            System.out.print(root.value + " ");
        else if (level > 1) {
            printCurrentLevel(root.left, level - 1);
            printCurrentLevel(root.right, level - 1);
        }
    }
}
