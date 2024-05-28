package model.cards;

public class Ducklett extends Water {
    public String toString() {
        return "ducklett";
    }

    public Ducklett() {
        this.hitpoint = 70;
    }

    public int maxHitpoint() {
        return 70;
    }

    int power() {
        return 20;
    }

    double resistance() {
        return 0.6;
    }

    double weakness(Pokemon pokemon) {
        switch (pokemon.toString()) {
            case "tepig":
            case "dragonite":
            case "lugia":
            case "ducklett":
                return 1.0;
            case "pineco":
            case "rowlet":
                return 1.5;
            default:
                return 0;
        }
    }
}
