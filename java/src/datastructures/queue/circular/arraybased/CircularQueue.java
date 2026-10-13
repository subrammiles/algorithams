package datastructures.queue.circular.arraybased;

public class CircularQueue {
	
	public static void main(String[] args) {
		Queue queue=new Queue(5);	
		queue.enQueue(1);
		queue.enQueue(2);
		queue.enQueue(3);
		queue.enQueue(4);
		queue.enQueue(5);
		
		queue.deQueue();
		queue.deQueue();
		queue.enQueue(98);
		queue.enQueue(68);
		queue.enQueue(68);
		queue.printArray();
		queue.print();
	
	}

}
