package model;

import model.cards.*;

import java.util.*;

public class Player {
    private final static ArrayList<Player> players = new ArrayList<>();
    private static Player loggedInPlayer;
    private final ArrayList<Card> allCards;
    private final ArrayList<Card> deckCards;
    private final ArrayList<Card> notDeckCards;
    private final String username;
    private final String password;
    private int coin;
    private int experience;
    private Table table;
    private ArrayList<Card> deckCardsCopy;
    private boolean hasPlayedEnergy;

    public Player(String username, String password) {
        this.allCards = new ArrayList<>();
        this.deckCards = new ArrayList<>();
        this.notDeckCards = new ArrayList<>();
        this.username = username;
        this.password = password;
        this.coin = 300;
        this.experience = 0;
        this.table = new Table();
        Player.players.add(this);
    }

    public String getUsername() {
        return username;
    }

    public static Player findPlayer(String username) {
        for (Player player : Player.players)
            if (Objects.equals(player.username, username))
                return player;
        return null;
    }

    public boolean passwordComparator(String password) {
        return Objects.equals(this.password, password);
    }

    public int deckCardsCount() {
        return this.deckCards.size();
    }

    public int getCoin() {
        return coin;
    }

    public int getExperience() {
        return experience;
    }

    public ArrayList<Card> getAllCards() {
        return this.allCards;
    }

    public static Player getLoggedInPlayer() {
        return Player.loggedInPlayer;
    }

    public boolean findCardInDeckCards(String cardName) {
        for (Card card : this.deckCards)
            if (Objects.equals(card.toString(), cardName))
                return true;
        return false;
    }

    public Card getCardInDeckCards(String cardName) {
        for (Card card : this.deckCards)
            if (Objects.equals(card.toString(), cardName))
                return card;
        return null;
    }

    public boolean findCardInAllCards(String cardName) {
        for (Card card : this.allCards)
            if (Objects.equals(card.toString(), cardName))
                return true;
        return false;
    }

    public boolean findCardInNotDeckCards(String cardName) {
        for (Card card : this.notDeckCards)
            if (Objects.equals(card.toString(), cardName))
                return true;
        return false;
    }

    public void addCardToDeck(String cardName) {
        for (Card card : this.notDeckCards)
            if (Objects.equals(card.toString(), cardName)) {
                this.deckCards.add(card);
                this.notDeckCards.remove(card);
                break;
            }
    }

    public void removeCardFromDeck(String cardName) {
        for (Card card : this.deckCards)
            if (Objects.equals(card.toString(), cardName)) {
                this.notDeckCards.add(card);
                this.deckCards.remove(card);
                break;
            }
    }

    public void putCard(Card card) {
        for (Card card1 : this.allCards)
            if (Objects.equals(card1.toString(), card.toString())) {
                this.allCards.remove(card1);
                break;
            }
        this.deckCards.remove(card);
        if (Objects.equals(card.generalType(), "energy"))
            this.hasPlayedEnergy = true;
    }

    public ArrayList<Card> getDeckCards() {
        return this.deckCards;
    }

    public static void setLoggedInPlayer(Player player) {
        Player.loggedInPlayer = player;
    }

    private static void rankOrganizer() {
        for (int i = 0; i < Player.players.size(); i++) {
            for (int j = i + 1; j < Player.players.size(); j++) {
                Player player1 = Player.players.get(i);
                Player player2 = Player.players.get(j);
                if (player1.experience < player2.experience)
                    Collections.swap(Player.players, i, j);
                else if (player1.experience == player2.experience &&
                        player1.username.compareTo(player2.username) > 0)
                    Collections.swap(Player.players, i, j);
            }
        }
    }

    public int rankCalculator() {
        Player.rankOrganizer();
        return Player.players.indexOf(this) + 1;
    }

    public static ArrayList<Player> getPlayers() {
        Player.rankOrganizer();
        return Player.players;
    }

    public void buyCard(String cardName) {
        Card card;
        switch (cardName) {
            case "dragonite":
                card = new Dragonite();
                break;
            case "tepig":
                card = new Tepig();
                break;
            case "lugia":
                card = new Lugia();
                break;
            case "ducklett":
                card = new Ducklett();
                break;
            case "pineco":
                card = new Pineco();
                break;
            case "rowlet":
                card = new Rowlet();
                break;
            case "pink":
                card = new Pink();
                break;
            case "yellow":
                card = new Yellow();
                break;
            default:
                card = null;
        }
        this.allCards.add(card);
        this.notDeckCards.add(card);
        this.coin -= Objects.requireNonNull(card).priceForBuy();
    }

    public void sellCard(String cardName) {
        for (Card card : this.allCards)
            if (Objects.equals(card.toString(), cardName)) {
                this.allCards.remove(card);
                if (!this.notDeckCards.remove(card))
                    this.deckCards.remove(card);
                this.coin += card.priceForSell();
                break;
            }
    }

    public Table getTable() {
        return table;
    }

    public void setExperience(int killCount) {
        this.experience += killCount * 10;
    }

    public void setCoin(double hit) {
        this.coin += (int) hit / 10;
    }

    public ArrayList<Card> getDeckCardsCopy() {
        return this.deckCardsCopy;
    }

    public void reset() {
        this.table = new Table();
    }

    public void start() {
        this.hasPlayedEnergy = false;
        this.deckCardsCopy = new ArrayList<>();
        for (Card card : this.deckCards)
            switch (card.toString()) {
                case "dragonite":
                    this.deckCardsCopy.add(new Dragonite());
                    break;
                case "tepig":
                    this.deckCardsCopy.add(new Tepig());
                    break;
                case "lugia":
                    this.deckCardsCopy.add(new Lugia());
                    break;
                case "ducklett":
                    this.deckCardsCopy.add(new Ducklett());
                    break;
                case "pineco":
                    this.deckCardsCopy.add(new Pineco());
                    break;
                case "rowlet":
                    this.deckCardsCopy.add(new Rowlet());
                    break;
                case "pink":
                    this.deckCardsCopy.add(new Pink());
                    break;
                case "yellow":
                    this.deckCardsCopy.add(new Yellow());
            }
    }

    public void turnReset() {
        this.hasPlayedEnergy = false;
        Table table = this.table;
        for (int i = 0; i < 4; i++) {
            Card card = table.getCard(i, 0);
            if (card != null &&
                    Objects.equals(card.generalType(), "plant"))
                ((Plant) card).setForceShield(15);
        }
    }

    public boolean hasPlayedEnergy() {
        return this.hasPlayedEnergy;
    }
}
