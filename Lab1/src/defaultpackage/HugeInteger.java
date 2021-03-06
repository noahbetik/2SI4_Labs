package defaultpackage;


public class HugeInteger {
	
	private int[] digits;
	private int sign = 0;
	private int size;
	
	public HugeInteger(String val) {
		try {
			this.size = val.length();
			
			if (val.charAt(0) == '-') { // if the string contains a negative sign, update the sign field accordingly
				this.digits = new int[this.size - 1]; // also decrease the size since the length of the string includes the negative sign
				this.sign = -1;
				this.size--;
				
				
				for (int n = 1; n<this.size+1; n++) {
					char x = val.charAt(n);
					try {
						int here = Integer.parseInt(String.valueOf(x)); // populate the integer array
						this.digits[n-1] = here;
					}
					catch(NumberFormatException nfe) {
						throw new NumberFormatException("String must only contain numerals after an optional negative sign");
					}
				}
			
			}
			
			else { // populate the array and fields if the string was positive
				this.sign = 1;
				this.digits = new int[this.size];
				for (int n = 0; n<this.size; n++) {
					char x = val.charAt(n);
					try {
						int here = Integer.parseInt(String.valueOf(x)); 
						this.digits[n] = here;
					}
					catch(NumberFormatException nfe) {
						throw new NumberFormatException("String must only contain numerals after an optional negative sign");
					}
				}	
			}
			
			while(this.digits[0] == 0 && this.size > 1) { // if there are extra zeroes at the front, remove them and shift digits
				for(int index = 0; index<this.size-1; index++) {
					this.digits[index] = this.digits[index+1];
				}
				this.size--; // update size accordingly
			}
			
			if(this.size == 1 && this.digits[0] == 0) {
				this.sign = 1;
			} // zero should be positive
		}
		
		catch (IllegalArgumentException e){
			throw new IllegalArgumentException("Please enter a string of decimal digits");
		}
	}
	
	public int[] getDigits() { // getter method for digits
		return this.digits;
	}
	
	public int getSize() { // getter method for size of number (number of digits)
		return this.size;
	}
	
	public int getSign() { // getter method for sign of number
		return this.sign;
	}
	
	public HugeInteger(int n) {	
		if (n <= 0) { // can't have a zero or negative number of digits
			throw new IllegalArgumentException("Size of HugeInteger cannot be negative or zero");
		}
		
		this.size = n;
		this.digits = new int[n]; 
		// first number cannot be zero
		this.digits[0] = (int)(Math.random() * (9 - 1 + 1) + 1); // Math.random() * (max - min + 1) + min 
		for (int len = 1; len<n; len++) {
			int rng = (int)(Math.random() * (9 - 0 + 1) + 0); // formula for random integer in a range
			this.digits[len] = rng;
		} 
		this.sign = (int)(Math.random() * (1 - 1 + 1) + 1); // randomize the sign
	}
	
	public HugeInteger(int[] d) { // bonus constructor to create a HugeInteger from a list of digits
		this.size = d.length;
		this.sign = 1;
		this.digits = d;
	}
	
	public HugeInteger add(HugeInteger h) {
		int large; // sizes of larger & smaller numbers
		int small;
		HugeInteger longest; // references to larger and smaller numbers
		HugeInteger shortest;
		
		if(this.size == h.size || this.size > h.size) {
			large = this.size;
			small = h.size;
			longest = this;
			shortest = h;
		}
		
		else {
			large = h.size;
			small = this.size;
			longest = h;
			shortest = this;
		}
		
		if (this.sign == 1 && h.sign == -1) { // outsource this operation to the subtract function
			HugeInteger flip = h;
			flip.sign = 1;
			return this.subtract(flip);
		}
		
		else if (this.sign == -1 && h.sign == 1) { // outsource this operation to the subtract function
			HugeInteger flip = this;
			flip.sign = 1;
			HugeInteger result = flip.subtract(h);
			
			if (flip.compareTo(h) == 1) {
				result.sign = -1;
			}
			else {
				result.sign = 1;
			}
			
			return result;
		} 
		
		HugeInteger sum = new HugeInteger(large+1); // create the HugeInteger with size one larger than largest number in case of end carry
		
		int i = large-1, j = small-1;
		int index = sum.size;
		int carry = 0;
		
		while(i>=0 && j>=0) { // add digits when both lists have digits
			sum.digits[index-1] = longest.digits[i] + shortest.digits[j] + carry;
			carry = 0;
			
			if  (sum.digits[index-1] > 9) { // carry mechanism
				sum.digits[index-1] -= 10;
				carry = 1;
			}
			
			index--;
			i--;
			j--;
		}
		
		while (i>=0 && index >= 0) { // add digits while only the largest number has digits
			sum.digits[index-1] = longest.digits[i] + carry;
			carry = 0;
			if  (sum.digits[index-1] > 9) {
				sum.digits[index-1] -= 10;
				carry = 1;
			}
			index--;
			i--;
		}
		
		if(index == 1 && carry == 1) { // end carry
			sum.digits[0] = 1;
		}
		
		else if (index == 1 && carry == 0) { // no end carry
			sum.digits[0] = 0;
			index--;
			int[] actualDigits = new int[large];
			for (int k=0; k<large; k++) {
				actualDigits[k] = sum.digits[k+1];
			}
			sum.digits = actualDigits;
			sum.size -= 1;
		}
		
		if (h.sign == -1 && this.sign == -1) { // assign sign based on operands' sign
			sum.sign = -1;
		}
		
		else {
			sum.sign = 1;
			}
		
		
		return sum;	
	}
	
	public HugeInteger subtract(HugeInteger h) {
		
		int large; // create reference to larger operand and its size for later use
		HugeInteger longest;
		if(this.size == h.size || this.size > h.size) {
			large = this.size;
			longest = this;
		}
		
		else {
			large = h.size;
			longest = h;
		}
		
		if (this.compareTo(h) == 0) { // if the two operands are the same, return 0
			return new HugeInteger("0");
		}
		
		if (this.sign == 1 && h.sign == -1) { // sign magic in order to make later algorithm work in all cases
			HugeInteger flip = h;
			flip.sign = 1;
			return this.add(flip); // subtracting a negative is like adding a positive
		}
		
		else if (this.sign == -1 && h.sign == -1) {
			HugeInteger flipH = h;
			HugeInteger flipT = this;
			flipH.sign = 1;
			flipT.sign = 1; // this is like subtracting the first operand from the second
			HugeInteger result = flipT.subtract(flipH); 
			// needs to be done this way because crossing over zero into negative from positive causes issues
			
			if (flipH.compareTo(flipT) == 1) {
				result.sign = 1; // compare magnitudes in order to have proper sign
			}
			else {
				result.sign = -1;
			}
			
			return result;
		}
		
		else if (this.sign == -1 && h.sign == 1) {
			HugeInteger flip = this;
			flip.sign = 1;
			HugeInteger result = flip.add(h);
			result.sign = -1;
			return result; // like adding two negative numbers
		}

		
		HugeInteger diff = new HugeInteger(large);
		
		int i = this.size - 1, j = h.size - 1;
		int index = diff.size;
		int carry = 0;
		
		
		if (this.compareTo(h) == 1) { // if the first number is larger, difference will be positive
			diff.sign = 1;
			
			while(i>=0 && j>= 0) { // add digits when both lists have digits
				int preResult = this.digits[i] - h.digits[j] - carry; // subtract operand and any borrowed values
				carry = 0;
				if (preResult < 0) {
					diff.digits[index-1] = 10 + preResult; // borrow from the next highest place if the pre-result is below zero
					carry = 1; // reflect this borrowing in the carry counter
				}
				else {
					diff.digits[index-1] = preResult; // if no borrowing needed, store as is
				}
				
				index--;
				i--;
				j--; // decrement indexing variables
			}
			
			while (index > 0) { // once one number is out of digits, check for cascading carries and compute remaining results
				int preResult = longest.digits[index-1] - carry;
				carry = 0;
				if (preResult < 0) { // same process as above
					diff.digits[index-1] = 10 + preResult;
					carry = 1;
				}
				else {
					diff.digits[index-1] = preResult;
				}
				index--;
			}
		}
		
		else { // if second number is larger, difference will be negative
			// can deal with this by subtracting the first from the second and then flipping the sign because of zero crossover issues
			
			HugeInteger flip = this;
			flip.sign = 1;
			diff = h.subtract(flip);
			diff.sign = -1;

		}
		
		// size adjustment
		
		int k = 0;
		int counter = 0; // see how many leading zeroes there are
		while(diff.digits[k] == 0) {
			counter++;
			k++;
		}
		
		if (counter > 0) { // remove leading zeroes if they exist
			int[] newDiff = new int[diff.size - counter]; // create digit array of the proper size
			for (int x=0; x<newDiff.length; x++) {
				newDiff[x] = diff.digits[x+counter]; // fill new array with values from original digit array
			}
			diff.digits = newDiff; // replace digit array in HugeInteger with updated array
			diff.size -= counter; // update size to match
		}

				
		return diff;
		
	}

	
	public HugeInteger multiply (HugeInteger h) {
		
		int large;
		int small;
		HugeInteger longest;
		HugeInteger shortest; // references to longer & shorter operands and their sizes
		
		if(this.size == h.size || this.size > h.size) { // assignments
			large = this.size;
			small = h.size;
			longest = this;
			shortest = h;
		}
		
		else {
			large = h.size;
			small = this.size;
			longest = h;
			shortest = this;
		}	
		
		if (h.digits[0] == 0 || this.digits[0] == 0) { // anything multiplied by zero is zero
			return new HugeInteger("0");
		}
		
		if (h.digits[0] == 1 && h.size == 1) { // anything multiplied by 1 is itself
			HugeInteger prod = this;
			if (this.sign == h.sign) {
				prod.sign = 1;
			}
			else {
				prod.sign = -1; // check to make sure proper sign is used by checking parity of operand signs
			}
			return prod;
		}
		
		if (this.digits[0] == 1 && this.size == 1) { // same as above but reversed
			HugeInteger prod = h;
			if (this.sign == h.sign) {
				prod.sign = 1;
			}
			else {
				prod.sign = -1;
			}
			return prod;
		}
		
		HugeInteger prod = new HugeInteger(this.size + h.size); // create the product with enough space for the maximum sized product
		
		for (int x=0; x<prod.size; x++) {
			prod.digits[x] = 0; // fill the digit array with zeroes to facilitate algorithm
		}
		
		int len = prod.size;
		int carry = 0;
		int xMag = 1;
		int yMag = 0;
		
		/* use napier's bones method to multiply
		 * n*m grid set up with a diagonal line running through each square
		 * mutiply the "x" and "y" for each box
		 * ones place goes below the diagonal, tens place goes above the diagonal
		 * sum up everything in a diagonal row
		 * */
		
		for (int i=large-1; i>=0; i--) {
			yMag = 0;
			for (int j=small-1; j>=0; j--) {
				int digitProduct = longest.digits[i] * shortest.digits[j]; // multiply "x" and "y"
				while (digitProduct > 9) { // if product is greater than 9, remove 10s until single digit is reached
					digitProduct -= 10;
					carry++; // update carry accordingly
				}
				
				int location = len - xMag - yMag; // calculates which diagonal sum the result belongs to
				
				prod.digits[location] += digitProduct; // place the single-digit product in the proper place
				while (prod.digits[location] > 9) {
					prod.digits[location] -= 10; // if this results in a value greater than 9, update the carry
					carry++;
				}
				prod.digits[location-1] += carry; // go to the next-most significant place and add the carry 
				carry = 0; // reset carry
				

				yMag++;
			}
			xMag++;
		}
		
		// calculate sign based on operand signs
		
		if ((this.sign == 1 && h.sign == 1) || (this.sign == -1 && h.sign == -1)) {
			prod.sign = 1;
		}
		
		else { 
			prod.sign = -1;
		}
		
		if(prod.digits[0] == 0) { // size adjustment if there is a leading zero
			int[] newProd = new int[len-1];
			for (int x=1; x<len; x++) {
				newProd[x-1] = prod.digits[x];
			}
			prod.digits = newProd;
			prod.size--;
		}
	
		return prod;
	}
	
	public int compareTo(HugeInteger h) { 
		if (this.sign > h.sign) { // check to see if one is negative and therefore smaller
			return 1;
		}
		
		else if (this.sign < h.sign) {
			return -1;
		}
		
		else { // check sizes to see if one is smaller
			if (this.sign == 1) {
				if (this.size > h.size) { // if signs are positive, smaller size means smaller number
					return 1;
				}
				if (this.size < h.size) {
					return -1;
				}
			}
			
			if (this.sign == -1) { // if signs are negative, smaller size means larger number
				if (this.size > h.size) {
					return -1;
				}
				if (this.size < h.size) {
					return 1;
				}
			}
		}
		
		for (int i=0; i<this.size; i++) { // check digit by digit from the most significant until a discrepancy is found
			if ((this.digits[i] > h.digits[i] && this.sign == 1) || (this.digits[i] < h.digits[i] && this.sign == -1)) {
				return 1;
			}
			else if ((this.digits[i] < h.digits[i] && this.sign == 1) || (this.digits[i] > h.digits[i] && this.sign == -1)) {
				return -1;
			}
		}
		
		return 0; // if no differences are found, return 0 (equal)
		
	}
	
	public String toString() {
		String result = new String();
		if (this.sign == -1) {
			result += "-";
		}
		
		for (int i=0; i<this.size; i++) {
			result += this.digits[i];
		}
		
		return result;
	}

}
