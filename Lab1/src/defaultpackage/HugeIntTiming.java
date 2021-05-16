package defaultpackage;
import java.math.BigInteger;

public class HugeIntTiming {
	
	final static int MAXNUMINTS = 100;
	final static int MAXRUN = 2500; // change this so that the printed value on the screen is > 500

	public static void main(String[] args) {
		int n = 10000;
		
		// uncomment whichever test you're doing
		
		System.out.println("add test\n" + addTest(n) + "\n");
		//System.out.println("subtract test\n" + subtractTest(n) + "\n");
		//System.out.println("multiply test\n" + multiplyTest(n) + "\n");
		//System.out.println("comparison test\n" + compareTest(n) + "\n");
		
	}
	
	public static double addTest(int n) {
		
		HugeInteger huge1, huge2, huge3;
		BigInteger big1, big2, big3;
		long startTimeHuge, endTimeHuge, startTimeBig, endTimeBig;
		double runTimeHuge = 0.0;
		double runTimeBig = 0.0;
		
		for (int numInts=0; numInts<MAXNUMINTS; numInts++) {
			huge1 = new HugeInteger(n);
			huge2 = new HugeInteger(n);
			startTimeHuge = System.currentTimeMillis();
			for(int numRun=0; numRun<MAXRUN; numRun++) {
				huge3 = huge1.add(huge2);
			}
			endTimeHuge = System.currentTimeMillis();
			runTimeHuge += (double) (endTimeHuge - startTimeHuge) / ((double) MAXRUN);
			System.out.println(endTimeHuge - startTimeHuge);
			
			String h1 = huge1.toString();
			String h2 = huge2.toString();
			
			big1 = new BigInteger(h1);
			big2 = new BigInteger(h2);
			startTimeBig = System.currentTimeMillis();
			for(int numRun=0; numRun<MAXRUN; numRun++) {
				big3 = big1.add(big2);
			}
			endTimeBig = System.currentTimeMillis();
			runTimeBig += (double) (endTimeBig - startTimeBig) / ((double) MAXRUN);
			
		}
		
		runTimeHuge = runTimeHuge / ((double) MAXNUMINTS);
		System.out.println("huge " + runTimeHuge);
		
		runTimeBig = runTimeBig / ((double) MAXNUMINTS);
		System.out.println("big " + runTimeBig);
		
		return runTimeHuge - runTimeBig;

	}
	
public static double subtractTest(int n) {
		
		HugeInteger huge1, huge2, huge3;
		BigInteger big1, big2, big3;
		long startTimeHuge, endTimeHuge, startTimeBig, endTimeBig;
		double runTimeHuge = 0.0;
		double runTimeBig = 0.0;
		
		for (int numInts=0; numInts<MAXNUMINTS; numInts++) {
			huge1 = new HugeInteger(n);
			huge2 = new HugeInteger(n);
			startTimeHuge = System.currentTimeMillis();
			for(int numRun=0; numRun<MAXRUN; numRun++) {
				huge3 = huge1.subtract(huge2);
			}
			endTimeHuge = System.currentTimeMillis();
			runTimeHuge += (double) (endTimeHuge - startTimeHuge) / ((double) MAXRUN);
			System.out.println(endTimeHuge - startTimeHuge);
			
			String h1 = huge1.toString();
			String h2 = huge2.toString();
			
			big1 = new BigInteger(h1);
			big2 = new BigInteger(h2);
			startTimeBig = System.currentTimeMillis();
			for(int numRun=0; numRun<MAXRUN; numRun++) {
				big3 = big1.subtract(big2);
			}
			endTimeBig = System.currentTimeMillis();
			runTimeBig += (double) (endTimeBig - startTimeBig) / ((double) MAXRUN);
			
		}
		
		runTimeHuge = runTimeHuge / ((double) MAXNUMINTS);
		System.out.println("huge " + runTimeHuge);
		
		runTimeBig = runTimeBig / ((double) MAXNUMINTS);
		System.out.println("big " + runTimeBig);
		
		return runTimeHuge - runTimeBig;

	}

	public static double multiplyTest(int n) {
		
		HugeInteger huge1, huge2, huge3;
		BigInteger big1, big2, big3;
		long startTimeHuge, endTimeHuge, startTimeBig, endTimeBig;
		double runTimeHuge = 0.0;
		double runTimeBig = 0.0;
		
		for (int numInts=0; numInts<MAXNUMINTS; numInts++) {
			huge1 = new HugeInteger(n);
			huge2 = new HugeInteger(n);
			startTimeHuge = System.currentTimeMillis();
			for(int numRun=0; numRun<MAXRUN; numRun++) {
				huge3 = huge1.multiply(huge2);
			}
			endTimeHuge = System.currentTimeMillis();
			runTimeHuge += (double) (endTimeHuge - startTimeHuge) / ((double) MAXRUN);
			System.out.println(endTimeHuge - startTimeHuge);
			
			String h1 = huge1.toString();
			String h2 = huge2.toString();
			
			big1 = new BigInteger(h1);
			big2 = new BigInteger(h2);
			startTimeBig = System.currentTimeMillis();
			for(int numRun=0; numRun<MAXRUN; numRun++) {
				big3 = big1.multiply(big2);
			}
			endTimeBig = System.currentTimeMillis();
			runTimeBig += (double) (endTimeBig - startTimeBig) / ((double) MAXRUN);
			
		}
		
		runTimeHuge = runTimeHuge / ((double) MAXNUMINTS);
		System.out.println("huge " + runTimeHuge);
		
		runTimeBig = runTimeBig / ((double) MAXNUMINTS);
		System.out.println("big " + runTimeBig);
		
		return runTimeHuge - runTimeBig;
	
	}
	
public static double compareTest(int n) {
		
		HugeInteger huge1, huge2, huge3;
		BigInteger big1, big2, big3;
		long startTimeHuge, endTimeHuge, startTimeBig, endTimeBig;
		double runTimeHuge = 0.0;
		double runTimeBig = 0.0;
		
		for (int numInts=0; numInts<MAXNUMINTS; numInts++) {
			huge1 = new HugeInteger(n);
			huge2 = new HugeInteger(n);
			startTimeHuge = System.currentTimeMillis();
			for(int numRun=0; numRun<MAXRUN; numRun++) {
				huge1.compareTo(huge2);
			}
			endTimeHuge = System.currentTimeMillis();
			runTimeHuge += (double) (endTimeHuge - startTimeHuge) / ((double) MAXRUN);
			System.out.println(endTimeHuge - startTimeHuge);
			
			String h1 = huge1.toString();
			String h2 = huge2.toString();
			
			big1 = new BigInteger(h1);
			big2 = new BigInteger(h2);
			startTimeBig = System.currentTimeMillis();
			for(int numRun=0; numRun<MAXRUN; numRun++) {
				big1.compareTo(big2);
			}
			endTimeBig = System.currentTimeMillis();
			runTimeBig += (double) (endTimeBig - startTimeBig) / ((double) MAXRUN);
			
		}
		
		runTimeHuge = runTimeHuge / ((double) MAXNUMINTS);
		System.out.println("huge " + runTimeHuge);
		
		runTimeBig = runTimeBig / ((double) MAXNUMINTS);
		System.out.println("big " + runTimeBig);
		
		return runTimeHuge - runTimeBig;
	
	}

}
