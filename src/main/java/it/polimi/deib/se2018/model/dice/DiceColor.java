package it.polimi.deib.se2018.model.dice;

public enum DiceColor {
    BLU("B"),GREEN("G"),RED("R"),VIOLET("V"),YELLOW("Y");

    private final String abbreviation;

    //Constructor...to use abbreviation
    DiceColor(String abbreviation){
        this.abbreviation = abbreviation;
    }

    //toString() to represent the DiceColor
    @Override
    public String toString() {
        return abbreviation;
    }
}
