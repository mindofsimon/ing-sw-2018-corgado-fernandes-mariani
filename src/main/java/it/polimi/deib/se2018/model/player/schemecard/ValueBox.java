package it.polimi.deib.se2018.model.player.schemecard;

public class ValueBox extends Box {
    private int value;

    //Constructor
    public ValueBox(int value){
        setValue(value);
    }

    //"Getters" methods
    public int getValue() {
        return value;
    }

    //"Setters" methods
    public void setValue(int value){
        this.value=value;
    }
}
