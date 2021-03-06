// Noah Betik

// 400246583
// betikn
// Mar 29, 2021

package lab5;

public class MaxHeap {
	
	private Integer[] items;
	private int size; // memory allocated
	private int numItems;
	
	public MaxHeap(int n) {
		items = new Integer[n];
		numItems = 0;
		size = n;
	}
	
	public MaxHeap(Integer[] vals) { // n*logn
		// n items to place into heap, logn checks for inversion
		numItems = vals.length;
		size = 2*(vals.length) + 1; // makes it less likely that recreation needs to be done right away
		items = new Integer[size];
		
		int i = 1;
		for (int val : vals) { // place each value from the input array into the heap
			items[i] = val;
			int j = i;
			while (j > 1) { // percolate up by checking for inversions from the element just inserted
				if (items[j] > items[j/2]) { // swap
					int temp = items[j];
					items[j] = items[j/2];
					items[j/2] = temp;
				}
				j /= 2; // integer division selects parent regardless of if the element is a left child or right child
			}
			
			i++;
		}
		
	}
	
	// public accessor methods
	
	public Integer[] getHeap() {
		return this.items;
	}
	
	public int getSizeArr() {
		return this.size;
	}
	
	public int getSizeHeap() {
		return this.numItems;
	}
	
	
	public void insert(int n) { // roughly constant, terminates when in proper position
		
		if (numItems == size-1) { // check if new heap needs to be allocated
			Integer[] temp = new Integer[2*size];
			for (int i=1; i<=numItems; i++) {
				temp[i] = items[i];
			}
			items = temp;
			size *= 2;
			items[numItems+1] = n;
			numItems++;
			
			int j = numItems-1;
			while (j > 1) {
				if (items[j] > items[j/2]) {
					int temp2 = items[j];
					items[j] = items[j/2];
					items[j/2] = temp2;
				}
				else {
					break;
				}
				
				j /= 2;
			}
		}
		
		else {
			items[numItems + 1] = n;
			numItems++;
			
			int j = numItems;
			while (j > 1) { // not sure why this doesn't work if not implemented linearly
				// but it doesn't work otherwise so I have no clue
				if (items[j] > items[j/2]) {
					int temp3 = items[j];
					items[j] = items[j/2];
					items[j/2] = temp3;
				}
				j--;
			}
		}
	}
	
	public int deleteMax() { // also probably linear probably bad
		// should in theory be logn worst case but for some reason I needed to check every element 
		
		int max = items[1]; // take max as the root of the heap
		items[1] = items[numItems]; // replace root with the last leaf
		items[numItems--] = null; // set the last leaf to null (empty)
		
		int i = 1; // establish parents & children
		int left = 2*i;
		int right = 2*i + 1;
		
		while(right <= numItems) { // percolate down
			
			//int larger = items[left] > items[right] ? left : right;
			
			/*if (items[i] < items[larger]) {
				int temp = items[i];
				items[i] = items[larger];
				items[larger] = temp;
			}*/ // why doesn't this work???
			
			
			if (items[i] < items[left]) { // compare children and swap as needed
				int temp = items[i];
				items[i] = items[left];
				items[left] = temp;
			}
			
			
			if (items[i] < items[right]) {
				int temp = items[i];
				items[i] = items[right];
				items[right] = temp;
			}
			
			i++;
			left = 2*i;
			right = 2*i + 1;
		}
		
		return max;
	}
	
	public String toString(){ 
		String result = new String();
		for (int i=1; i<=numItems; i++) {
			result += items[i];
			result += ",";
			
		}
		
		return result;
	}
	
	// parent = i
	// left child = 2i
	// right child = 2i + 1
	
	public static void heapsort(Integer[] toSort) { // nlogn in theory 
		// n^2 in this implementation because my deleteMin function is linear
		int length = toSort.length;
		MaxHeap heap = new MaxHeap(toSort);
		for (int i=0; i<length; i++) {
			toSort[i] = heap.deleteMax();
		}
	}

}
