package it.polimi.deib.se2018.model.dice;

public class Dice {
    private DiceColor color;
    private int value;

    //Constructor
    public Dice(DiceColor color){
        setValue(0);
        setColor(color);
    }

    //"Getters" methods


    public int getValue() {
        return value;
    }

    public DiceColor getColor() {
        return color;
    }

    //"Setters" methods
    public void setValue(int value){
        this.value=value;
    }

    public void setColor(DiceColor color){
        this.color=color;
    }

}
