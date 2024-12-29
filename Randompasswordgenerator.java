import java.security.SecureRandom;
import java.util.Scanner;

public class Randompasswordgenerator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the desired password length: ");
        int length = scanner.nextInt();
        scanner.nextLine(); 

        System.out.print("Include lowercase letters? (yes/no): ");
        boolean includeLowercase = scanner.nextLine().equalsIgnoreCase("yes");

        System.out.print("Include uppercase letters? (yes/no): ");
        boolean includeUppercase = scanner.nextLine().equalsIgnoreCase("yes");

        System.out.print("Include digits? (yes/no): ");
        boolean includeDigits = scanner.nextLine().equalsIgnoreCase("yes");

        System.out.print("Include special characters? (yes/no): ");
        boolean includeSpecial = scanner.nextLine().equalsIgnoreCase("yes");

        String password = generatePassword(length, includeLowercase, includeUppercase, includeDigits, includeSpecial);
        System.out.println("Generated password: " + password);

        scanner.close();
    }

    public static String generatePassword(int length, boolean includeLowercase, boolean includeUppercase, boolean includeDigits, boolean includeSpecial) {
        StringBuilder characterSet = new StringBuilder();
        if (includeLowercase) {
            characterSet.append("abcdefghijklmnopqrstuvwxyz");
        }
        if (includeUppercase) {
            characterSet.append("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        }
        if (includeDigits) {
            characterSet.append("0123456789");
        }
        if (includeSpecial) {
            characterSet.append("!@#$%^&*()-_=+[]{}|;:'\",.<>?/`~");
        }

        if (characterSet.length() == 0) {
            throw new IllegalArgumentException("At least one character set must be selected");
        }

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characterSet.length());
            password.append(characterSet.charAt(index));
        }

        return password.toString();
    }
}