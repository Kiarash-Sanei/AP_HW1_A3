package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class Table {
    private final ArrayList<ArrayList<Card>> cards;

    public Table() {
        this.cards = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            cards.add(i, new ArrayList<>());
            for (int j = 0; j < 3; j++)
                cards.get(i).add(j, null);
        }
    }

    public void setCard(int benchNumber, int index, Card card) {
        this.cards.get(benchNumber).add(index, card);
    }

    public ArrayList<Card> getBench(int benchNumber) {
        return this.cards.get(benchNumber);
    }

    public Card getCard(int benchNumber, int index) {
        return this.cards.get(benchNumber).get(index);
    }

    public Card getCard(String name) {
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 3; j++) {
                Card card = this.cards.get(i).get(j);
                if (card != null &&
                        Objects.equals(card.toString(), name))
                    return card;
            }
        return null;
    }

    public void benchSwapper(int benchNumber1, int benchNumber2) {
        Collections.swap(this.cards, benchNumber1, benchNumber2);
    }

    public void paralyze(int benchNumber) {
        this.cards.get(benchNumber).remove(1);
        this.cards.get(benchNumber).add(1, null);
        this.cards.get(benchNumber).remove(2);
        this.cards.get(benchNumber).add(2, null);
    }

    public void benchKiller(int benchNumber) {
        this.cards.get(benchNumber).remove(0);
        this.cards.get(benchNumber).add(0, null);
        this.cards.get(benchNumber).remove(1);
        this.cards.get(benchNumber).add(1, null);
        this.cards.get(benchNumber).remove(2);
        this.cards.get(benchNumber).add(2, null);
    }

    public void deleteEnergy() {
        ArrayList<Card> bench = this.cards.get(0);
        if (bench.get(2) != null) {
            Collections.swap(bench, 1, 2);
            bench.remove(2);
            bench.add(2, null);
        } else if (bench.get(1) != null) {
            bench.remove(1);
            bench.add(1, null);
        }
    }

    public int size() {
        int result = 0;
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 3; j++)
                if (this.cards.get(i).get(j) != null)
                    result++;
        return result;
    }
}
