package it.polimi.deib.se2018.server.model.events;

import java.io.Serializable;

public class CardActivation extends Event implements Serializable {

    private String playerNickName;
    private int cardNumber;

    //Constructor
    public CardActivation(String name,int n){
        playerNickName=name;
        cardNumber=n;
    }

    //"Getters" methods
    public String  getPlayerNickName() {
        return playerNickName;
    }

    public int getCardNumber() {
        return cardNumber;
    }
}
