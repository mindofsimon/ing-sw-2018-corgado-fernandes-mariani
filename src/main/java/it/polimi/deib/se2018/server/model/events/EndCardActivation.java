package it.polimi.deib.se2018.server.model.events;

import java.io.Serializable;

/**
 * End card activation class
 * @author Simone Mariani, Coreena Corgado
 */
public class EndCardActivation extends Event implements Serializable {

    private String playerNickName;
    private int cardNumber;

    /**
     * Constructor, initializes end card activation class
     * @param n player's nickname
     * @param num card number
     */
    public EndCardActivation (String n,int num){
        playerNickName=n;
        cardNumber=num;
    }

    /**
     * Gets player's nickname
     * @return player's nickname
     */
    public String getPlayerNickName() {
        return playerNickName;
    }

    /**
     * Gets card number
     * @return card number
     */
    public int getCardNumber(){return cardNumber;}
}
