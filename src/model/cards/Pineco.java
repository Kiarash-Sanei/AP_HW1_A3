package model.cards;

public class Pineco extends Plant {
    public String toString() {
        return "pineco";
    }

    public Pineco() {
        this.hitpoint = 110;
    }

    public int maxHitpoint() {
        return 110;
    }

    int power() {
        return 25;
    }

    double resistance() {
        return 0.9;
    }

    double weakness(Pokemon pokemon) {
        return 1.0;
    }
}
