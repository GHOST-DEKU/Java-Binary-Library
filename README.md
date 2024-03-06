# Binary File
In this java file, there are classes that handle binary no.s
You can use this file to do operations(Multiplication & Addition) on 2 unsigned binary no.s

# Driver File
In this java file, there is a driver class which uses the classes of binary java file & access the 2 binary no.s from a text file and write the result in another text file

## IOException

In the provided Java code snippet from Driver.java, an IOException can be thrown due to several reasons related to file operations being performed within the try block. Here are the primary reasons:

1. File Access: Attempting to open a file that does not exist with new File(pathOfInputFile) and subsequently trying to create a Scanner with new Scanner(inputFile) can throw an IOException if the file at pathOfInputFile cannot be found or accessed for reading.
2. File Reading: Using a Scanner to read from a file (new Scanner(inputFile)) can throw an IOException if the file is not accessible, cannot be opened for reading, or if an error occurs while reading the file.
3. File Writing: Creating a FileWriter with new FileWriter(pathOfOutputFile) for writing to a file can throw an IOException if the file at pathOfOutputFile cannot be created or opened for writing. Additionally, writing to the file with fileWriter.write(...) and closing the file with fileWriter.close() can also result in an IOException if an error occurs during these operations.
4. Closing Resources: Closing the Scanner and FileWriter resources with sc.close() and fileWriter.close() can throw an IOException if an error occurs while closing the underlying file.

In summary, any issues related to accessing, reading from, writing to, or closing files can lead to an IOException being thrown in this context.
