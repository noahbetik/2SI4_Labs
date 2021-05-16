// Noah Betik
// 400246583
// betikn
// Mar 29, 2021

package lab5;

public class sandbox {

	public static void main(String[] args) {
		Integer[] eg = {1,2,5,-3,-9};
		
		MaxHeap test = new MaxHeap(eg);
		MaxHeap.heapsort(eg);
		System.out.println(test.toString());

	}

}
