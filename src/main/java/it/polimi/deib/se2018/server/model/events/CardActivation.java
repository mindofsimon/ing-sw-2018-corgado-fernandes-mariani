package it.polimi.deib.se2018.server.model.events;

import java.io.Serializable;

/**
 * Card activation class
 * @author Simone Mariani
 */
public class CardActivation extends Event implements Serializable {

    private String playerNickName;
    private int cardNumber;

    /**
     * Constructor, initializes card activation class
     * @param name player's nickname
     * @param n card number
     */
    public CardActivation(String name,int n){
        playerNickName=name;
        cardNumber=n;
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
}
