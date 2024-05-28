package controller;

import model.Player;
import model.Card;
import view.*;

import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileMenu {
    private static final Pattern showCoin = Pattern.compile
            ("\\s*show\\s+coins\\s*");
    private static final Pattern showExperience = Pattern.compile
            ("\\s*show\\s+experience\\s*");
    private static final Pattern showStorage = Pattern.compile
            ("\\s*show\\s+storage\\s*");
    private static final Pattern equipCard = Pattern.compile
            ("\\s*equip\\s+card\\s+(?<cardName>\\S+)\\s+to\\s+my\\s+deck\\s*");
    private static final Pattern unEquipCard = Pattern.compile
            ("\\s*unequip\\s+card\\s+(?<cardName>\\S+)\\s+from\\s+my\\s+deck\\s*");
    private static final Pattern showDeck = Pattern.compile
            ("\\s*show\\s+my\\s+deck\\s*");
    private static final Pattern showRank = Pattern.compile
            ("\\s*show\\s+my\\s+rank\\s*");
    private static final Pattern showRanking = Pattern.compile
            ("\\s*show\\s+ranking\\s*");
    private static final Pattern showMenu = Pattern.compile
            ("\\s*show\\s+current\\s+menu\\s*");
    private static final Pattern back = Pattern.compile
            ("\\s*back\\s*");

    public static void run(Scanner scanner, Menus menu) {
        String line = scanner.nextLine();
        Matcher matcher = back.matcher(line);
        while (!matcher.matches()) {
            matcher = showCoin.matcher(line);
            if (matcher.matches()) {
                ProfileMenu.showCoin();
                line = scanner.nextLine();
                matcher = back.matcher(line);
                continue;
            }
            matcher = showExperience.matcher(line);
            if (matcher.matches()) {
                ProfileMenu.showExperience();
                line = scanner.nextLine();
                matcher = back.matcher(line);
                continue;
            }
            matcher = showStorage.matcher(line);
            if (matcher.matches()) {
                ProfileMenu.showStorage();
                line = scanner.nextLine();
                matcher = back.matcher(line);
                continue;
            }
            matcher = equipCard.matcher(line);
            if (matcher.matches()) {
                String cardName = matcher.group("cardName");
                ProfileMenu.equipCard(cardName);
                line = scanner.nextLine();
                matcher = back.matcher(line);
                continue;
            }
            matcher = unEquipCard.matcher(line);
            if (matcher.matches()) {
                String cardName = matcher.group("cardName");
                ProfileMenu.unEquipCard(cardName);
                line = scanner.nextLine();
                matcher = back.matcher(line);
                continue;
            }
            matcher = showDeck.matcher(line);
            if (matcher.matches()) {
                ProfileMenu.showDeck();
                line = scanner.nextLine();
                matcher = back.matcher(line);
                continue;
            }
            matcher = showRank.matcher(line);
            if (matcher.matches()) {
                ProfileMenu.showRank();
                line = scanner.nextLine();
                matcher = back.matcher(line);
                continue;
            }
            matcher = showRanking.matcher(line);
            if (matcher.matches()) {
                ProfileMenu.showRanking();
                line = scanner.nextLine();
                matcher = back.matcher(line);
                continue;
            }
            matcher = showMenu.matcher(line);
            if (matcher.matches())
                ProfileMenu.showMenu(menu);
            else
                ProfileMenu.invalidCommand();
            line = scanner.nextLine();
            matcher = back.matcher(line);
        }
    }

    //show coin methods:
    private static void showCoin() {
        Printer.showCoin(Player.getLoggedInPlayer());
    }

    //show experience methods:
    private static void showExperience() {
        Printer.showExperience(Player.getLoggedInPlayer());
    }

    // show storage methods:
    private static void showStorage() {
        Printer.showStorage(Player.getLoggedInPlayer());
    }

    //equip card methods:
    private static void equipCard(String cardName) {
        Player player = Player.getLoggedInPlayer();
        if (Card.nameIsNotValid(cardName))
            Printer.profileMassage(ProfileMassage.CardExistence);
        else if (!player.findCardInNotDeckCards(cardName))
            Printer.profileMassage(ProfileMassage.CardNotDeckExistence);
        else if (player.deckCardsCount() == 12)
            Printer.profileMassage(ProfileMassage.DeckIsFull);
        else if (player.findCardInDeckCards(cardName) &&
                !Objects.equals(cardName, "pink") &&
                !Objects.equals(cardName, "yellow"))
            Printer.profileMassage(ProfileMassage.RepeatedPokemonInDeck);
        else {
            player.addCardToDeck(cardName);
            Printer.equipCard(cardName);
        }
    }

    //un equip card methods:
    private static void unEquipCard(String cardName) {
        Player player = Player.getLoggedInPlayer();
        if (Card.nameIsNotValid(cardName))
            Printer.profileMassage(ProfileMassage.CardExistence);
        else if (!player.findCardInDeckCards(cardName))
            Printer.profileMassage(ProfileMassage.CardInDeckExistence);
        else {
            player.removeCardFromDeck(cardName);
            Printer.unEquipCard(cardName);
        }
    }

    //show deck methods:
    private static void showDeck() {
        Printer.showDeck(Player.getLoggedInPlayer());
    }

    //show rank methods:
    private static void showRank() {
        Printer.showRank(Player.getLoggedInPlayer());
    }

    //show ranking methods:
    private static void showRanking() {
        Printer.showRanking();
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
