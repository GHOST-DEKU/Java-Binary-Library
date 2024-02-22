package classes;

// Class to represent a binary number

class myBinaryNumber {
    int[] binaryNumber; // Array to store binary number
    
    // Constructor to initialize binary number of size n with all bits as 0
    myBinaryNumber(int n) {
        binaryNumber = new int[n];
        for(int i = 0; i < n; i++) {
            binaryNumber[i] = 0;
        }
    }

    // Constructor to initialize binary number from a string representation
    myBinaryNumber(String S) {
        binaryNumber = new int[S.length()];
        if (S.length() == 0) {
            binaryNumber[0] = 0;
        }
        else {
            for (int i = 0; i < S.length(); i++) {
                binaryNumber[i] = Integer.parseInt(Character.toString(S.charAt(i)));
            }
        }
    }

    // Method to get the size of the binary number
    public int getSize() {
        return binaryNumber.length;
    }

    // Method to get the bit at position p
    public int getBit(int p) {
        if (p < getSize()) {
            return binaryNumber[p];
        }
        else {
            System.err.println(p + " is out of bound");
            return -1;
        }
    }

    // Method to set the bit at position p to b (0 or 1)
    public void setBit(int p, int b) {
        if ((b == 0 || b == 1) && p < getSize()) {
            binaryNumber[p] = b;
        }
        else if (p >= getSize()) {
            System.err.println(p+" is out of the bound");
        }
        else {
            System.err.println(b+" is not a valid binary number");
        }
    }

    // Method to print the binary number
    public void printNumber() {
        for(int i = 0; i < getSize(); i++) {
            System.out.print(binaryNumber[i]);
        }
        System.out.println();
    }

    // Method to convert binary array to decimal number
    public static int binaryToDecimal(int[] binary) {
        int decimal = 0;
        for(int i = binary.length - 1; i >= 0; i--) {
            decimal += binary[binary.length - 1 - i] * Math.pow(2, i);
        }
        return decimal;
    }

    // Method to print the decimal representation of the binary number
    public void printDecimalNumber() {
        System.out.println(binaryToDecimal(binaryNumber));
    }
    
    // Method to get a part of the binary number from start to end index
    public myBinaryNumber getPart(int start, int end) {
        if (start < 0 || end > this.getSize() || start > end) {
            throw new IllegalArgumentException("Invalid start or end index");
        }
        int size = end - start;
        myBinaryNumber part = new myBinaryNumber(size);
        for (int i = 0; i < size; i++) {
            part.setBit(i, this.getBit(start + i));
        }
        return part;
    }
}

// Class to operate on binary numbers
abstract class binaryOperations {
    // Abstract method for binary multiplication to be implemented by subclasses
    abstract public myBinaryNumber binaryMultiplication(myBinaryNumber a, myBinaryNumber b);
    
    // Method to perform binary addition operation
    public myBinaryNumber binaryAddition(myBinaryNumber a, myBinaryNumber b) {
        // Determine the size for the result binary number. It should be the larger of a.size or b.size + 1 to accommodate carry.
        int maxSize;
        if (a.getSize() > b.getSize()) {
            maxSize = a.getSize();
        } else {
            maxSize = b.getSize();
        }
        myBinaryNumber result = new myBinaryNumber(maxSize + 1);

        // Adjusting the size of binary numbers to match before addition
        if (a.getSize() > b.getSize()) {
            b = shiftDigits.shiftRight(a.getSize() - b.getSize(), b);
        } else if (a.getSize() < b.getSize()) {
            a = shiftDigits.shiftRight(b.getSize() - a.getSize(), a);
        }

        int carry = 0; // To hold the carry value
        int sum = 0; // To hold the sum of bits
        int i = maxSize - 1; // Index for iteration
        while (i >= 0) {
            int A;
            int B;
            // Adjusting bit values based on size differences
            if (a.getSize() > b.getSize()) {
                A = a.getBit(i);
                B = 0;
            }
            else if (a.getSize() < b.getSize()) {
                A = 0;
                B = b.getBit(i);
            }
            else {
                A = a.getBit(i);
                B = b.getBit(i);
            }
            sum = A + B + carry; // Calculating sum of bits and carry
            // Setting result bit and carry based on sum value
            switch (sum) {
                case 0:
                    result.setBit(i+1, 0);
                    carry = 0;
                    break;
                case 1:
                    result.setBit(i+1, 1);
                    carry = 0;
                    break;
                case 2:
                    result.setBit(i+1, 0);
                    carry = 1;
                    break;
                case 3:
                    result.setBit(i+1, 1);
                    carry = 1;
                    break;
                default:
                    throw new AssertionError();
            }
            i--;
        }
        result.setBit(0, carry); // Setting the final carry bit
        return BinaryUtils.truncateBinaryNumber(result);
    }
}

class binaryMultiplicationNaive extends binaryOperations {
    
    // Variable to store the runtime of the binaryMultiplication method
    long lastRuntime;

    // Method to perform naive binary multiplication
    public myBinaryNumber binaryMultiplication(myBinaryNumber a, myBinaryNumber b) {
        long startTime = System.nanoTime(); // Start time measurement

        myBinaryNumber result = new myBinaryNumber(a.getSize() + b.getSize()); // Resultant binary number
        int j = b.getSize() - 1; // Index for iterating through second binary number
        myBinaryNumber temp; // Temporary binary number for intermediate results
        do { 
            int i = a.getSize() - 1; // Index for iterating through first binary number
            temp = new myBinaryNumber(a.getSize() + 1); // Temporary binary number for current iteration

            while (i >= 0) { 
                // Setting bit in temporary binary number based on multiplication result
                if (a.getBit(i) == 1 && b.getBit(j) == 1) {
                    temp.setBit(i+1, 1);
                }
                else if (a.getBit(i) == 0 || b.getBit(j) == 1){
                    temp.setBit(i+1, 0);
                }
                i--;
            }

            temp = shiftDigits.shiftLeft(temp, b.getSize() - j -1); // Shifting temporary binary number

            result = binaryAddition(result, temp); // Adding temporary result to final result
            j--;
        } while (j >= 0);

        long endTime = System.nanoTime(); // End time measurement
        lastRuntime = endTime - startTime; // Calculate and store runtime

        return BinaryUtils.truncateBinaryNumber(result);
    }

    // // Method to get the last runtime of the binaryMultiplication method
    // public long getLastRuntime() {
    //     return lastRuntime;
    // }
}

class shiftDigits {
    // Method to shift binary number to the right
    public static myBinaryNumber shiftRight(int shifts, myBinaryNumber num) {
        myBinaryNumber result = new myBinaryNumber(num.getSize() + shifts); // Resultant binary number after shift
        for (int i = 0; i < num.getSize(); i++) {
            result.setBit(i + shifts, num.getBit(i)); // Shifting bits
        }
        for (int j = 0; j < shifts; j++) {
            result.setBit(j, 0); // Setting initial bits to 0
        }
        return result;
    }
    // Method to shift binary number to the left
    public static myBinaryNumber shiftLeft(myBinaryNumber num, int shifts) {
        myBinaryNumber result = new myBinaryNumber(num.getSize() + shifts); // Resultant binary number after shift
        for (int i = 0; i < num.getSize(); i++) {
            result.setBit(i, num.getBit(i)); // Shifting bits
        }
        for (int j = num.getSize(); j < num.getSize() + shifts; j++) {
            result.setBit(j, 0); // Setting trailing bits to 0
        }
        return result;
    }
}

class BinaryUtils {
    // Converts a myBinaryNumber instance to its String representation
    static String binaryNumberToString(myBinaryNumber binary) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < binary.getSize(); i++) {
            sb.append(binary.getBit(i));
        }
        return sb.toString();
    }
    // Truncate the initial zeroes
    static myBinaryNumber truncateBinaryNumber(myBinaryNumber binary) {
        int firstNonZero = 0;
        while (firstNonZero < binary.getSize() && binary.getBit(firstNonZero) == 0) {
            firstNonZero++;
        }
        if (firstNonZero == binary.getSize()) {
            return new myBinaryNumber(1); // Return a binary number with a single 0 if all bits are 0
        }
        int newSize = binary.getSize() - firstNonZero;
        myBinaryNumber truncatedBinary = new myBinaryNumber(newSize);
        for (int i = 0; i < newSize; i++) {
            truncatedBinary.setBit(i, binary.getBit(i + firstNonZero));
        }
        return truncatedBinary;
    }
}
//!Trying Bonus Task
class binaryMultiplicationFast extends binaryOperations {
    public myBinaryNumber binaryMultiplication(myBinaryNumber num1, myBinaryNumber num2) {
        long startTime = System.nanoTime();
        myBinaryNumber result = karatsubaMultiplication(num1, num2);
        long endTime = System.nanoTime();
        System.out.println("Karatsuba Multiplication Runtime: " + (endTime - startTime) + " nanoseconds");
        return result;
    }

    myBinaryNumber karatsubaMultiplication(myBinaryNumber num1, myBinaryNumber num2) {
        // Base case for recursion
        if (num1.getSize() == 0 || num2.getSize() == 0) {
            return new myBinaryNumber(1); // Return a binary number with a single 0 if one of the numbers is 0
        }

        if (num1.getSize() == 1 || num2.getSize() == 1) {
            int product = num1.getBit(num1.getSize() - 1) * num2.getBit(num2.getSize() - 1);
            return new myBinaryNumber(Integer.toBinaryString(product));
        }

        int m = Math.max(num1.getSize(), num2.getSize());
        int m2 = m / 2;

        // Splitting the binary numbers
        myBinaryNumber high1 = num1.getPart(0, num1.getSize() - m2);
        myBinaryNumber low1 = num1.getPart(num1.getSize() - m2, num1.getSize());
        myBinaryNumber high2 = num2.getPart(0, num2.getSize() - m2);
        myBinaryNumber low2 = num2.getPart(num2.getSize() - m2, num2.getSize());

        // Recursive calls
        myBinaryNumber z0 = karatsubaMultiplication(low1, low2);
        myBinaryNumber z1 = karatsubaMultiplication(binaryAddition(low1, high1), binaryAddition(low2, high2));
        myBinaryNumber z2 = karatsubaMultiplication(high1, high2);

        // Calculating the result
        myBinaryNumber step1 = shiftDigits.shiftLeft(z2, 2 * m2);
        myBinaryNumber step2 = shiftDigits.shiftLeft(binarySubtraction(z1, binaryAddition(z0, z2)), m2);
        myBinaryNumber result = binaryAddition(binaryAddition(step1, step2), z0);

        return result;
    }

    // Method for binary subtraction within binaryMultiplicationFast class
    public myBinaryNumber binarySubtraction(myBinaryNumber a, myBinaryNumber b) {
        int maxSize = Math.max(a.getSize(), b.getSize());
        myBinaryNumber result = new myBinaryNumber(maxSize);

        // Convert b to its 2's complement
        myBinaryNumber twoComplementB = new myBinaryNumber(maxSize);
        // Invert the bits of b
        for (int i = 0; i < b.getSize(); i++) {
            twoComplementB.setBit(i, b.getBit(i) == 0 ? 1 : 0);
        }
        // Add 1 to the inverted b to complete 2's complement
        int carry = 1;
        for (int i = maxSize - 1; i >= 0; i--) {
            int sum = twoComplementB.getBit(i) + carry;
            twoComplementB.setBit(i, sum % 2);
            carry = sum / 2;
        }

        // Add a and 2's complement of b
        carry = 0;
        for (int i = maxSize - 1; i >= 0; i--) {
            int sum = a.getBit(i) + twoComplementB.getBit(i) + carry;
            result.setBit(i, sum % 2);
            carry = sum / 2;
        }

        // Ignore the carry out to avoid sign bit, effectively performing subtraction

        return BinaryUtils.truncateBinaryNumber(result); // Assuming truncateBinaryNumber method exists to remove leading zeros
    }
}
