import java.io.*;
import java.util.Scanner;

public class FileEncryptDecrypt {

    // Method to perform encryption or decryption
    private static String processText(String text, int shift, boolean isDecrypt) {
        StringBuilder result = new StringBuilder();//StringBuilder is a mutable sequence of characters. This class provides an API compatible with StringBuffer, but with no guarantee of synchronization. This class is designed
        
        for (char c : text.toCharArray()) {//The toCharArray() method of String class converts this string into an array of characters. It returns a newly created character array, its length is similar to this string and its contents are initialized with the characters of this string.
            if (Character.isLetter(c)) {//The java.lang.Character.isLetter(char ch) determines if the specified character is a letter.
                char base = Character.isLowerCase(c) ? 'a' : 'A';//The java.lang.Character.isLowerCase(char ch) determines if the specified character is a lowercase letter.
                int offset = isDecrypt ? -shift : shift;//The java.lang.Character.isUpperCase(char ch) determines if the specified character is an uppercase letter.
                c = (char) ((c - base + offset + 26) % 26 + base);//The java.lang.Character.isUpperCase(char ch) determines if the specified character is an uppercase letter.
            }
            result.append(c);//The java.lang.Character.isUpperCase(char ch) determines if the specified character is an uppercase letter.
        }
        return result.toString();//The java.lang.Character.isUpperCase(char ch) determines if the specified character is an uppercase letter.
    }

    // Method to read file content
    private static String readFile(String filePath) throws IOException {//The java.io.IOException is a checked exception that is thrown when an input/output operation fails.
        StringBuilder content = new StringBuilder();//StringBuilder is a mutable sequence of characters. This class provides an API compatible with StringBuffer, but with no guarantee of synchronization. This class is designed
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");//The java.lang.StringBuilder.append() method is used to add the specified string to the current string.
            }
        }
        return content.toString();
    }

    // Method to write content to a file
    private static void writeFile(String filePath, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);//The java.io.BufferedWriter.write(String s) method writes a string to the output stream.
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);//The java.util.Scanner class is a simple initial API to read input from the console. It has methods like nextInt(), nextFloat(), nextDouble(), next(), etc.

        try {
            System.out.println("Choose an option: \n1. Encrypt a file \n2. Decrypt a file");
            int choice = scanner.nextInt();//The java.util.Scanner.nextInt() method scans the next token of the input as an int.
            scanner.nextLine(); // Consume newline

            if (choice != 1 && choice != 2) {
                System.out.println("Invalid choice! Exiting...");
                return;
            }

            System.out.print("Enter the file path (or type 'create' to create a new file): ");
            String filePath = scanner.nextLine();

            if (filePath.equalsIgnoreCase("create")) {//The java.lang.String.equalsIgnoreCase() method compares this String to another String, ignoring case considerations.
                System.out.print("Enter the name of the new file: ");
                filePath = scanner.nextLine();
                System.out.println("Enter the content of the new file (type 'END' on a new line to finish):");
                StringBuilder newContent = new StringBuilder();
                while (true) {
                    String line = scanner.nextLine();
                    if (line.equalsIgnoreCase("END")) {
                        break;
                    }
                    newContent.append(line).append("\n");
                }
                writeFile(filePath, newContent.toString());
                System.out.println("File created successfully.");
            }

            System.out.print("Enter the shift value (e.g., 3): ");
            int shift = scanner.nextInt();

            // Read the file content
            String fileContent = readFile(filePath);

            // Process the text
            boolean isDecrypt = (choice == 2);
            String result = processText(fileContent, shift, isDecrypt);

            // Save the result to a new file
            String outputFilePath = filePath + (isDecrypt ? ".decrypted" : ".encrypted");
            writeFile(outputFilePath, result);

            System.out.println("Operation successful! File saved as: " + outputFilePath);

        } catch (FileNotFoundException e) {
            System.err.println("Error: File not found.");
        } catch (IOException e) {
            System.err.println("Error: Unable to process the file.");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
