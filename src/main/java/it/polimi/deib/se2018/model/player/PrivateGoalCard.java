package it.polimi.deib.se2018.model.player;

import it.polimi.deib.se2018.model.dice.DiceColor;

public class PrivateGoalCard {
    private DiceColor color;
    private String name;

    //Constructor
    public PrivateGoalCard(DiceColor color,String name) {
        this.color = color;
        this.name = name;
    }

    //"Getters" methods
    public DiceColor getColor() {
        return color;
    }

    @Override
    public String toString() {
        return name;
    }
}