import java.util.Scanner;

public class Password {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String password;

        while (true) {
            System.out.println("Enter a password with the following requirements:");
            System.out.println("It must be between 8 and 16 characters");
            System.out.println("It must contain lower and upper case characters");
            System.out.println("Contains a number");
            System.out.println("Contains a special character (i.e. ~!@#$)");
            System.out.print("Now enter your password: ");

            password = scanner.nextLine();

            if (passwordChecker(password) == true) {
                System.out.println("Your password is valid.");
                break;
            } else {
                System.out.println("Your password is invalid. Please try again.");
            }
        }
        scanner.close();
    }

    public static boolean passwordChecker(String password) {
        if (password.length() < 8 || password.length() > 16) {
            return false;
        }
        boolean hasLower = false;
        boolean hasUpper = false;
        boolean hasDigit = false;
        boolean hasSpec = false;
        
        String specialCharacters = "~!@#$%^&*()-=+_";
    
        for (int i = 0; i < password.length(); i++) {
            char character = password.charAt(i);
    
            if (Character.isLowerCase(character)) {
                hasLower = true;
            } else if (Character.isUpperCase(character)) { 
                hasUpper = true;
            } else if (Character.isDigit(character)) {
                hasDigit = true;
            } else if (specialCharacters.indexOf(character) >= 0) {
                hasSpec = true;
            }
        }

        int categories = 0;

        if (hasLower == true) {
            categories++;
        }
        if (hasUpper == true) {
            categories++;
        }
        if (hasDigit == true) {
            categories++;
        }
        if (hasSpec == true) {
            categories++;
        }
        if (categories < 3) {
            return false;
        }
        return true;
    }
}


