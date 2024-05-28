package controller;

import model.*;
import model.Card;
import model.cards.Plant;
import model.cards.Pokemon;
import view.GameMassage;
import view.Menus;
import view.Printer;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameMenu {
    private static final Pattern showTable = Pattern.compile
            ("\\s*show\\s+table\\s*");
    private static final Pattern showInfo = Pattern.compile
            ("\\s*show\\s+(?<who>my|enemy)\\s+info\\s+(?<placeNumber>\\S+)\\s*");
    private static final Pattern putCard = Pattern.compile
            ("\\s*put\\s+card\\s+(?<cardName>\\S+)\\s+to\\s+(?<placeNumber>\\S+)\\s*");
    private static final Pattern substitute = Pattern.compile
            ("\\s*substitute\\s+active\\s+card\\s+with\\s+bench\\s+(?<benchNumber>\\S+)\\s*");
    private static final Pattern endTurn = Pattern.compile
            ("\\s*end\\s+turn\\s*");
    private static final Pattern execute = Pattern.compile
            ("\\s*execute\\s+action\\s*");
    private static final Pattern executeWithTarget = Pattern.compile
            ("\\s*execute\\s+action\\s+-t\\s+(?<target>\\S+)\\s*");
    private static final Pattern showMenu = Pattern.compile
            ("\\s*show\\s+current\\s+menu\\s*");

    public static void run(Scanner scanner, Menus menu, Player me, Player opponent) {
        int turnNumber = 0;
        ArrayList<Player> players = new ArrayList<>();
        players.add(me);
        players.add(opponent);
        me.start();
        opponent.start();
        while (true) {
            String line = scanner.nextLine();
            Matcher matcher = showTable.matcher(line);
            if (matcher.matches()) {
                GameMenu.showTable(players.get(0), players.get(1), turnNumber / 2 + 1);
                continue;
            }
            matcher = showInfo.matcher(line);
            if (matcher.matches()) {
                int placeNumber = Integer.parseInt(matcher.group("placeNumber"));
                if (Objects.equals(matcher.group("who"), "my"))
                    GameMenu.showInfo(players.get(0), placeNumber);
                else
                    GameMenu.showInfo(players.get(1), placeNumber);
                continue;
            }
            matcher = putCard.matcher(line);
            if (matcher.matches()) {
                int placeNumber = Integer.parseInt(matcher.group("placeNumber"));
                String cardName = matcher.group("cardName");
                GameMenu.putCard(cardName, placeNumber, players.get(0));
                continue;
            }
            matcher = substitute.matcher(line);
            if (matcher.matches()) {
                int index = Integer.parseInt(matcher.group("benchNumber"));
                GameMenu.substitute(index, players.get(0));
                continue;
            }
            matcher = endTurn.matcher(line);
            if (matcher.matches()) {
                GameMenu.stateChecker(players.get(0));
                GameMenu.stateChecker(players.get(1));
                if (players.get(0).getTable().getCard(0, 0) == null) {
                    Printer.showWinner(Objects.requireNonNull(players.get(1)));
                    GameMenu.scoreGiver(players.get(0), players.get(1));
                    GameMenu.scoreGiver(players.get(1), players.get(0));
                    players.get(0).reset();
                    players.get(1).reset();
                    break;
                }
                players.get(0).turnReset();
                players.get(1).turnReset();
                Collections.swap(players, 0, 1);
                turnNumber++;
                Printer.showTurn(players.get(0));
                continue;
            }
            matcher = executeWithTarget.matcher(line);
            if (matcher.matches()) {
                int target = Integer.parseInt(matcher.group("target"));
                if (GameMenu.execute(players.get(0), target, players.get(1))) {
                    GameMenu.ending(players.get(0), target, players.get(1));
                    GameMenu.stateChecker(players.get(0));
                    GameMenu.stateChecker(players.get(1));
                    if (players.get(0).getTable().getCard(0, 0) == null) {
                        Printer.showWinner(Objects.requireNonNull(players.get(1)));
                        GameMenu.scoreGiver(players.get(0), players.get(1));
                        GameMenu.scoreGiver(players.get(1), players.get(0));
                        players.get(0).reset();
                        players.get(1).reset();
                        break;
                    }
                    players.get(0).turnReset();
                    players.get(1).turnReset();
                    Collections.swap(players, 0, 1);
                    turnNumber++;
                    Printer.showTurn(players.get(0));
                }
                continue;
            }
            matcher = execute.matcher(line);
            if (matcher.matches()) {
                if (GameMenu.execute(players.get(0), players.get(1))) {
                    GameMenu.ending(players.get(0), players.get(1));
                    GameMenu.stateChecker(players.get(0));
                    GameMenu.stateChecker(players.get(1));
                    if (players.get(0).getTable().getCard(0, 0) == null) {
                        Printer.showWinner(Objects.requireNonNull(players.get(1)));
                        GameMenu.scoreGiver(players.get(0), players.get(1));
                        GameMenu.scoreGiver(players.get(1), players.get(0));
                        players.get(0).reset();
                        players.get(1).reset();
                        break;
                    }
                    players.get(0).turnReset();
                    players.get(1).turnReset();
                    Collections.swap(players, 0, 1);
                    turnNumber++;
                    Printer.showTurn(players.get(0));
                }
                continue;
            }
            matcher = showMenu.matcher(line);
            if (matcher.matches())
                GameMenu.showMenu(menu);
            else
                GameMenu.invalidCommand();
        }
    }

    //show table methods:
    private static void showTable(Player player1, Player player2, int roundNumber) {
        Printer.showTable(player1, player2, roundNumber);
    }

    //show info methods:
    private static void showInfo(Player player, int benchNumber) {
        Table table = player.getTable();
        if (benchNumber < 0 || benchNumber > 3)
            Printer.gameMassage(GameMassage.InvalidPlace);
        else if (table.getCard(benchNumber, 0) == null)
            Printer.gameMassage(GameMassage.PokemonExistence);
        else
            Printer.showInfo(table.getBench(benchNumber));
    }

    //put card methods:
    private static void putCard(String cardName, int benchNumber, Player player) {
        Card card = player.getCardInDeckCards(cardName);
        Table table = player.getTable();
        if (Card.nameIsNotValid(cardName))
            Printer.gameMassage(GameMassage.CardExistence);
        else if (!player.findCardInDeckCards(cardName))
            Printer.gameMassage(GameMassage.PutCardExistence);
        else if (benchNumber < 0 || benchNumber > 3)
            Printer.gameMassage(GameMassage.InvalidPlace);
        else {
            ArrayList<Card> bench = table.getBench(benchNumber);
            if (!Objects.equals(card.generalType(), "energy") &&
                    bench != null &&
                    bench.get(0) != null)
                Printer.gameMassage(GameMassage.PokemonFull);
            else if (Objects.equals(card.generalType(), "energy") &&
                    (bench == null || bench.get(0) == null))
                Printer.gameMassage(GameMassage.PokemonExistence);
            else if (Objects.equals(card.generalType(), "energy") &&
                    bench != null &&
                    bench.get(1) != null &&
                    bench.get(2) != null)
                Printer.gameMassage(GameMassage.EnergyFull);
            else if (Objects.equals(card.generalType(), "energy") &&
                    player.hasPlayedEnergy())
                Printer.gameMassage(GameMassage.EnergyCanNotBePlayed);
            else if (Objects.equals(card.generalType(), "energy")) {
                if (bench != null &&
                        bench.get(1) == null) {
                    table.setCard(benchNumber, 1, card);
                    player.putCard(card);
                } else {
                    table.setCard(benchNumber, 2, card);
                    player.putCard(card);
                }
                Printer.gameMassage(GameMassage.CardPut);
            } else {
                table.setCard(benchNumber, 0, card);
                player.putCard(card);
                Printer.gameMassage(GameMassage.CardPut);
            }
        }
    }

    //substitute methods:
    private static void substitute(int benchNumber, Player player) {
        Table table = player.getTable();
        ArrayList<Card> active = table.getBench(0);
        if (benchNumber < 1 || benchNumber > 3) {
            Printer.gameMassage(GameMassage.InvalidBenchNumber);
            return;
        }
        ArrayList<Card> bench = table.getBench(benchNumber);
        if (bench.get(0) == null)
            Printer.gameMassage(GameMassage.PokemonExistence);
        else if (active.get(0) != null && ((Pokemon) active.get(0)).getState() == State.sleep)
            Printer.gameMassage(GameMassage.PokemonIsSleeping);
        else {
            table.benchSwapper(0, benchNumber);
            Printer.gameMassage(GameMassage.BenchSwapped);
        }
    }

    // execute methods:
    private static boolean execute(Player player, int target, Player opponent) {
        Table table = player.getTable();
        ArrayList<Card> active = table.getBench(0);
        Card activePokemon = active.get(0);
        if (activePokemon == null) {
            Printer.gameMassage(GameMassage.PokemonActive);
            return false;
        } else if (!Objects.equals(activePokemon.toString(), "ducklett") &&
                !Objects.equals(activePokemon.toString(), "rowlet")) {
            Printer.gameMassage(GameMassage.InvalidAction);
            return false;
        } else if ((Objects.equals(activePokemon.toString(), "ducklett") && (target < 0 || target > 3)) ||
                (Objects.equals(activePokemon.toString(), "rowlet") && (target < 1 || target > 3))) {
            Printer.gameMassage(GameMassage.InvalidTarget);
            return false;
        } else if ((opponent.getTable().getCard(target, 0) == null && Objects.equals(activePokemon.toString(), "ducklett")) ||
                (player.getTable().getCard(target, 0) == null && Objects.equals(activePokemon.toString(), "rowlet"))) {
            Printer.gameMassage(GameMassage.PokemonExistence);
            return false;
        } else if (((Pokemon) activePokemon).getState() == State.sleep) {
            Printer.gameMassage(GameMassage.PokemonIsSleeping);
            return false;
        } else {
            Printer.gameMassage(GameMassage.Action);
            return true;
        }
    }

    private static boolean execute(Player player, Player opponent) {
        Table table = player.getTable();
        ArrayList<Card> active = table.getBench(0);
        Card activePokemon = active.get(0);
        if (activePokemon == null) {
            Printer.gameMassage(GameMassage.PokemonActive);
            return false;
        } else if (Objects.equals(activePokemon.toString(), "ducklett") ||
                Objects.equals(activePokemon.toString(), "rowlet")) {
            Printer.gameMassage(GameMassage.InvalidAction);
            return false;
        } else if (opponent.getTable().getCard(0, 0) == null) {
            Printer.gameMassage(GameMassage.PokemonExistence);
            return false;
        } else if (((Pokemon) activePokemon).getState() == State.sleep) {
            Printer.gameMassage(GameMassage.PokemonIsSleeping);
            return false;
        } else {
            Printer.gameMassage(GameMassage.Action);
            return true;
        }
    }

    private static void ending(Player player, Player opponent) {
        ArrayList<Card> active1 = player.getTable().getBench(0);
        if (active1.get(0) != null) {
            switch (active1.get(0).toString()) {
                case "dragonite":
                case "lugia":
                    GameMenu.attack(active1, 0, 1, opponent);
                    player.getTable().deleteEnergy();
                    return;
                case "tepig":
                    for (int i = 1; i <= 3; i++)
                        GameMenu.attack(active1, i, 0.2, opponent);
                    GameMenu.attack(active1, 0, 1, opponent);
                    player.getTable().deleteEnergy();
                    return;
                case "pineco":
                    double point = Pokemon.pointCalculator(active1, (Pokemon) active1.get(0));
                    player.getTable().deleteEnergy();
                    ((Pokemon) player.getTable().getCard(0, 0)).healer(point);
            }
        }
    }

    private static void ending(Player player, int target, Player opponent) {
        ArrayList<Card> active = player.getTable().getBench(0);
        if (active.get(0) != null) {
            if (Objects.equals(active.get(0).toString(), "ducklett")) {
                GameMenu.attack(active, target, 1, opponent);
                player.getTable().deleteEnergy();
            } else if (Objects.equals(active.get(0).toString(), "rowlet")) {
                Card targetCard = player.getTable().getBench(target).get(0);
                double point = Pokemon.pointCalculator(active, (Pokemon) targetCard);
                player.getTable().deleteEnergy();
                ((Pokemon) player.getTable().getCard(target, 0)).healer(point);
            }
        }
    }

    private static void attack(ArrayList<Card> active, int target, double multiply, Player opponent) {
        Table enemy = opponent.getTable();
        Pokemon card = (Pokemon) enemy.getCard(target, 0);
        Card activeCard = active.get(0);
        if (card != null) {
            double point = multiply * Pokemon.pointCalculator(active, card);
            if (Objects.equals(card.generalType(), "plant")) {
                Plant card1 = (Plant) card;
                point -= card1.getShield();
                if (point > 0) {
                    card1.setForceShield(0);
                    if (card.isCardAlive(point))
                        card.damage(point);
                    else
                        enemy.benchKiller(target);
                } else
                    card1.setForceShield(-point);
            } else {
                if (card.isCardAlive(point))
                    card.damage(point);
                else
                    enemy.benchKiller(target);
            }
            if (Objects.equals(activeCard.generalType(), "fire") &&
                    !Objects.equals(card.generalType(), "water")) {
                card.setState(State.burning);
                card.startStateTime();
            } else if (Objects.equals(activeCard.toString(), "lugia")) {
                card.setState(State.sleep);
                card.startStateTime();
            } else if (Objects.equals(activeCard.toString(), "ducklett"))
                enemy.paralyze(target);
        }
    }

    //end of turn general methods:
    private static void stateChecker(Player player) {
        for (int i = 0; i < 4; i++) {
            Card card = player.getTable().getCard(i, 0);
            if (card != null) {
                Pokemon card1 = (Pokemon) card;
                if (card1.getState() == State.sleep) {
                    if (card1.getStateTime() == 0)
                        card1.setState(State.none);
                    else
                        card1.passStateTime();
                } else if (card1.getState() == State.burning) {
                    if (card1.getStateTime() == 0) {
                        double point = 10;
                        if (Objects.equals(card.generalType(), "plant")) {
                            Plant card2 = (Plant) card1;
                            point -= card2.getShield();
                            if (point > 0) {
                                card2.setForceShield(0);
                                if (card1.isCardAlive(point))
                                    card1.damage(point);
                                else
                                    player.getTable().benchKiller(i);
                            } else
                                card2.setForceShield(-point);
                        } else {
                            if (card1.isCardAlive(point))
                                card1.damage(point);
                            else
                                player.getTable().benchKiller(i);
                        }
                        card1.setState(State.none);
                    } else
                        card1.passStateTime();
                }
            }
        }
    }

    private static void scoreGiver(Player me, Player opponent) {
        double hitpoint = 0;
        ArrayList<Card> deck = me.getDeckCards();
        ArrayList<Card> deckCopy = me.getDeckCardsCopy();
        Table table = me.getTable();
        int killCount = deckCopy.size() - deck.size() - table.size();
        for (Card card : deckCopy) {
            if (!Objects.equals(card.generalType(), "energy")) {
                Card cardInDeck = null;
                Card cardInTable = table.getCard(card.toString());
                for (Card card1 : deck)
                    if (Objects.equals(card.toString(), card1.toString())) {
                        cardInDeck = card1;
                        break;
                    }
                if (cardInTable != null)
                    hitpoint += ((Pokemon) card).maxHitpoint() - ((Pokemon) cardInTable).getHitpoint();
                else if (cardInDeck != null)
                    hitpoint += ((Pokemon) card).maxHitpoint() - ((Pokemon) cardInDeck).getHitpoint();
                else
                    hitpoint += ((Pokemon) card).maxHitpoint();
            }
        }
        opponent.setCoin(hitpoint);
        opponent.setExperience(killCount);
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