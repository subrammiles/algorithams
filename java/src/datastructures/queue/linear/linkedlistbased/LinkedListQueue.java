package datastructures.queue.linear.linkedlistbased;

public class LinkedListQueue {

	 

	public static void main(String[] args) {
		
		Queue queue=new Queue();
		queue.enqueue(34);
		queue.enqueue(64);
		queue.enqueue(56);
		queue.enqueue(32);
		queue.enqueue(3);
		queue.printQueue();
		queue.dequeue();
		queue.printQueue();
	}

}
