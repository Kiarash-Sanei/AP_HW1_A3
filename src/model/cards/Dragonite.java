package model.cards;

public class Dragonite extends Fire {
    public String toString() {
        return "dragonite";
    }

    public Dragonite() {
        this.hitpoint = 120;
    }

    public int maxHitpoint() {
        return 120;
    }

    int power() {
        return 40;
    }

    double resistance() {
        return 0.7;
    }

    double weakness(Pokemon pokemon) {
        switch (pokemon.toString()) {
            case "tepig":
            case "dragonite":
            case "pineco":
            case "rowlet":
                return 1.0;
            case "lugia":
            case "ducklett":
                return 1.2;
            default:
                return 0;
        }
    }
}
