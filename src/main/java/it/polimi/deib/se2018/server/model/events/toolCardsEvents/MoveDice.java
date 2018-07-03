package it.polimi.deib.se2018.server.model.events.toolCardsEvents;

import it.polimi.deib.se2018.server.model.events.Event;

import java.io.Serializable;

/** Moves dice class
 * @author Sirlan Fernandes
 */
public class MoveDice extends Event implements Serializable {


    private final String playerNickName;

    private final int row;

    private final int column;

    private final int dicerow;

    private final int dicecolum;

    private final int numCard;


    /**
     * Constructor, initializes move dice class
     * @param n player's nickname
     * @param rd dice's row
     * @param cd dice's column
     * @param r row
     * @param c column
     * @param num tool card number
     */
    public MoveDice(String n, int rd, int cd,int r, int c,int num){
        playerNickName=n;
        column=c;
        row=r;
        dicerow=rd;
        dicecolum=cd;
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
     * Gets dice's row
     * @return dice's row
     */
    public int getDiceRow(){
        return dicerow;
    }

    /**
     * Gets dice's column
     * @return dice's column
     */
    public int getDiceColum(){
        return dicecolum;
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
     * Gets tool card number
     * @return tool card number
     */
    public int getCardNumber() {
        return numCard;
    }
}

