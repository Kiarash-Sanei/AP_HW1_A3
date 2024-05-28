package model;

import java.util.Objects;

public abstract class Card {
    abstract public String toString();

    public static int priceForBuy(String cardName) {
        switch (cardName) {
            case "dragonite":
                return 10;
            case "tepig":
                return 13;
            case "lugia":
                return 11;
            case "ducklett":
                return 15;
            case "pineco":
                return 9;
            case "rowlet":
                return 12;
            case "pink":
            case "yellow":
                return 5;
            default:
                return 0;
        }
    }

    public int priceForBuy() {
        switch (this.toString()) {
            case "dragonite":
                return 10;
            case "tepig":
                return 13;
            case "lugia":
                return 11;
            case "ducklett":
                return 15;
            case "pineco":
                return 9;
            case "rowlet":
                return 12;
            case "pink":
            case "yellow":
                return 5;
            default:
                return 0;
        }
    }

    public int priceForSell() {
        switch (this.toString()) {
            case "dragonite":
                return 8;
            case "tepig":
                return 10;
            case "lugia":
            case "rowlet":
                return 9;
            case "ducklett":
                return 11;
            case "pineco":
                return 7;
            case "pink":
            case "yellow":
                return 3;
            default:
                return 0;
        }
    }

    public static boolean nameIsNotValid(String name) {
        return !Objects.equals(name, "dragonite") &&
                !Objects.equals(name, "tepig") &&
                !Objects.equals(name, "lugia") &&
                !Objects.equals(name, "ducklett") &&
                !Objects.equals(name, "pineco") &&
                !Objects.equals(name, "rowlet") &&
                !Objects.equals(name, "pink") &&
                !Objects.equals(name, "yellow");
    }

    public String generalType() {
        switch (this.toString()) {
            case "dragonite":
            case "tepig":
                return "fire";
            case "lugia":
            case "ducklett":
                return "water";
            case "pineco":
            case "rowlet":
                return "plant";
            case "pink":
            case "yellow":
                return "energy";
            default:
                return "";
        }
    }
}
