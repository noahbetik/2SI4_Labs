package defaultpackage;

public class test {

	public static void main(String[] args) {

		String str1 = new String ("188610111412412056368346785406"); // 30 digits
		String str2 = new String ("25497197316781717347644145818"); // 29 digits
		String str3 = new String ("-118148129618172468331038474587"); // 30 digits
		String str4 = new String ("-33217168423372767345235384362"); // 29 digits
		String str5 = new String ("0"); // zero
		String strOne = new String ("0"); // zero
		

		HugeInteger h1 = new HugeInteger(str1);
		HugeInteger h2 = new HugeInteger(str2);
		HugeInteger h3 = new HugeInteger(str3);
		HugeInteger h4 = new HugeInteger(str4);
		HugeInteger h0 = new HugeInteger(str5);
		HugeInteger hOne = new HugeInteger(strOne);
		
		HugeInteger constructorRandomTest = new HugeInteger(23);
		
		//boolean cTestBasic = constructorTest(h1, str1, 1, 30);
		//boolean cTestNeg = constructorTest(h4, str4, -1, 29);
		//boolean cTestRand = cRTest(148); // test for size and completion only
		
		//System.out.println("testing constructors: " + cTestBasic + " " + cTestNeg + " " + cTestRand);
		
		boolean aT1 = addTest(h1, h2, "214107308729193773715990931224", 1, 30); // add positive to positive	
		boolean aT2 = addTest(h2, h3, "92650932301390750983394328769", -1, 29); //add negative to positive
		boolean aT3 = addTest(h4, h1, "155392942989039289023111401044", 1, 30); // add positive to negative
		boolean aT4 = addTest(h3, h4, "151365298041545235676273858949", -1, 30); // add negative to negative
		boolean aT5 = addTest(h1, h0, "188610111412412056368346785406", 1, 30); // add 0
		
		System.out.println("testing add function: " + aT1 + " " + aT2 + " " + aT3 + " " + aT4 + " " + aT5);
		
		
		
		//boolean sT1 = subtractTest(h1, h2, "163112914095630339020702639588", 1, 30); // subtract positive from positive
		//boolean sT2 = subtractTest(h2, h3, "143645326934954185678682620405", 1, 30); // subtract negative from positive
		//boolean sT3 = subtractTest(h4, h1, "221827279835784823713582169768", -1, 30); // subtract positive from negative
		//boolean sT4 = subtractTest(h3, h4, "84930961194799700985803090225", -1, 29); // subtract negative from negative
		//boolean sT5 = subtractTest(h1, h0, "188610111412412056368346785406", 1, 30); // subtract 0
		//boolean sT6 = subtractTest(h1, h1, "0", 1, 1); // subtract number from itself (=0)
		//boolean sT7 = subtractTest(h1, h1.subtract(new HugeInteger("188610111412412056368346785405")), "1", 1, 1); 
		// subtract one less to check digit reduction
		
		//System.out.println("testing subtract function: " + sT1 + " " + sT2 + " " + sT3 + " " + sT4 + " " + sT5 + " " + sT6 + " " + sT7);
		
		// boolean mT1 = multiplyTest(h1, h2, "", 1, x) // multiply positive by positive
		// boolean mT2 = multiplyTest(h1, h3, "", 1, x) // multiply positive by negative
		// boolean mT3 = multiplyTest(h4, h2, "", 1, x) // multiply negative by positive
		// boolean mT4 = multiplyTest(h3, h4, "", 1, x) // multiply negative by negative
		// boolean mT5 = multiplyTest(h1, hOne, "188610111412412056368346785406", 1, 30) // multiply by 1
		// boolean mT6 = multiplyTest(h1, h0, "0", 1, 1) // multiply by 0
		
		/*boolean cT1 = compareTest(h1, h2, 1); // +ve to +ve (larger)
		boolean cT2 = compareTest(h2, h1, -1); // +ve to +ve (smaller)
		boolean cT3 = compareTest(h1, h4, 1); // +ve to -ve (larger)
		boolean cT4 = compareTest(h4, h1, -1); // -ve to +ve (smaller)
		boolean cT5 = compareTest(h3, h4, 1); // -ve to -ve (larger)
		boolean cT6 = compareTest(h4, h3, -1); // -ve to -ve (larger)
		boolean cT7 = compareTest(h1, h1, 0); // equal +ve
		boolean cT8 = compareTest(h3, h3, 0); // equal -ve
		
		System.out.println("\ntesting compare function: " + cT1 + " " + cT2 + " " + cT3 + " " + cT4 + " " + cT5 + " " + cT6 + " " + cT7 + " " + cT8);*/

	}
	
	public static boolean cRTest(int n) {
		HugeInteger result = new HugeInteger(n);
		System.out.println();
		if (result.getSize() != n) {
			System.out.println("expected size " + n + " but was " + result.getSize());
			return false;
		}
		System.out.println(result.toString());
		
		return true;
	}
	
	public static boolean constructorTest(HugeInteger hTest, String source, int expSign, int expSize) {
		System.out.println();
		int[] t = hTest.getDigits();
		//System.out.println("checking digits");
		if (source.charAt(0) != '-') {
			for (int i=0; i<t.length; i++) {
				if (t[i] != Integer.parseInt(String.valueOf(source.charAt(i)))) {
					return false; 
				}
				System.out.print(t[i]);	
			}
		}
		
		else if (source.charAt(0) == '-') {
			for (int i=1; i<=t.length; i++) {
				if (t[i-1] != Integer.parseInt(String.valueOf(source.charAt(i)))) { 
					return false; 
				}
				System.out.print(t[i-1]);	
			}
		}
		
		System.out.println("\n" + source);
		
		System.out.println("actual sign: " + hTest.getSign());
		System.out.println("expected sign: " + expSign);
		if (hTest.getSign() != expSign) {
			return false;
		}
		
		System.out.println("actual size: " + hTest.getSize());
		System.out.println("expected size: " + expSize);
		if (hTest.getSize() != expSize) {
			return false;
		}
		
		return true;
		
	}
	
	public static boolean addTest(HugeInteger t1, HugeInteger t2, String exp, int expSign, int expSize) {
		HugeInteger sum = t1.add(t2);
		String actual = sum.toString();
		
		System.out.println("actual result: ");
		for (int i=0; i<sum.getSize(); i++) {
			//System.out.println("iteration" + i);
			System.out.print(actual.charAt(i));			
			if (exp.charAt(i) != actual.charAt(i)) {
				return false;
			}
		}
		
		System.out.println("\nexpected result: ");
		System.out.println(exp);
		System.out.println();
		
		System.out.println("actual sign: " + sum.getSign());
		System.out.println("expected sign: " + expSign);
		if (sum.getSign() != expSign) {
			System.out.println("wrong sign");
			return false;
		}
		
		System.out.println("actual size: " + sum.getSize());
		System.out.println("expected size: " + expSize);
		if (sum.getSize() != expSize) {
			System.out.println("wrong size");
			return false;
		}

		System.out.println();
		
		return true;
		
	}
	
	public static boolean subtractTest(HugeInteger t1, HugeInteger t2, String exp, int expSign, int expSize) {
		HugeInteger diff = t1.subtract(t2);
		String actual = diff.toString();
		
		System.out.println("actual result: ");
		for (int i=0; i<diff.getSize(); i++) {
			//System.out.println("iteration" + i);
			System.out.print(actual.charAt(i));
			if (exp.charAt(i) != actual.charAt(i)) {
				return false;
			}
		}
		
		System.out.println("\nexpected result: ");
		System.out.print(exp);
		
		System.out.println("\nactual sign: " + diff.getSign());
		System.out.println("expected sign: " + expSign);
		if (diff.getSign() != expSign) {
			return false;
		}
		
		System.out.println("actual size: " + diff.getSize());
		System.out.println("expected size: " + expSize);
		if (diff.getSize() != expSize) {
			return false;
		}
		
		System.out.println();
		
		return true;
		
	}
	
	public static boolean multiplyTest(HugeInteger t1, HugeInteger t2, String expVal, int expSign, int expSize) {
		
		HugeInteger actual = t1.multiply(t2);
		
		if (actual.getSign() != expSign || actual.getSize() != expSize) {
			return false;
		}
		
		String actStr = actual.toString();
		
		
		
		return true;
	}	
	
	public static boolean compareTest(HugeInteger h, HugeInteger comp, int expResult) {
		
		int actual = h.compareTo(comp);
		
		if (actual != expResult) {
			return false;
		}
		
		return true;
	}
	

}
