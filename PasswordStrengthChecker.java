import java.util.Scanner;

public class PasswordStrengthChecker {

    // Method to check password strength
    public static String checkPasswordStrength(String password) {
        int lengthCriteria = 8; // Minimum length for a strong password
        boolean hasUppercase = false;//Boolean variable to check if the password has an uppercase letter
        boolean hasLowercase = false;//Boolean variable to check if the password has a lowercase letter
        boolean hasDigit = false;//Boolean variable to check if the password has a digit
        boolean hasSpecialChar = false;//Boolean variable to check if the password has a special character
        String specialCharacters = "!@#$%^&*()-+";//String containing special characters

        // Analyze the password
        for (char ch : password.toCharArray()) {//Loop through each character in the password
            if (Character.isUpperCase(ch)) {//Check if the character is uppercase
                hasUppercase = true;
            } else if (Character.isLowerCase(ch)) {//Check if the character is lowercase
                hasLowercase = true;
            } else if (Character.isDigit(ch)) {//Check if the character is a digit
                hasDigit = true;
            } else if (specialCharacters.contains(String.valueOf(ch))) {
                hasSpecialChar = true;//Check if the character is a special character
            }
        }

        // Determine strength based on criteria
        if (password.length() >= lengthCriteria && hasUppercase && hasLowercase && hasDigit && hasSpecialChar) {//Check if the password meets all criteria
            return "Strong Password: Your password meets all the criteria.";
        } else if (password.length() >= lengthCriteria && (hasUppercase || hasLowercase) && (hasDigit || hasSpecialChar)) {//Check if the password meets some criteria
            return "Moderate Password: Consider adding more variety to your password.";
        } else {//Check if the password is weak
            return "Weak Password: Your password is too simple. Try adding more characters, uppercase letters, numbers, and special characters.";
        }
    }

    public static void main(String[] args) {//Main method
        Scanner scanner = new Scanner(System.in);

        // Prompt user to enter a password
        System.out.print("Enter a password to check its strength: ");
        String password = scanner.nextLine();//Read the password from the user

        // Check and display password strength
        String strength = checkPasswordStrength(password);
        System.out.println(strength);//Print the password strength

        scanner.close();//Close the scanner it prevents resource leak
    }
}
