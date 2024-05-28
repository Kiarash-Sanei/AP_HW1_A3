package model.cards;

public abstract class Water extends Pokemon {
    double energy(Energy energy) {
        switch (energy.toString()) {
            case "pink":
                return 1.05;
            case "yellow":
                return 1.2;
            default:
                return 0;
        }
    }
}
