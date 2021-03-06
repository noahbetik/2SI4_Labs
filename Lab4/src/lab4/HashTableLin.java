package lab4;

import java.util.Random;

public class HashTableLin {
	private Integer[] table;
	private int size; // M
	private int numKeys; 
	private int maxSize;
	private double maxLoad; // lambda
	
	private boolean isPrime(int n) { // prime checking method
		// θ(log n), for loop is main driver which increments i linearly but the condition is that i^2 < n
		if (n <= 1) { // simple edge cases
			return false;
		}
		
		if (n <= 3) {
			return true;
		}
		
		if (n % 2 == 0 || n % 3 == 0) {
			return false;
		}
		
		for(int i=5; i*i <= n; i+=6) {
			if (n % i == 0 || n % (i + 2) == 0) {
				return false;
			}
		}
		return true;

	} 

    private int nextPrime(int n) { // θ(n^4) according to research? big yikes
    	// roughly constant time for SMALL numbers
    	if (n == 1) { // edge case
    		return 2;
    	}
    	
    	int prime = n;
    	boolean found = false;
    	
    	while (!found) { // check all numbers after a given value to see if the next one is prime
    		prime++;
    		if (isPrime(prime)) { // this should really not be used for big numbers
    			found = true;
    		}
    	}
    	return prime;	
    }
	
	public HashTableLin(int maxNum, double load) { // θ(1) time excluding prime number generation
		maxLoad = load; // lambda = n/M so M = n / lambda
		size = (int) Math.ceil(maxNum / load);
		maxSize = maxNum;
		table = new Integer[nextPrime(size)];
		
		// space complexity θ(n), need to allocate array for the number of elements
	}
	
	public double getMaxLoadFactor() { // public accessor methods -- θ(1) for all
		return this.maxLoad;
	}
	
	public int getTableSize() {
		return this.size;
	}
	
	public int getNumKeys() {
		return this.numKeys;
	}
	
	public void insert(int n) { // θ(1) on average, θ(n) on worst if has table full or almost full
		
		int hx = n % size;
		
		for (int i=1; i<size; i++) {
			if (table[(hx+i) % size] == null) { // linear probe
				table[(hx+i) % size] = n;
				numKeys++;
				break;
			}
			else if (table[(hx+i) % size] == n) {
				return;  // if the value is found, get outta here
			}
		}
		
		if (numKeys == maxSize) {
			System.out.println("hi there unfortunately we are all out of avocados");
			rehash();
		}

	}
	
	private void rehash() { // θ(n) need to rehash every single key
		int newSize = nextPrime(2*this.size);
		Integer[] newTable = new Integer[newSize];
		
		for (int i=0; i<size; i++) {
			
			if (table[i] == null) {
				continue;
			}
			
			int hx2 = table[i] % newSize;

			for (int j=0; j<size; j++) {
				if (newTable[(hx2+j) % newSize] == null) { // linear probe
					newTable[(hx2+j) % newSize] = table[i];
					break;
				}
			}
		}
		this.table = newTable;
		this.size = newSize;
		
		// θ(n) space complexity, need to allocate new array of at least 2*n size
	}
	
	public boolean isIn(int n) { // θ(n) as worst case, θ(1) as best and avg case
		// possible to do it more efficiently -- only check until next null value
		// didn't have time to get it to work
		
		int hx = n % size;
		
		for (int i=0; i<size; i++) { // go through values until at end of table
			if (table[(hx+i) % size] != null && table[(hx+i) % size] == n) { // linear probe
				return true; // return early if the value found
			}
		}
		
		return false;
	}
	
	
	public int insertCount(int n) { // θ(1) on average, especially if lambda is less than 0.5
		// may begin to creep up to θ(n) for higher values of lambda
		int count = 0; // counter for number of probes
		
		int hx = n % size; // hash function

		for (int i=0; i<size; i++) { // linear probe
			if (table[(hx+i) % size] == null) { // if null position is found, insert here
				table[(hx+i) % size] = n;
				numKeys++; // keep track of number of keys in the table!
				count++; // increment the probe
				break; // exit early
			}
			else if (table[(hx+i) % size] == n) {
				count++; // if the value is found, return the number of probes needed to get there
				return count;
			}
			count++; // increment number of probes if nothing interesting happens
		}

		if (numKeys == maxSize) {
			rehash(); // if adding the key will exceed the value of lambda, rehash the table
		}
		
		return count; // return total number of probes
	}
	
	public int searchCount(int n) {
		int searches = 0;
		
		return searches;
		
	}
	
/*	public static double probeSim(double lf) {
		System.out.println("Linear probing using load factor " + lf);
		Random r = new Random();
		int probes = 0;
		HashTableLin tb = new HashTableLin(100000, lf);
		for (int i=0; i<100000; i++) {
			probes += tb.insertCount(Math.abs(r.nextInt()));
			System.out.println((double) (probes / tb.numKeys));
		}
		System.out.println(probes + " probes were needed");
		double avgProbes = probes/ ((double)tb.numKeys);
		System.out.println(avgProbes + " is the average number of probes for " + tb.numKeys + " elements");
		double theory = (0.5) * (1 + (1 / (1 - lf)));
		System.out.println(theory + " is the theoretical number of probes");

		return avgProbes;
	}*/
	
	public void printKeys() { // θ(n), gotta print everything
		for (int i=0; i<size; i++) {
			System.out.print(table[i] + " ");
		}
	}
	
	public void printKeysAndIndexes() { // θ(n), same as above
		for (int i=0; i<size; i++) {
			System.out.println(table[i] + " -- [" + i + "]");
		}
		System.out.println("\n");
	}

}
