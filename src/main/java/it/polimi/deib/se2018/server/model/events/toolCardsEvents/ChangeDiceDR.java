package it.polimi.deib.se2018.server.model.events.toolCardsEvents;

import it.polimi.deib.se2018.server.model.dice.Dice;
import it.polimi.deib.se2018.server.model.events.Event;

import java.io.Serializable;

/**
 * Changes dice from stock with a dice in round track class
 * @author Sirlan Fernandes
 */
public class ChangeDiceDR extends Event implements Serializable {
    private final String playerNickName;

    private final Dice dice;

    private final Dice diceR;

    private final int numCard;

    /**
     * Constructor, initializes ChangeDiceDR class
     * @param n name
     * @param diceS dice from stock
     * @param diceR dice from round track
     * @param num tool card number
     */
    public ChangeDiceDR(String n,Dice diceS,Dice diceR,int num){
        this.playerNickName=n;
        this.dice=diceS;
        this.numCard=num;
        this.diceR=diceR;

    }

    /**
     * Gets dice
     * @return dice
     */
    public Dice getDice() {
        return dice;
    }

    /**
     * Gets dice round
     * @return dice round
     */
    public Dice getDiceRound() {
        return diceR;
    }

    /**
     * Gets tool card number
     * @return tool card number
     */
    public int getCardNumber() {
        return numCard;
    }

    /**
     * Gets player's nickname
     * @return player's nickname
     */
    public String getPlayerNickName() {
        return playerNickName;
    }

}
