package model.cards;

public abstract class Plant extends Pokemon {
    private double shield = 15;

    double energy(Energy energy) {
        switch (energy.toString()) {
            case "pink":
                return 1.15;
            case "yellow":
                return 1.2;
            default:
                return 0;
        }
    }

    public void setForceShield(double shield) {
        this.shield = shield;
    }

    public double getShield() {
        return this.shield;
    }
}
