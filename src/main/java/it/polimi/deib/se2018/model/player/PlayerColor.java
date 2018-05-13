package it.polimi.deib.se2018.model.player;

public enum PlayerColor {
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
