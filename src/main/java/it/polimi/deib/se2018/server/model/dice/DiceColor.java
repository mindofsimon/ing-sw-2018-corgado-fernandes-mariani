package it.polimi.deib.se2018.server.model.dice;

import java.io.Serializable;

/**
 * Possible dice colors: blue, green, red, violet, yellow
 */
public enum DiceColor implements Serializable {
    BLUE("B"),GREEN("G"),RED("R"),VIOLET("V"),YELLOW("Y");

    private final String abbreviation;

    DiceColor(String abbreviation){
        this.abbreviation = abbreviation;
    }

    @Override
    public String toString() {
        return abbreviation;
    }

}
