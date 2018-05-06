package it.polimi.deib.se2018.model.player;

public enum PlayerColor {
    BLU("B"),GREEN("G"),RED("R"),VIOLET("V");
    private final String abbreviation;

    //Constructor...to use abbreviation
    PlayerColor(String abbreviation){
        this.abbreviation = abbreviation;
    }

    //toString() to represent the PlayerColor
    @Override
    public String toString() {
        return abbreviation;
    }
}
