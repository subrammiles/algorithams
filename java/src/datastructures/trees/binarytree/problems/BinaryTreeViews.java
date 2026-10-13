package datastructures.trees.binarytree.problems;


import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import datastructures.trees.binarytree.MyBinaryTree;

import java.util.Queue;
import java.util.TreeMap;

public class BinaryTreeViews {
	private static class NodeWithDistance {
		MyBinaryTree node;
        int horizontalDistance;
        NodeWithDistance(MyBinaryTree node, int hd)
        {
            this.node = node;
            this.horizontalDistance = hd;
        }
    }
	
	public static  void topView1(MyBinaryTree root)
    {
        
        Queue<NodeWithDistance> q = new LinkedList<NodeWithDistance>();
        Map<Integer, MyBinaryTree> topViewMap
            = new TreeMap<Integer, MyBinaryTree>();
        if (root == null) {
            return;
        }
        else {
            q.add(new NodeWithDistance(root, 0));
        }
 
        System.out.println(
            "The top view of the tree is : ");
 
        // count function returns 1 if the container
        // contains an element whose key is equivalent
        // to horizontalDistance, or returns zero otherwise.
        while (!q.isEmpty()) {
        	NodeWithDistance tmpNode = q.poll();
            if (!topViewMap.containsKey(tmpNode.horizontalDistance)) {
                topViewMap.put(tmpNode.horizontalDistance, tmpNode.node);
            }
 
            if (tmpNode.node.left != null) {
                q.add(new NodeWithDistance(tmpNode.node.left,
                                   tmpNode.horizontalDistance - 1));
            }
            if (tmpNode.node.right != null) {
                q.add(new NodeWithDistance(tmpNode.node.right,
                                   tmpNode.horizontalDistance + 1));
            }
        }
        for (Entry<Integer, MyBinaryTree> entry :
             topViewMap.entrySet()) {
        	System.out.print(entry.getValue().value);
        }
    }
	
	/*Approch 2 for Top view.........*/
	private static  class MyPair {
        int nodeData, height;
        
        MyPair(int i, int j)
        {
        	nodeData = i;
        	height = j;
        }
    }
	
   

    static void inorderTopView(MyBinaryTree root, int w,int h, TreeMap <Integer,MyPair> Map)
    {
    	if(root == null) 
            return; 
            
    	inorderTopView(root.left,w-1,h+1,Map);
            
            /* check if a particular horizontal level has been visited or not */
            if(!Map.containsKey(w))
                Map.put(w,new MyPair(root.value,h)); 
            
            /* if particular horizontal level has been visited 
            then check if height of current node is less than
            the previous node met at same horizontal level, if this 
            is true then the current node should replace previous node
            in top view of the binary tree */
            else if(Map.get(w).height > h)
                Map.put(w,new MyPair(root.value,h));
            inorderTopView(root.right,w+1,h+1,Map);
    }
 
    // function should print the topView of
    // the binary tree
    public static void topView(MyBinaryTree root)
    {

        /* map to store node at each vertical(horizontal) distance(Level)
        i.e. stores top view */
        TreeMap<Integer,MyPair> Map = new TreeMap<>();
      
        // obtain top view of binary tree into the Map
        inorderTopView(root,0,0,Map); 
        
        /* traverse the map and print top view */
        for (Map.Entry<Integer, MyPair> i : Map.entrySet()) 
            System.out.print(i.getValue().nodeData+" ");
    }
    
    static void preorderBottomView(MyBinaryTree root, int w,int h, TreeMap <Integer,MyPair> Map)
    {
    	if(root == null) 
            return; 
    	
    	/* check if a particular horizontal level has been visited or not */
        if(!Map.containsKey(w))
            Map.put(w,new MyPair(root.value,h)); 
        
        /* if particular horizontal level has been visited 
        then check if height of current node is less than
        the previous node met at same horizontal level, if this 
        is true then the current node should replace previous node
        in top view of the binary tree */
        else if(Map.get(w).height > h)
            Map.put(w,new MyPair(root.value,h));
            
    	inorderTopView(root.left,w-1,h+1,Map);
            
            
            inorderTopView(root.right,w+1,h+1,Map);
    }
    
    
    public static void bottomView2(MyBinaryTree root)
    {

        /* map to store node at each vertical(horizontal) distance(Level)
        i.e. stores top view */
        TreeMap<Integer,MyPair> Map = new TreeMap<>();
      
        // obtain top view of binary tree into the Map
        preorderBottomView(root,0,0,Map); 
        
        /* traverse the map and print top view */
        for (Map.Entry<Integer, MyPair> i : Map.entrySet()) 
            System.out.print(i.getValue().nodeData+" ");
    }
    
    
    // Iterative function to print the left view of a given binary tree
    public static void leftView(MyBinaryTree root)
    {
        // return if the tree is empty
        if (root == null) {
            return;
        }
 
        // create an empty queue and enqueue the root node
        Queue<MyBinaryTree> queue = new ArrayDeque<>();
        queue.add(root);
 
        // to store the current node
        MyBinaryTree curr;
 
        // loop till queue is empty
        while (!queue.isEmpty())
        {
            // calculate the total number of nodes at the current level
            int size = queue.size();
            int i = 0;
 
            // process every node of the current level and enqueue their
            // non-empty left and right child
            while (i++ < size)
            {
                curr = queue.poll();
 
                // if this is the first node of the current level, print it
                if (i == 1) {
                    System.out.print(curr.value + " ");
                }
 
                if (curr.left != null) {
                    queue.add(curr.left);
                }
 
                if (curr.right != null) {
                    queue.add(curr.right);
                }
            }
        }
    }
    
    
    // Recursive function to traverse the nodes in a preorder fashion
    public static void leftView(MyBinaryTree root, int level, Map<Integer, Integer> map)
    {
        // base case
        if (root == null) {
            return;
        }
 
        // if the level is visited for the first time, insert the current node
        // and level information into the map
        map.putIfAbsent(level, root.value);
 
        leftView(root.left, level + 1, map);
        leftView(root.right, level + 1, map);
    }
 
    // Function to print the left view of a given binary tree
    public static void leftView2(MyBinaryTree root)
    {
        // create an empty HashMap to store the first node for each level
        Map<Integer, Integer> map = new HashMap<>();
 
        // traverse the tree and fill the map
        leftView(root, 1, map);
 
        // iterate through the HashMap in sorted order of its keys
        // and print the left view
        for (int i = 1; i <= map.size(); i++) {
            System.out.print(map.get(i) + " ");
        }
    }
    
    
 // Recursive function to print the left view of a given binary tree
    public static int leftView(MyBinaryTree root, int level, int last_level)
    {
        // base case: empty tree
        if (root == null) {
            return last_level;
        }
 
        // if the current node is the first node of the current level
        if (last_level < level)
        {
            // print the node's data
            System.out.print(root.value + " ");
 
            // update the last level to the current level
            last_level = level;
        }
 
        // recur for the left and right subtree by increasing the level by 1
        last_level = leftView(root.left, level + 1, last_level);
        last_level = leftView(root.right, level + 1, last_level);
 
        return last_level;
    }
 
    // Function to print the left view of a given binary tree
    public static void leftView3(MyBinaryTree root) {
        leftView(root, 1, 0);
    }
 
    
   
}
