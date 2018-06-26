package it.polimi.deib.se2018.server.model.events;

import java.io.Serializable;

public class EndCardActivation extends Event implements Serializable {

    private String playerNickName;
    private int cardNumber;

    public EndCardActivation (String n,int num){
        playerNickName=n;
        cardNumber=num;
    }

    public String getPlayerNickName() {
        return playerNickName;
    }
    public int getCardNumber(){return cardNumber;}
}
