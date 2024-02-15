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
        if ((b == 0 || b == 1) && p <= getSize()) {
            if (p == getSize()) {
                binaryNumber = new int[getSize()+1];
            }
            binaryNumber[p] = b;
        }
        else if (p > getSize()) {
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
    
}

// Class to operate on binary numbers
abstract class binaryOperations {
    abstract public myBinaryNumber binaryMultiplication(myBinaryNumber a, myBinaryNumber b);
    public myBinaryNumber binaryAddition(myBinaryNumber a, myBinaryNumber b) {
        myBinaryNumber result;
        int i;
        if (a.getSize() > b.getSize()) {
            result = new myBinaryNumber(a.getSize());
            i = a.getSize() - 1;
        }
        else if (a.getSize() == b.getSize()) {
            result = new myBinaryNumber(a.getSize() + 1);
            i = a.getSize() - 1;
        }
        else {
            result = new myBinaryNumber(b.getSize());
            i = b.getSize() - 1;
        }
        int carry = 0;
        int sum = 0;
        while (i >= 0) {
            int A = a.getBit(i), B = b.getBit(i);
            sum = A + B + carry;
            switch (sum) {
                case 0:
                    result.setBit(i, 0);
                    carry = 0;
                    break;
                case 1:
                    result.setBit(i, 1);
                    carry = 0;
                    break;
                case 2:
                    result.setBit(i, 0);
                    carry = 1;
                    break;
                case 3:
                    result.setBit(i, 1);
                    carry = 1;
                    break;
                default:
                    throw new AssertionError();
            }
            i--;
        }
        if (carry == 1) {
            for (i = 0; i < result.getSize(); i++) {
                result.setBit(i+1, result.getBit(i));
            }
            result.setBit(0, carry);
        }
        return result;
    }
}