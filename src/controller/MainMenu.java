package controller;

import model.Player;
import view.MainMassage;
import view.Menus;
import view.Printer;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainMenu {
    private static final Pattern shopMenu = Pattern.compile
            ("\\s*go\\s+to\\s+shop\\s+menu\\s*");
    private static final Pattern profileMenu = Pattern.compile
            ("\\s*go\\s+to\\s+profile\\s+menu\\s*");
    private static final Pattern gameMenu = Pattern.compile
            ("\\s*start\\s+new\\s+game\\s+with\\s+(?<username>\\S+)\\s*");
    private static final Pattern showMenu = Pattern.compile
            ("\\s*show\\s+current\\s+menu\\s*");
    private static final Pattern logout = Pattern.compile
            ("\\s*logout\\s*");

    public static void run(Scanner scanner, Menus menu) {
        String line = scanner.nextLine();
        Matcher matcher = logout.matcher(line);
        while (!matcher.matches()) {
            matcher = shopMenu.matcher(line);
            if (matcher.matches()) {
                MainMenu.shopMenu(scanner);
                line = scanner.nextLine();
                matcher = logout.matcher(line);
                continue;
            }
            matcher = profileMenu.matcher(line);
            if (matcher.matches()) {
                MainMenu.profileMenu(scanner);
                line = scanner.nextLine();
                matcher = logout.matcher(line);
                continue;
            }
            matcher = gameMenu.matcher(line);
            if (matcher.matches()) {
                String username = matcher.group("username");
                MainMenu.gameMenu(username, scanner);
                line = scanner.nextLine();
                matcher = logout.matcher(line);
                continue;
            }
            matcher = showMenu.matcher(line);
            if (matcher.matches())
                MainMenu.showMenu(menu);
            else
                MainMenu.invalidCommand();
            line = scanner.nextLine();
            matcher = logout.matcher(line);
        }
    }

    //shop menu methods:
    private static void shopMenu(Scanner scanner) {
        ShopMenu.run(scanner, Menus.ShopMenu);
    }

    //profile menu methods:
    private static void profileMenu(Scanner scanner) {
        ProfileMenu.run(scanner, Menus.ProfileMenu);
    }

    //game menu methods:
    private static void gameMenu(String username, Scanner scanner) {
        Player me = Player.getLoggedInPlayer();
        Player opponent = Player.findPlayer(username);
        if (opponent == null)
            Printer.mainMassage(MainMassage.OpponentUsernameExistence);
        else if (me.deckCardsCount() < 12)
            Printer.insufficientCard(me);
        else if (opponent.deckCardsCount() < 12)
            Printer.insufficientCard(opponent);
        else
            GameMenu.run(scanner, Menus.GameMenu, me, opponent);
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
