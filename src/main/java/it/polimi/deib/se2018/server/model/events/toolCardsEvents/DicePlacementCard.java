package it.polimi.deib.se2018.server.model.events.toolCardsEvents;

import it.polimi.deib.se2018.server.model.dice.Dice;
import it.polimi.deib.se2018.server.model.events.Event;

import java.io.Serializable;
/**
 * Dice placement card class
 * @author Sirlan Fernandes
 */
public class DicePlacementCard extends Event implements Serializable {

    private final String playerNickName;

    private final int row;

    private final int column;

    private final Dice dice;

    private final int numCard;


    /**
     * Constructor, initializes dice placement card class
     * @param n player's nickname
     * @param r row
     * @param c column
     * @param d dice
     * @param num tool card number
     */
    public DicePlacementCard(String n, int r, int c, Dice d,int num){
        playerNickName=n;
        column=c;
        row=r;
        dice=d;
        numCard=num;
    }

    /**
     * Gets player's nickname
     * @return player's nickname
     */
    public String getPlayerNickName() {
        return playerNickName;
    }


    /**
     * Gets tool card number
     * @return tool card number
     */
    public int getCardNumber(){return numCard;}


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

