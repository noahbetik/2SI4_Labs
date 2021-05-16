package lab4;

import java.util.Random;

public class HashTableQuad { // all of the time and space complexities are the same as linear
	private Integer[] table;
	private int size; // M
	private int numKeys; 
	private int maxSize;
	private double maxLoad; // lambda
	
	private boolean isPrime(int n) { 
		if (n <= 1) {
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

    private int nextPrime(int n) {
    	if (n == 1) {
    		return 2;
    	}
    	
    	int prime = n;
    	boolean found = false;
    	
    	while (!found) {
    		prime++;
    		if (isPrime(prime)) {
    			found = true;
    		}
    	}
    	return prime;	
    }
	
	public HashTableQuad(int maxNum, double load) {
		maxLoad = load; // lambda = n/M so M = n / lambda
		size = (int) Math.ceil(maxNum / load);
		maxSize = maxNum;
		table = new Integer[nextPrime(size)];
	}
	
	public double getMaxLoadFactor() {
		return this.maxLoad;
	}
	
	public int getTableSize() {
		return this.size;
	}
	
	public int getNumKeys() {
		return this.numKeys;
	}
	
	public void insert(int n) {
		if (isIn(n)) {
			return;
		}
		
		int hx = n % size;
		
		for (int i=1; i<size; i++) {
			if (table[(hx+(i*i)) % size] == null) { // linear probe
				table[(hx+(i*i)) % size] = n;
				numKeys++;
				break;
			}
			else if (table[(hx+(i*i)) % size] == n) {
				return;
			}	
		}
		
		if (numKeys == maxSize) {
			rehash();
		}

	}
	
	private void rehash() {
		int newSize = nextPrime(2*this.size);
		Integer[] newTable = new Integer[newSize];
		
		for (int i=0; i<size; i++) {
			
			if (table[i] == null) {
				continue;
			}
			
			int hx2 = table[i] % newSize;
			
			for (int j=0; j<size; j++) {
				int spot = (hx2 + (j*j)) % newSize;
				if (newTable[spot] == null) { // linear probe
					newTable[spot] = table[i];
					break;
				}
			}
		}

		
		this.table = newTable;
		this.size = newSize;
	}
	
	public boolean isIn(int n) { // h(x) = x%M
		
		int hx = n % size;
		
		for (int i=0; i<size; i++) {
			if ((table[(hx+i) % size] != null) && (table[(hx+i) % size] == n)) { // linear probe
				return true;
			}
		}
		
		return false;
	}
	
	public int insertCount(int n) {
		int count = 0;
		
		int hx = n % size;

		for (int i=0; i<size; i++) {
			int spot = (hx + (i*i)) % size;
			if (table[spot] == null) { // linear probe
				table[spot] = n;
				numKeys++;
				count++;
				break;
			}
			else if (table[spot] == n) {
				count++;
				return count;
			}	
			count++;
		}

		if (numKeys == maxSize) {
			rehash();
		}
		
		return count;
	}
	
	public int searchCount(int n) {
		int searches = 0;
		
		return searches;
		
	}
	
	public static double probeSim(double lf) {
		System.out.println("Quadratic probing using load factor " + lf);
		Random r = new Random();
		int probes = 0;
		HashTableQuad tb = new HashTableQuad(100000, lf);
		for (int i=0; i<100000; i++) {
			probes += tb.insertCount(Math.abs(r.nextInt()));
			//System.out.println((double) (probes / tb.numKeys));
		}
		System.out.println(probes + " probes were needed");
		double avgProbes = probes/ ((double)tb.numKeys);
		System.out.println(avgProbes + " is the average number of probes for " + tb.numKeys + " elements");
		double theory = (0.5) * (1 + (1 / (1 - lf))); 
		System.out.println(theory + " is the theoretical number of probes");
		
		return avgProbes;
	}
	
	public void printKeys() {
		for (int i=0; i<size; i++) {
			System.out.print(table[i] + " ");
		}
	}
	
	public void printKeysAndIndexes() {
		for (int i=0; i<size; i++) {
			System.out.println(table[i] + " -- [" + i + "]");
		}
		System.out.println("\n");
	}

}