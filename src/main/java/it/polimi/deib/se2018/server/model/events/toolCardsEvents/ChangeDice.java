package it.polimi.deib.se2018.server.model.events.toolCardsEvents;

import it.polimi.deib.se2018.server.model.dice.Dice;
import it.polimi.deib.se2018.server.model.events.Event;

import java.io.Serializable;

/**
 * Changes dice class
 * @author Sirlan Fernandes
 */
public class ChangeDice extends Event implements Serializable {
    private final String playerNickName;

    private final Dice dice;

    private final int numCard;

    private final String action;

    /**
     * Constructor, initializes change dice class
     * @param n player's nickname
     * @param dice dice
     * @param action action
     * @param num tool card number
     */
    public ChangeDice(String n,Dice dice,String action,int num){
        this.playerNickName=n;
        this.dice=dice;
        this.numCard=num;
        this.action=action;

    }

    /**
     * Gets dice
     * @return dice
     */
    public Dice getDice() {
        return dice;
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

    /**
     * Gets action
     * @return action
     */
    public String getAction() {
        return action;
    }
}
