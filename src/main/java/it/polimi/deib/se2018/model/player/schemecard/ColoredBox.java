package it.polimi.deib.se2018.model.player.schemecard;

import it.polimi.deib.se2018.model.dice.DiceColor;

public class ColoredBox extends Box{
    private DiceColor color;

    //Constructor
    public ColoredBox(DiceColor color){
        setColor(color);
    }

    //"Getters" methods
    public DiceColor getColor() {
        return color;
    }

    //"Setters" methods
    public void setColor(DiceColor color){
        this.color=color;
    }

}
