package lab3;

import java.util.NoSuchElementException;

public class BSTSet {
	
	TNode root;
	
	BSTSet(){ // Θ(1)
		root = null;
	}
	// space complexity Θ(1)
	
	BSTSet(int[] input){ // Θ(n) is dominant term
		
		if (input.length >= 1) { // check & statement is Θ(1)
			root = new TNode(input[0], null, null); // set the root to be the actual first value
		}
		
		else {
			root = null;
			return;
		}
		
		TNode here = root;
		int i = 1;
		
		while (i<input.length) { // n values to add, 1-3 operations depending on case (constant), therefore Θ(n)
			
			if (this.isIn(input[i])) { // remove duplicate
				i++;
				continue;	
			}
			
			else if(input[i] < here.element && here.left == null) { // place in left
				here.left = new TNode(input[i], null, null);
				i++;
				here = root;
				continue;
			}
			
			else if(input[i] > here.element && here.right == null) { // place in right
				here.right = new TNode(input[i], null, null);
				i++;
				here = root;
				continue;
			}
			
			else if(input[i] < here.element) { // go left if smaller
				here = here.left;
				continue;
			}
			
			else if(input[i] > here.element) { // go right if bigger
				here = here.right;
				continue;
			} 
			
		}
	}
	// space complexity Θ(n)
	
	public TNode getRoot() { // Θ(1)
		return this.root;
	}
	// space complexity Θ(1)
	
	public boolean isIn(int v) { // Θ(log(n)), cuts problem in half for each branch downwards on average
		TNode here = root;
		
		if (root == null) { 
			return false;
		}
		
		while (here.left != null || here.right != null) { // Θ(log(n)), each iteration reduces the elements to check by roughly half			
			if (here.element == v) { // found!
				return true;
			}
			
			else if(v < here.element) { // go left if smaller
				if (here.left == null) {
					return false;
				}
				here = here.left;
			}
			
			else if(v > here.element) { // go right if bigger
				if (here.right == null) {
					return false;
				}
				here = here.right;
			} 
		}
		
		if (here.element == v) { // found!
			return true;
		}
		
		return false;
	}
	// space complexity Θ(1), references always rewritten
	
	public int findMin(TNode t) { // Θ(log)n)) on average, could be Θ(n) in worst case 
		if (t == null) {
			throw new NoSuchElementException();
		}
		
		while (t.left != null) {
			t = t.left;
		}
		return t.element;
	}
	// space complexity Θ(1)
	
	public int findMax(TNode t) { // Θ(log)n)) on average, could be Θ(n) in worst case 
		if (t == null) {
			throw new NoSuchElementException();
		}
		
		while (t.right != null) {
			t = t.right;
		}
		return t.element;
	}
	// space complexity Θ(1)
	
	public void add(int v) { // Θ(log(n)), cuts problem in half for each branch downwards on average
		if (isIn(v)) {
			return;
		}
		
		TNode here = root;
		
		while (here.left != null || here.right != null) { // Θ(log(n)), cuts problem in half for each branch downwards on average
			
			if(v < here.element) { // go left if smaller
				here = here.left;
			}
			
			else { // go right if bigger
				here = here.right;
			} 
		}
		
		// continue going downwards while only one branch is null
		
		if(v < here.element) { // go left if smaller
			here.left = new TNode(v, null, null); // create the node
		}
		
		else { // go right if bigger
			here.right = new TNode(v, null, null);
		} 
	}
	// space complexity Θ(1)
	
	public boolean remove(int v) { // Θ(log(n))
		
		if (!isIn(v)) { // check if element not in set, Θ(log(n))
			return false;
		}
				
		TNode here = root; // traversal node
		TNode prev = null; // previous node visited to use for re-referencing
		int dir = 0; // tracker to see did I go left or right
		

		while (here.left != null || here.right != null) {
			// Θ(log(n)), cuts problem in half for each branch downwards on average
			// constant number of operations inside each loop
			
			if (here.element == v) { // found!
				
				if(here.left == null && here.right == null) { // its a leaf
					if (dir == 1) {
						prev.right = null; // remove parent references
					}
					if (dir == -1) {
						prev.left = null;
					}
				}
				
				else if(here.left == null) { // one child (right)
					if (dir == 1) {
						prev.right = here.right; 
					}
					if (dir == -1) {
						prev.left = here.right;
					} // establish grandparent->child relation as parent->child 
				}
				
				else if(here.right == null) { // one child (left)
					if (dir == 1) {
						prev.right = here.left;
					}
					if (dir == -1) {
						prev.left = here.left; // same
					}
				}
				
				else { // two children
					int replace = findMin(here.right); // find replacement leaf
					remove(replace); // delete the leaf
					here.element = replace; // set the node to be deleted as the replacement value
				} // Θ(log(n)) + Θ(log(n)) + Θ(log(n)) bc method called inside and also findMin is Θ(log(n))
				// therefore Θ(log(n))
				
				return true;
			}
			
			// keep traversing if node not found
			
			else if(v < here.element) { // go left if smaller
				prev = here;
				dir = -1;
				here = here.left;
			}
			
			else if(v > here.element) { // go right if bigger
				prev = here;
				dir = 1;
				here = here.right;
			} 
		}
		
		return false;
	}
	// space complexity Θ(1)
	
	public BSTSet union(BSTSet s) { // Θ(n) because I need to visit every node to collect all values
		
		int[] vals = new int[this.size() + s.size()]; // get all the values, constructor will deal with duplicates
		int i = 0; // index variable
		Stack stack = new Stack(); // LL implementation of stack, super zoomy Θ(1)
		
		TNode here = this.root; // traversal node
		
		while (here != null || stack.size > 0) { // outer loop to shift one right branch over at a time
			while (here != null) { // inner loop to traverse down a left branch of a subtree
				stack.push(here); // Θ(1)
				here = here.left;
			}
			// since here is null, at the end of a left branch, therefore pop
			here = stack.pop(); // put the popped value into the array, Θ(1)
			vals[i] = here.element;
			i++; // increment array counter
			here = here.right; 
		}
		
		here = s.root; // do it again for s
		
		while (here != null || stack.size > 0) { // outer loop to shift one right branch over at a time
			while (here != null) { // inner loop to traverse down a left branch of a subtree
				stack.push(here);
				here = here.left;
			}
			// since here is null, at the end of a left branch, therefore pop
			here = stack.pop(); // put the popped value into the array
			vals[i] = here.element;
			i++; // increment array counter
			here = here.right; 
		}
		// space complexity Θ(log(n)) on average or O(n) worst case
		// there will never be more than a branch of elements in the stack
		// if tree is balanced, log(n)
		// if tree is unbalanced, could be up to n (straight line down)

		return new BSTSet(vals);
	}
	
	public BSTSet intersection(BSTSet s) { // Θ(n) because i need to visit every node and check all values
		
		int[] vals = new int[this.size() + s.size()]; // get all the values, constructor will deal with duplicates
		int i = 0; // index variable
		Stack stack = new Stack();
		
		TNode here = this.root;
		
		while (here != null || stack.size > 0) { // outer loop to shift one right branch over at a time
			while (here != null) { // inner loop to traverse down a left branch of a subtree
				stack.push(here);
				here = here.left;
			}
			// since here is null, at the end of a left branch, therefore pop
			here = stack.pop(); // put the popped value into the array
			
			if (s.isIn(here.element)){
				vals[i] = here.element;
				i++; // increment array counter IF value is in s
			}
			
			here = here.right; 
		}
		
		if (vals[0] == 0 && vals[1] == 0) { // if no values were added to the vals array
			return new BSTSet();
		}
		
		return new BSTSet(vals); // return BSTSet with values if they exist
	}
	// space complexity Θ(log(n)) on average or O(n) worst case
	// there will never be more than a branch of elements in the stack
	// if tree is balanced, log(n)
	// if tree is unbalanced, could be up to n (straight line down)
	
	public BSTSet difference(BSTSet s) { // you guessed it, Θ(n)
		
		int[] vals = new int[this.size() + s.size()]; // get all the values, constructor will deal with duplicates
		int i = 0; // index variable
		Stack stack = new Stack();
		
		TNode here = this.root;
		
		while (here != null || stack.size > 0) { // outer loop to shift one right branch over at a time
			while (here != null) { // inner loop to traverse down a left branch of a subtree
				stack.push(here);
				here = here.left;
			}
			// since here is null, at the end of a left branch, therefore pop
			here = stack.pop(); // put the popped value into the array
			
			if (!s.isIn(here.element)){
				vals[i] = here.element;
				i++; // increment array counter IF value is in s
			}
			
			here = here.right; 
		}
		
		if (vals[0] == 0 && vals[1] == 0) { // if no values were added to the vals array
			return new BSTSet();
		}
		
		return new BSTSet(vals);
	}
	// space complexity Θ(log(n)) on average or O(n) worst case
	// there will never be more than a branch of elements in the stack
	// if tree is balanced, log(n)
	// if tree is unbalanced, could be up to n (straight line down)
	
	public int size() { // Θ(n), literally need to go find every node
		// this would be so much better as a instance field updated upon add/remove
		int size = 0;
		
		TNode here = root;
		Stack stack = new Stack();
		
		while (here != null || stack.size > 0) { // outer loop to shift one right branch over at a time
			while (here != null) { // inner loop to traverse down a left branch of a subtree
				stack.push(here);
				here = here.left;
			}
			// since here is null, at the end of a left branch, therefore pop
			here = stack.pop(); 
			size++; // increment size every time an element popped
			here = here.right; 
		}
		
		return size;
	}
	// space complexity Θ(log(n)) on average or O(n) worst case
	// there will never be more than a branch of elements in the stack
	// if tree is balanced, log(n)
	// if tree is unbalanced, could be up to n (straight line down)
	
	public int height() { // Θ(n), again need to visit every node to make sure
		if (root == null) {
			return -1;
		}
		
		TNode here = root;
		Stack stack = new Stack();
		int height = 0;
		int maxHeight = 0;
		
		// use a stack to track depth, max size of stack is height
		
		while (here != null || stack.size > 0) { // outer loop to shift one right branch over at a time
			while (here != null) { // inner loop to traverse down a left branch of a subtree
				stack.push(here);
				height++; // update current depth
				if (height > maxHeight) {
					maxHeight = height; // if current depth is deeper than deepest found so far, update 
				}
				
				here = here.left;
			}
			here = stack.pop(); 
			height--; // update current depth
			here = here.right;
		}
		
		return maxHeight;
	}
	// space complexity Θ(log(n)) on average or O(n) worst case
	// there will never be more than a branch of elements in the stack
	// if tree is balanced, log(n)
	// if tree is unbalanced, could be up to n (straight line down)
	
	public void printBSTSet() { // Θ(1)
		if (root == null) {
			System.out.println("The set is empty");
		}
		else {
			System.out.print("The set elements are: ");
			printBSTSet(root);
			System.out.println("\n");
		}	
	}
	// space complexity Θ(1), no local variables declared
	
	public void printBSTSet(TNode t) { // Θ(n)
		if (t != null) {
			printBSTSet(t.left);
			System.out.println(" " + t.element + ", ");
			printBSTSet(t.right);
		}
	}
	// space complexity Θ(1), no local variables declared
	
	public void printNonRec() { // Θ(n)
		Stack stack = new Stack();
		TNode here = root;
		
		if (root == null) {
			return;
		}

		
		while (here != null || stack.size > 0) { // outer loop to shift one right branch over at a time
			while (here != null) { // inner loop to traverse down a left branch of a subtree
				stack.push(here);
				here = here.left;
			}
			// since here is null, at the end of a left branch, therefore pop
			here = stack.pop(); // put the popped value into the array
			System.out.println(here.element);
			here = here.right; 
		}
	}
	// space complexity Θ(log(n)) on average or O(n) worst case
	// there will never be more than a branch of elements in the stack
	// if tree is balanced, log(n)
	// if tree is unbalanced, could be up to n (straight line down)
	
	public void printLevelOrder() { // Θ(n)
		Queue q = new Queue(this.size());
		
		q.enq(root); // enqueue the root
		TNode here = root; // traversal variable
		
		while (!q.isEmpty()) {
			here = q.deq(); // dequeue front of line (last node visited)
			System.out.println(here); // print it
			if (here.left != null) {
				q.enq(here.left); // enqueue left if available
			}
			if (here.right != null) { // enqueue right if available
				q.enq(here.right);
			}
		} // done once queue is empty
		
	}
	// space complexity O(n) 
	// for a perfect binary tree, the last iteration will have half the nodes in the queue, therefore n/2 = n

}
