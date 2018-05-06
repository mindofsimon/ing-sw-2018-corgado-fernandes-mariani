package it.polimi.deib.se2018.model.player;

import it.polimi.deib.se2018.model.dice.DiceColor;

public class PrivateGoalCard {
    private DiceColor color;
    private String name;
    private String description;

    //Constructor
    public PrivateGoalCard(DiceColor color,String name,String description){
        this.color=color;
        this.name=name;
        this.description=description;
    }

    //"Getters" methods
    public DiceColor getColor() {
        return color;
    }
}