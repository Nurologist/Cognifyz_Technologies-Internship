import java.util.Scanner;

public class palindromechecker {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a word or phrase: ");
        String input = scanner.nextLine();
        System.out.println("Is the input a palindrome? " + isPalindrome(input));
        scanner.close();
    }

    public static boolean isPalindrome(String s) {
        // Remove punctuation and spaces, and convert to lowercase
        StringBuilder cleaned = new StringBuilder();
        
        for (char c : s.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                cleaned.append(Character.toLowerCase(c));
            }
        }
        
        // Check if the cleaned string is the same forwards and backwards
        String cleanedString = cleaned.toString();
        int length = cleanedString.length();
        for (int i = 0; i < length / 2; i++) {
            if (cleanedString.charAt(i) != cleanedString.charAt(length - 1 - i)) {
                return false;
            }
        }
        return true;
    }
}