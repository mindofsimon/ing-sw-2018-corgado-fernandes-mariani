package it.polimi.deib.se2018.server.model.events;

import it.polimi.deib.se2018.server.model.dice.Dice;

import java.io.Serializable;

public class DicePlacement extends Event implements Serializable {

    private final String playerNickName;

    private final int row;

    private final int column;

    private final Dice dice;

    public DicePlacement(String n, int r, int c, Dice d){
        playerNickName=n;
        column=c;
        row=r;
        dice=d;
    }

    public String getPlayerNickName() {
        return playerNickName;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public Dice getDice() { return dice; }
}