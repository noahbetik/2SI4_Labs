package lab3;

public class Queue {
	TNode[] vals; 
	int end = 1;
	int front = 0;
	
	// empty when front == end
	// full when items == vals.length - 1
	
	public Queue(int n) {
		vals = new TNode[n];
	}
	
	public int getSize() {
		if (front <= end) {
			return (end-front);
		}
		else {
			return (vals.length + end - front);
		}
	}
	
	public boolean isFull() {
		return (getSize() == vals.length - 1);
	}
	
	public boolean isEmpty() {
		return (front == end);
	}
	
	public boolean enq(TNode t) {
		if (isFull()) {
			return false;
		}
		else {
			vals[end] = t;
			end = (end+1) % vals.length;
			return true;
		}
	}
	
	public TNode deq() {
		TNode t;
		if (isEmpty()) {
			return null;
		}
		else {
			t = vals[front];
			vals[front] = null;
			if (++front == vals.length) {
				front = 0;
			}
			return t;
		}
	}

}
