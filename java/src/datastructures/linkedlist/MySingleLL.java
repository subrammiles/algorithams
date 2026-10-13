package datastructures.linkedlist;

public class MySingleLL {
	int value;
	public MySingleLL next;

	public MySingleLL(int d) {
		this.value = d;
		next = null;
	}
	
	public MySingleLL(int d,MySingleLL ll) {
		this.value = d;
		this.next = ll;
	}
}
