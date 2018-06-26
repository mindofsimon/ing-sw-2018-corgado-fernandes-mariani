package it.polimi.deib.se2018.server.model.events;

import it.polimi.deib.se2018.server.model.dice.Dice;

import java.io.Serializable;

/**
 * Card activation class
 * @author Simone Mariani
 */
public class CardActivation extends Event implements Serializable {

    private String playerNickName;
    private int cardNumber;
    private Dice dice;

    /**
     * Constructor, initializes card activation class
     * @param name player's nickname
     * @param n card number
     * @param dice dice to activate the card in single player
     */
    public CardActivation(String name,int n,Dice dice){
        playerNickName=name;
        cardNumber=n;
        this.dice=dice;
    }

    //"Getters" methods

    /**
     * Gets player's nickname
     * @return player's nickname
     */
    public String  getPlayerNickName() {
        return playerNickName;
    }

    /**
     * Gets card number
     * @return card number
     */
    public int getCardNumber() {
        return cardNumber;
    }

    /**
     * Gets dice
     * @return dice
     */
    public Dice getDice() {
        return dice;
    }
}
