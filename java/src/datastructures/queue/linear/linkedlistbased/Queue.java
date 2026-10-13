package datastructures.queue.linear.linkedlistbased;

public class Queue {
	QNode front, real;

	public Queue() {
		front = real = null;
	}

	void enqueue(int data) {
		/*
		 * 
		 * 
		 * 1. Declare a new node and allocate memory for it.
		 * 
		 * 2. If front == NULL,
		 * 
		 * make both front and rear points to the new node.
		 * 
		 * 3. Otherwise,
		 * 
		 * add the new node in rear->next.
		 * 
		 * make the new node as the rear node. i.e. rear = new node
		 * 
		 */
		QNode tempNode = new QNode(data);
		if (real == null) {
			real = tempNode;
			front = tempNode;
		} else {
			this.real.node = tempNode;
			this.real = tempNode;
		}
	}

	void dequeue() {
		/*
		 * Check whether the queue is empty or not If it is the empty queue (front ==
		 * NULL) We can't dequeue the element.
		 * 
		 * Otherwise,
		 * 
		 * Make the front node points to the next node. i.e front = front->next;
		 * 
		 * if front pointer becomes NULL, set the rear pointer also NULL.
		 * 
		 * Free the front node's memory.
		 * 
		 */
		if(front==null) {
			System.out.println("Queue is empty");
			
		}else {
			//Take backup of current node
			QNode temp=front;
			front=front.node;
			//Free the old front node
			temp=null;
		}

	}

	void printQueue() {
		
		System.out.println("Printing the Queue......");
		
		QNode tempNode=front;
		//We copyed front to tempNode
		//We took front insated of real ,because real node changes on enquee
		while(tempNode!=null) {
			System.out.println(tempNode.data);
			tempNode=tempNode.node;			
		}
	}

}
