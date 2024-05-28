package controller;

import model.Player;
import view.*;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginMenu {
    private static final ArrayList<String> specialCharacters = new ArrayList<>();

    static {
        specialCharacters.add("@");
        specialCharacters.add("#");
        specialCharacters.add("$");
        specialCharacters.add("^");
        specialCharacters.add("&");
        specialCharacters.add("!");
    }

    private static final Pattern register = Pattern.compile
            ("\\s*register\\s+username\\s+(?<username>\\S+)\\s+password\\s+(?<password>\\S+)\\s+email\\s+(?<email>\\S+)\\s*");
    private static final Pattern username = Pattern.compile
            ("[a-zA-Z_]+");
    private static final Pattern emailAddress = Pattern.compile
            ("[a-zA-Z0-9.]+");
    private static final Pattern emailEnding = Pattern.compile
            ("@[a-z]+\\.com");
    private static final Pattern mainMenu = Pattern.compile
            ("\\s*login\\s+username\\s+(?<username>\\S+)\\s+password\\s+(?<password>\\S+)\\s*");
    private static final Pattern showMenu = Pattern.compile
            ("\\s*show\\s+current\\s+menu\\s*");
    private static final Pattern exit = Pattern.compile
            ("\\s*exit\\s*");

    public static void run(Scanner scanner, Menus menu) {
        String line = scanner.nextLine();
        Matcher matcher = exit.matcher(line);
        while (!matcher.matches()) {
            matcher = register.matcher(line);
            if (matcher.matches()) {
                String username = matcher.group("username");
                String password = matcher.group("password");
                String email = matcher.group("email");
                LoginMenu.register(username, password, email);
                line = scanner.nextLine();
                matcher = exit.matcher(line);
                continue;
            }
            matcher = mainMenu.matcher(line);
            if (matcher.matches()) {
                String username = matcher.group("username");
                String password = matcher.group("password");
                LoginMenu.mainMenu(username, password, scanner);
                line = scanner.nextLine();
                matcher = exit.matcher(line);
                continue;
            }
            matcher = showMenu.matcher(line);
            if (matcher.matches())
                LoginMenu.showMenu(menu);
            else
                LoginMenu.invalidCommand();
            line = scanner.nextLine();
            matcher = exit.matcher(line);
        }
    }

    //register methods:
    private static boolean usernameChecker(String username) {
        Player player = Player.findPlayer(username);
        if (!LoginMenu.username.matcher(username).matches()) {
            Printer.loginMassage(LoginMassage.InvalidUsername);
            return false;
        }
        if (player != null) {
            Printer.loginMassage(LoginMassage.RepeatedUsername);
            return false;
        }
        return true;
    }

    public static boolean specialCharacterCheck(String password) {
        for (String s : LoginMenu.specialCharacters)
            if (password.contains(s))
                return true;
        return false;
    }

    private static boolean passwordChecker(String password) {
        int length = password.length();
        char firstCharacter = password.charAt(0);
        if (length > 18 || length < 6) {
            Printer.loginMassage(LoginMassage.InvalidPasswordLength);
            return false;
        }
        if (!LoginMenu.specialCharacterCheck(password)) {
            Printer.loginMassage(LoginMassage.InsufficientSpecialCharacters);
            return false;
        }
        if ((firstCharacter < 'a' || firstCharacter > 'z') && (firstCharacter < 'A' || firstCharacter > 'Z')) {
            Printer.loginMassage(LoginMassage.InvalidStartingCharacter);
            return false;
        }
        return true;
    }

    private static boolean emailChecker(String email) {
        String emailAddress;
        String emailEnding;
        if (email.indexOf('@') == 0) {
            emailAddress = "";
            emailEnding = email;
        } else {
            emailAddress = email.substring(0, email.indexOf('@') - 1);
            emailEnding = email.substring(email.indexOf('@'));
        }
        int counter = 0;
        if (!LoginMenu.emailEnding.matcher(emailEnding).matches()) {
            Printer.loginMassage(LoginMassage.InvalidEmailFormat);
            return false;
        }
        if (!LoginMenu.emailAddress.matcher(emailAddress).matches()) {
            Printer.loginMassage(LoginMassage.InvalidEmailCharacter);
            return false;
        }
        for (int i = 0; i < emailAddress.length(); i++)
            if (emailAddress.charAt(i) == '.')
                counter++;
        if (counter > 1) {
            Printer.loginMassage(LoginMassage.InvalidEmailCharacter);
            return false;
        }
        return true;
    }

    private static void register(String username, String password, String email) {
        if (usernameChecker(username) &&
                passwordChecker(password) &&
                emailChecker(email)) {
            new Player(username, password);
            Printer.loginMassage(LoginMassage.Registered);
        }
    }

    //login methods:
    private static void mainMenu(String username, String password, Scanner scanner) {
        Player player = Player.findPlayer(username);
        if (player == null)
            Printer.loginMassage(LoginMassage.UsernameExistence);
        else if (!player.passwordComparator(password))
            Printer.loginMassage(LoginMassage.IncorrectPassword);
        else {
            Player.setLoggedInPlayer(player);
            Printer.loginMassage(LoginMassage.LoggedIn);
            MainMenu.run(scanner, Menus.MainMenu);
        }
    }

    //show menu methods:
    private static void showMenu(Menus menu) {
        Printer.showMenu(menu);
    }

    //invalid command methods:
    private static void invalidCommand() {
        Printer.invalidCommand();
    }
}
