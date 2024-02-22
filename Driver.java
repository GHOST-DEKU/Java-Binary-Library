package classes;
import java.io.*;
import java.util.Scanner;
public class Driver {
    public static void main(String[] args) {
        //Using try for Exception Handling
        try{
            Scanner input = new Scanner(System.in);
            System.out.print("Enter the relative path to the input file: ");
            String pathOfInputFile  = input.nextLine();
            File inputFile = new File(pathOfInputFile);
            Scanner sc = new Scanner(inputFile);
            int lines = 0;
            while (sc.hasNextLine()) {
                lines++;
                sc.nextLine();
            }
            // Reinitializing Scanner to read the file again from the beginning
            sc = new Scanner(inputFile); 
            if (lines == 2) {
                String num1 = sc.nextLine();
                String num2 = sc.nextLine();
                // Creating myBinaryNumber instances for the read binary numbers
                myBinaryNumber binary1 = new myBinaryNumber(num1);
                myBinaryNumber binary2 = new myBinaryNumber(num2);
                // Prompting user for the output file's path
                System.out.print("Enter the relative path to the output file(Make Sure The Directory(s) Exist): ");
                String pathOfOutputFile  = input.nextLine();
                // Creating the output file
                File outputFile = new File(pathOfOutputFile);
                outputFile.createNewFile();
                // Creating a object of FileWriter class to write on the file
                FileWriter fileWriter = new FileWriter(pathOfOutputFile);
                binaryMultiplicationNaive bmn = new binaryMultiplicationNaive();
                myBinaryNumber additionResult = bmn.binaryAddition(binary1, binary2);
                myBinaryNumber multiResult = bmn.binaryMultiplication(binary1, binary2);
                // Writing the results to the output file, prepending '0' to maintain binary format
                fileWriter.write("0"+BinaryUtils.binaryNumberToString(additionResult)+"\n");
                fileWriter.write("0"+BinaryUtils.binaryNumberToString(multiResult)+"\n");
                fileWriter.close();
            }
            else {
                System.out.println("Needed 2 lines in the file");
            }
            // Closing the Scanner to prevent resource leak
            input.close();
            sc.close();
        } catch (IOException e) {
            // Handling potential IOExceptions during file operations
            System.out.println("An IOException occurred while handling the file operations.");
            e.printStackTrace();
        }
    }

}