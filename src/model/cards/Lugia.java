package model.cards;

public class Lugia extends Water {
    public String toString() {
        return "lugia";
    }

    public Lugia() {
        this.hitpoint = 90;
    }

    public int maxHitpoint() {
        return 90;
    }

    int power() {
        return 20;
    }

    double resistance() {
        return 0.7;
    }

    double weakness(Pokemon pokemon) {
        switch (pokemon.toString()) {
            case "tepig":
            case "dragonite":
                return 1.3;
            case "lugia":
            case "ducklett":
            case "pineco":
            case "rowlet":
                return 1.0;
            default:
                return 0;
        }
    }
}
