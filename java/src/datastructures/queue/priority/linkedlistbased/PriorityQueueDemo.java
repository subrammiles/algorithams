package datastructures.queue.priority.linkedlistbased;

public class PriorityQueueDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// Create a Priority Queue
	    // 7.4.5.6
	    Queue pq = new Queue(4, 1);
	    pq.push(5, 2);
	    pq.push(6, 3);
	    pq.push(7, 0);
	     
	    while (pq.isEmpty()==0) {
	        System.out.printf("%d ", pq.peek());
	        pq.pop();
	    }

	}

}
