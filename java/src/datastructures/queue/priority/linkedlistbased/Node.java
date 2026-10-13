package datastructures.queue.priority.linkedlistbased;

public class Node {
	
	int data; 
    int priority; 
    Node node;
    
    Node(int data,int priority){
    	this.data=data;
    	this.priority=priority;
    	this.node=null;
    }

}
