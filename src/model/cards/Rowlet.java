package model.cards;

public class Rowlet extends Plant {
    public String toString() {
        return "rowlet";
    }

    public Rowlet() {
        this.hitpoint = 180;
    }

    public int maxHitpoint() {
        return 180;
    }

    int power() {
        return 40;
    }

    double resistance() {
        return 0.5;
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
