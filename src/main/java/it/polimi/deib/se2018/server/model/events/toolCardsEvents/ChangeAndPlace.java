package it.polimi.deib.se2018.server.model.events.toolCardsEvents;

import it.polimi.deib.se2018.server.model.dice.Dice;
import it.polimi.deib.se2018.server.model.events.Event;

import java.io.Serializable;

public class ChangeAndPlace extends Event implements Serializable {
    private final String playerNickName;

    private final Dice dice;


    private final int numCard;



    public ChangeAndPlace(String n,Dice diceS,int num){
        this.playerNickName=n;
        this.dice=diceS;
        this.numCard=num;


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
}

