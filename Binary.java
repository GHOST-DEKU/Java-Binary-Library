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
    
}

// Class to operate on binary numbers
abstract class binaryOperations {
    abstract public myBinaryNumber binaryMultiplication(myBinaryNumber a, myBinaryNumber b);
    // Method to perform binary addition operation
    public myBinaryNumber binaryAddition(myBinaryNumber a, myBinaryNumber b) {
        // Determine the size for the result binary number. It should be max(a.size, b.size) + 1 to accommodate carry.
        int maxSize = Math.max(a.getSize(), b.getSize());
        myBinaryNumber result = new myBinaryNumber(maxSize + 1);
        int carry = 0;
        int sum = 0;
        int i = maxSize - 1;
        while (i >= 0) {
            int A;
            int B;
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
            for (i = result.getSize() - 1; i > 0; i--) {
                result.setBit(i, result.getBit(i-1));
            }
            result.setBit(0, carry);
        }
        return result;
    }
}

class binaryMultiplicationNaive extends binaryOperations {
    public myBinaryNumber binaryMultiplication(myBinaryNumber a, myBinaryNumber b) {
        myBinaryNumber result = new myBinaryNumber(a.getSize() + b.getSize());
        int i = a.getSize() - 1;
        int j = b.getSize() - 1;
        myBinaryNumber temp;
        do { 
            if (a.getSize() > b.getSize()) {
                temp = new myBinaryNumber(a.getSize());
            }
            else if (a.getSize() == b.getSize()) {
                temp = new myBinaryNumber(a.getSize() + 1);
            }
            else {
                temp = new myBinaryNumber(b.getSize());
            }

            while (i >= 0) { 
                if (a.getBit(i) == 1 && b.getBit(i) == 1) {
                    temp.setBit(i, 1);
                }
                else {
                    temp.setBit(i, 0);
                }
                i--;
            }

            result = binaryAddition(result, temp);

            j--;
        } while (j >= 0);
        return result;
    }
}

class Main {
    public static void main(String[] args) {
        myBinaryNumber a = new myBinaryNumber("1010");
        a.printDecimalNumber();
        myBinaryNumber b = new myBinaryNumber("1101");
        b.printDecimalNumber();
        binaryOperations binaryMultiplicationObj = new binaryMultiplicationNaive();
        myBinaryNumber result = binaryMultiplicationObj.binaryMultiplication(a, b);
        result.printNumber();
        result.printDecimalNumber();
    }
}
