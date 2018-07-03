package it.polimi.deib.se2018.server.model.events;

import it.polimi.deib.se2018.server.model.dice.Dice;

import java.io.Serializable;

/**
 * Dice placement class
 * @author Simone Mariani, Coreena Corgado
 */
public class DicePlacement extends Event implements Serializable {

    private final String playerNickName;
    private final int row;
    private final int column;
    private final Dice dice;

    /**
     * Constructor, initializes dice placement class
     * @param n player's nickname
     * @param r row
     * @param c column
     * @param d dice
     */
    public DicePlacement(String n, int r, int c, Dice d){
        playerNickName=n;
        column=c;
        row=r;
        dice=d;
    }

    /**
     * Gets player's nickname
     * @return player's nickname
     */
    public String getPlayerNickName() {
        return playerNickName;
    }

    /**
     * Gets row
     * @return row
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets column
     * @return column
     */
    public int getColumn() {
        return column;
    }

    /**
     * Gets dice
     * @return dice
     */
    public Dice getDice() { return dice; }
}