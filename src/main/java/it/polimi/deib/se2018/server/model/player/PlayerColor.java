package it.polimi.deib.se2018.server.model.player;

import java.io.Serializable;

/**
 * Possible player colors: blue, green, red, violet
 */
public enum PlayerColor implements Serializable {
    BLUE("B"),GREEN("G"),RED("R"),VIOLET("V");
    private final String abbreviation;

    PlayerColor(String abbreviation){
        this.abbreviation = abbreviation;
    }

    @Override
    public String toString() {
        return abbreviation;
    }
}
