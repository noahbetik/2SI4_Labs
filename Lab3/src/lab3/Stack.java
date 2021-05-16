package lab3;

public class Stack {
	
	private Node head;
	public int size;
	
	public boolean isEmpty() {
		return (head == null);
	}
	
	public void push(TNode t) {
		head = new Node(t, head);
		size++;
	}
	
	public TNode top() {
		TNode result = head.tn;
		return result;
		
	}
	
	public TNode pop() {
		TNode result = head.tn;
		head = head.next;
		size--;
		return result;
	}

}
