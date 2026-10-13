package datastructures.queue.priority.linkedlistbased;

public class Queue {
	
	Node head;
	

	public Queue(int d, int p) {
		head=new Node(d,p);
		
	}
	
	
	 
	// Function to Create A New Node
	 Node newNode(int d, int p)
	{
	    Node temp = new Node(d,p);
	    temp.data = d;
	    temp.priority = p;
	    temp.node = null;
	     
	    return temp;
	}
	     
	// Return the value at head
	 int peek()
	{
	    return (head).data;
	}
	     
	// Removes the element with the highest priority
	 Node pop()
	{
	    Node temp = head;
	    (head) = (head).node;
	    return head;
	}
	     
	//push according to priority
	 Node push(int d, int p)
	{
	    Node start = (head);
	    Node temp = newNode(d, p);
	     
	    // Special Case: The head of list has lesser
	    // priority than new node. So insert new
	    // node before head node and change head node.
	    if ((head).priority > p) {
	     
	        // Insert New Node before head
	        temp.node = head;
	        (head) = temp;
	    }
	    else {
	     
	        // Traverse the list and find a
	        // position to insert new node
	        while (start.node != null &&
	            start.node.priority < p) {
	            start = start.node;
	        }
	     
	        // Either at the ends of the list
	        // or at required position
	        temp.node = start.node;
	        start.node = temp;
	    }
	    return head;
	}
	     
	// Function to check is list is empty
	 int isEmpty(){
	    return ((head) == null)?1:0; 
	 }
	     

}
