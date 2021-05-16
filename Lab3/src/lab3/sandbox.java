package lab3;

public class sandbox {
	public static void main(String[] args) {
		
		int[] vs = {6, 3, 8, 323, 7, 83, 6};
		int[] vs2 = {7, 4, 9, 324, 8, 84, 7};
		BSTSet yote = new BSTSet(vs);
		BSTSet yote2 = new BSTSet(vs2);
		BSTSet eck = yote.union(yote2);
		//eck.printNonRec();
		
		
		int[] empty = new int[10];
		System.out.println(empty[0]);
	}

}
