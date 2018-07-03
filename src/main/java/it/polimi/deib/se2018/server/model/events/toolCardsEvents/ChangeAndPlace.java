package it.polimi.deib.se2018.server.model.events.toolCardsEvents;

import it.polimi.deib.se2018.server.model.dice.Dice;
import it.polimi.deib.se2018.server.model.events.Event;

import java.io.Serializable;

/**
 * Changes and places dice class
 * @author Sirlan Fernandes
 */
public class ChangeAndPlace extends Event implements Serializable {
    private final String playerNickName;

    private final Dice dice;


    private final int numCard;

    /**
     * Constructor, initializes change and place class
     * @param n player's nickname
     * @param diceS dice
     * @param num tool card number
     */
    public ChangeAndPlace(String n,Dice diceS,int num){
        this.playerNickName=n;
        this.dice=diceS;
        this.numCard=num;


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
}

