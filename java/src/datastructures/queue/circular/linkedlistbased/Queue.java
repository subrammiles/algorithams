package datastructures.queue.circular.linkedlistbased;

public class Queue {
	
	private Node front, rear;
	
	public Queue() {
		front=null;
		rear=null;
	}
	
	
	void enqueue(int data){
		//Time complexity  is O(1)
		Node temp=new Node(data);
		if(front==null) {
			//Queue is empty
			front=temp;
		}else {
			//Real is not null
			rear.node=temp;
		}
		rear=temp;
		//We are assigning last element node to front.So that it will form a circular queue
		rear.node=front;
	}
	
	 int deQueue()
	    {   //Time complexity  is O(1)
	        if (front == null) {
	            System.out.printf("Queue is empty");
	            return Integer.MIN_VALUE;
	        }
	 
	        
	        int value; 
	        if (front == rear) {
	        	// last node to be deleted
	            value = front.data;
	            front = null;
	            rear = null;
	        }
	        else 
	        {// There are more than one nodes
	            Node temp = front;
	            value = temp.data;
	            front = front.node;
	            rear.node = front;
	        }
	 
	        return value;
	    }
	 
	 
	 void displayQueue()
	 {   
	    	 //Time complexity  O(n)
	        Node temp = front;
	        System.out.printf(" Circular Queue : ");
	        while (temp.node != front) {
	            System.out.printf("%d ", temp.data);
	            temp = temp.node;
	        }
	        System.out.printf("%d", temp.data);
	 }
	
	void printRear() {
		Node temp=rear;
		do {
			System.out.println(temp.data);
			temp=temp.node;
		}while(temp != rear); 
	}
}
