package datastructures.queue.linear.arraybased;

public class SimpleQueue {


	public static void main(String[] args) {
         
		Queue queue=new Queue(5);
		
		queue.enque(34);
		queue.enque(84);
		queue.enque(58);
		queue.enque(74);
		queue.enque(7);
		queue.enque(58);
		queue.printQueue();
		queue.deque();
		
		queue.printQueue();
		
		
		queue.deque();
		queue.deque();
		queue.deque();
		queue.deque();
		queue.deque();
		queue.enque(34);
		queue.enque(84);
		queue.enque(58);
		queue.enque(74);
		queue.enque(7);
		queue.enque(58);
		queue.printQueue();
		
		

	}

}
