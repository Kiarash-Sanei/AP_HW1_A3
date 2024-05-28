package model.cards;

public class Tepig extends Fire {
    public String toString() {
        return "tepig";
    }

    public Tepig() {
        this.hitpoint = 140;
    }

    public int maxHitpoint() {
        return 140;
    }

    int power() {
        return 25;
    }

    double resistance() {
        return 0.8;
    }

    double weakness(Pokemon pokemon) {
        switch (pokemon.toString()) {
            case "tepig":
            case "dragonite":
                return 1.0;
            case "lugia":
            case "ducklett":
                return 2.0;
            case "pineco":
            case "rowlet":
                return 1.3;
            default:
                return 0;
        }
    }
}
