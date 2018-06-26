package it.polimi.deib.se2018.server.model.events.toolCardsEvents;

import it.polimi.deib.se2018.server.model.dice.Dice;
import it.polimi.deib.se2018.server.model.events.Event;

import java.io.Serializable;

public class ChangeDice extends Event implements Serializable {
    private final String playerNickName;

    private final Dice dice;

    private final int numCard;

    private final String action;


    public ChangeDice(String n,Dice dice,String action,int num){
        this.playerNickName=n;
        this.dice=dice;
        this.numCard=num;
        this.action=action;

    }


    public Dice getDice() {
        return dice;
    }

    public int getCardNumber() {
        return numCard;
    }

    public String getPlayerNickName() {
        return playerNickName;
    }

    public String getAction() {
        return action;
    }
}
