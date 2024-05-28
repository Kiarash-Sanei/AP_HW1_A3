package controller;

import model.Player;
import model.Card;
import view.*;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShopMenu {
    private static final Pattern buyCard = Pattern.compile
            ("\\s*buy\\s+card\\s+(?<cardName>\\S+)\\s*");
    private static final Pattern sellCard = Pattern.compile
            ("\\s*sell\\s+card\\s+(?<cardName>\\S+)\\s*");
    private static final Pattern showMenuShop = Pattern.compile
            ("\\s*show\\s+current\\s+menu\\s*");
    private static final Pattern back = Pattern.compile
            ("\\s*back\\s*");

    public static void run(Scanner scanner, Menus menu) {
        String line = scanner.nextLine();
        Matcher matcher = back.matcher(line);
        while (!matcher.matches()) {
            matcher = buyCard.matcher(line);
            if (matcher.matches()) {
                String cardName = matcher.group("cardName");
                ShopMenu.buyCard(cardName);
                line = scanner.nextLine();
                matcher = back.matcher(line);
                continue;
            }
            matcher = sellCard.matcher(line);
            if (matcher.matches()) {
                String cardName = matcher.group("cardName");
                ShopMenu.sellCard(cardName);
                line = scanner.nextLine();
                matcher = back.matcher(line);
                continue;
            }
            matcher = showMenuShop.matcher(line);
            if (matcher.matches())
                ShopMenu.showMenu(menu);
            else
                ShopMenu.invalidCommand();
            line = scanner.nextLine();
            matcher = back.matcher(line);
        }
    }

    //buy card methods:
    private static void buyCard(String cardName) {
        Player player = Player.getLoggedInPlayer();
        if (Card.nameIsNotValid(cardName))
            Printer.shopMassage(ShopMassage.CardExistence);
        else if (Card.priceForBuy(cardName) > player.getCoin())
            Printer.insufficientCoin(cardName);
        else {
            player.buyCard(cardName);
            Printer.buyCard(cardName);
        }
    }

    //sell card methods:
    private static void sellCard(String cardName) {
        Player player = Player.getLoggedInPlayer();
        if (Card.nameIsNotValid(cardName))
            Printer.shopMassage(ShopMassage.CardExistence);
        else if (!player.findCardInAllCards(cardName))
            Printer.shopMassage(ShopMassage.CardForPlayerExistence);
        else {
            player.sellCard(cardName);
            Printer.sellCard(cardName);
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
