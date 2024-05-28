package model.cards;

import model.Card;
import model.State;

import java.util.ArrayList;

public abstract class Pokemon extends Card {
    State state;
    int stateTime;
    double hitpoint;

    abstract public int maxHitpoint();

    abstract double resistance();

    abstract int power();

    abstract double weakness(Pokemon pokemon);

    abstract double energy(Energy energy);

    public static double pointCalculator(ArrayList<Card> influencers, Pokemon influenced) {
        double result;
        if (influencers.get(1) == null) {
            result = ((Pokemon) influencers.get(0)).power() *
                    influenced.weakness((Pokemon) influencers.get(0)) *
                    influenced.resistance();
        } else if (influencers.get(2) == null) {
            result = ((Pokemon) influencers.get(0)).power() *
                    influenced.weakness((Pokemon) influencers.get(0)) *
                    influenced.resistance() *
                    ((Pokemon) influencers.get(0)).energy((Energy) influencers.get(1));
        } else {
            result = ((Pokemon) influencers.get(0)).power() *
                    influenced.weakness((Pokemon) influencers.get(0)) *
                    influenced.resistance() *
                    ((Pokemon) influencers.get(0)).energy((Energy) influencers.get(1)) *
                    ((Pokemon) influencers.get(0)).energy((Energy) influencers.get(2));
        }
        return result;
    }

    public void setState(State state) {
        this.state = state;
    }

    public double getHitpoint() {
        return this.hitpoint;
    }

    public void startStateTime() {
        this.stateTime = 1;
    }

    public int getStateTime() {
        return this.stateTime;
    }

    public void passStateTime() {
        this.stateTime--;
    }

    public State getState() {
        return this.state;
    }

    public void healer(double healPoint) {
        if (this.maxHitpoint() < this.getHitpoint() + healPoint)
            this.hitpoint = this.maxHitpoint();
        else
            this.hitpoint += healPoint;
    }

    public void damage(double hitpoint) {
        this.hitpoint -= hitpoint;
    }

    public boolean isCardAlive(double hitpoint) {
        return hitpoint < this.hitpoint;
    }
}
