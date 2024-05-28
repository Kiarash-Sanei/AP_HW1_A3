package view;

import model.*;
import model.Card;
import model.cards.Pokemon;

import java.util.ArrayList;
import java.util.Objects;

public interface Printer {
    static void loginMassage(LoginMassage massage) {
        switch (massage) {
            case InvalidUsername:
                System.out.println("username format is invalid");
                break;
            case RepeatedUsername:
                System.out.println("username already exists");
                break;
            case InvalidPasswordLength:
                System.out.println("password length too small or short");
                break;
            case InsufficientSpecialCharacters:
                System.out.println("your password must have at least one special character");
                break;
            case InvalidStartingCharacter:
                System.out.println("your password must start with english letters");
                break;
            case InvalidEmailFormat:
                System.out.println("email format is invalid");
                break;
            case InvalidEmailCharacter:
                System.out.println("you can't use special characters");
                break;
            case Registered:
                System.out.println("user registered successfully");
                break;
            case UsernameExistence:
                System.out.println("username doesn't exist");
                break;
            case IncorrectPassword:
                System.out.println("password is incorrect");
                break;
            case LoggedIn:
                System.out.println("user logged in successfully");
                break;
        }
    }

    static void showMenu(Menus menu) {
        System.out.println(menu);
    }

    static void invalidCommand() {
        System.out.println("invalid command");
    }

    static void mainMassage(MainMassage massage) {
        if (Objects.requireNonNull(massage) == MainMassage.OpponentUsernameExistence) {
            System.out.println("username is incorrect");
        }
    }

    static void insufficientCard(Player player) {
        System.out.println(player.getUsername() + " has no 12 cards in deck");
    }

    static void showCoin(Player player) {
        System.out.println("number of coins:" + player.getCoin());
    }

    static void showExperience(Player player) {
        System.out.println("experience:" + player.getExperience());
    }

    static void showStorage(Player player) {
        int counter = 1;
        for (Card card : player.getAllCards()) {
            System.out.println(counter + "." +
                    card.generalType() + " " +
                    card + " " +
                    Card.priceForBuy(card.toString()));
            counter++;
        }
    }

    static void profileMassage(ProfileMassage massage) {
        switch (massage) {
            case CardExistence:
                System.out.println("card name is invalid");
                break;
            case CardNotDeckExistence:
                System.out.println("you don't have this type of card");
                break;
            case DeckIsFull:
                System.out.println("your deck is already full");
                break;
            case RepeatedPokemonInDeck:
                System.out.println("you have already added this type of pokemon to your deck");
                break;
            case CardInDeckExistence:
                System.out.println("you don't have this type of card in your deck");
                break;
        }
    }

    static void equipCard(String cardName) {
        System.out.println("card " + cardName + " equipped to your deck successfully");
    }

    static void unEquipCard(String cardName) {
        System.out.println("card " + cardName + " unequipped from your deck successfully");
    }

    static void showDeck(Player player) {
        int counter = 1;
        for (Card card : player.getDeckCards()) {
            System.out.println(counter + "." + card);
            counter++;
        }
    }

    static void showRank(Player player) {
        System.out.println("your rank:" + player.rankCalculator());
    }

    static void showRanking() {
        int counter = 1;
        for (Player player : Player.getPlayers()) {
            System.out.println(counter + ".username:" +
                    player.getUsername() + " " + "experience:" +
                    player.getExperience());
            counter++;
        }
    }

    static void shopMassage(ShopMassage massage) {
        switch (massage) {
            case CardExistence:
                System.out.println("card name is invalid");
                break;
            case CardForPlayerExistence:
                System.out.println("you don't have this type of card for sell");
                break;
        }
    }

    static void insufficientCoin(String cardName) {
        System.out.println("not enough coin to buy " + cardName);
    }

    static void buyCard(String cardName) {
        System.out.println("card " + cardName + " bought successfully");
    }

    static void sellCard(String cardName) {
        System.out.println("card " + cardName + " sold successfully");
    }

    static void showTable(Player me, Player opponent, int roundNumber) {
        System.out.println("round " + roundNumber);
        System.out.println("your active card:");
        Printer.showBench(me.getTable().getBench(0));
        System.out.println("\n" + "your hand:");
        ArrayList<Card> deck = me.getDeckCards();
        for (int i = 0; i < deck.size(); i++)
            System.out.println((i + 1) + "." + deck.get(i));
        System.out.println("\n" + "your bench:");
        for (int i = 1; i < 4; i++) {
            System.out.print(i + ".");
            Printer.showBench(me.getTable().getBench(i));
        }
        System.out.println("\n" + opponent.getUsername() + "'s active card:");
        Printer.showBench(opponent.getTable().getBench(0));
        System.out.println("\n" + opponent.getUsername() + "'s bench:");
        for (int i = 1; i < 4; i++) {
            System.out.print(i + ".");
            Printer.showBench(opponent.getTable().getBench(i));
        }
    }

    static void showBench(ArrayList<Card> bench) {
        if (bench != null) {
            StringBuilder out = new StringBuilder();
            for (int i = 0; i < 2; i++) {
                if (bench.get(i) != null) {
                    out.append(bench.get(i).toString());
                    out.append("|");
                } else
                    out.append("|");
            }
            if (bench.get(2) != null) {
                out.append(bench.get(2).toString());
                out.append("\n");
            } else
                out.append("\n");
            if (!Objects.equals(out.toString(), "||\n"))
                System.out.print(out);
            else
                System.out.println();
        } else {
            System.out.println();
        }
    }

    static void gameMassage(GameMassage massage) {
        switch (massage) {
            case InvalidPlace:
                System.out.println("invalid place number");
                break;
            case PokemonExistence:
                System.out.println("no pokemon in the selected place");
                break;
            case CardExistence:
                System.out.println("card name is invalid");
                break;
            case PutCardExistence:
                System.out.println("you don't have the selected card");
                break;
            case PokemonFull:
                System.out.println("a pokemon already exists there");
                break;
            case EnergyFull:
                System.out.println("pokemon already has 2 energies");
                break;
            case EnergyCanNotBePlayed:
                System.out.println("you have already played an energy card in this turn");
                break;
            case CardPut:
                System.out.println("card put successful");
                break;
            case InvalidBenchNumber:
                System.out.println("invalid bench number");
                break;
            case PokemonIsSleeping:
                System.out.println("active pokemon is sleeping");
                break;
            case BenchSwapped:
                System.out.println("substitution successful");
                break;
            case PokemonActive:
                System.out.println("no active pokemon");
                break;
            case InvalidAction:
                System.out.println("invalid action");
                break;
            case InvalidTarget:
                System.out.println("invalid target number");
                break;
            case Action:
                System.out.println("action executed successfully");
                break;
        }
    }

    static void showInfo(ArrayList<Card> bench) {
        System.out.println("pokemon: " + bench.get(0));
        System.out.print("special condition:");
        if (((Pokemon) bench.get(0)).getState() == null ||
                ((Pokemon) bench.get(0)).getState() == State.none)
            System.out.println();
        else
            System.out.println(" " + ((Pokemon) bench.get(0)).getState());
        System.out.printf("hitpoint: %.2f/%.2f\n", ((Pokemon) bench.get(0)).getHitpoint(),
                (float) ((Pokemon) bench.get(0)).maxHitpoint());
        if (bench.get(1) == null)
            System.out.println("energy 1:");
        else
            System.out.println("energy 1: " + bench.get(1));
        if (bench.get(2) == null)
            System.out.println("energy 2:");
        else
            System.out.println("energy 2: " + bench.get(2));
    }

    static void showTurn(Player player) {
        System.out.println(player.getUsername() + "'s turn");
    }

    static void showWinner(Player player) {
        System.out.println("Winner: " + player.getUsername());
    }
}
