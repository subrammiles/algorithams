package datastructures.queue.deque;

public class DequeMain {

	public static void main(String[] args) {
		DequeArray dq = new DequeArray(4);

	    
	    dq.insertRear(75);
	    dq.insertRear(25);
	    System.out.println("Real : " + dq.getRear());
	    dq.deleteRear();
	    System.out.println("After delete rear : " + dq.getRear());

	    System.out.println("inserting element at front ");
	    dq.insertFront(77);

	    System.out.println("front element: " + dq.getFront());

	    dq.deleteFront();

	    System.out.println("After delete front  : " + +dq.getFront());

	}

}
