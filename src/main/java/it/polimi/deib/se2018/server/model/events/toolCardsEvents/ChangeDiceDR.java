package it.polimi.deib.se2018.server.model.events.toolCardsEvents;

import it.polimi.deib.se2018.server.model.dice.Dice;
import it.polimi.deib.se2018.server.model.events.Event;

import java.io.Serializable;

public class ChangeDiceDR extends Event implements Serializable {
    private final String playerNickName;

    private final Dice dice;

    private final Dice diceR;

    private final int numCard;



    public ChangeDiceDR(String n,Dice diceS,Dice diceR,int num){
        this.playerNickName=n;
        this.dice=diceS;
        this.numCard=num;
        this.diceR=diceR;

    }


    public Dice getDice() {
        return dice;
    }
    public Dice getDiceRound() {
        return diceR;
    }

    public int getCardNumber() {
        return numCard;
    }

    public String getPlayerNickName() {
        return playerNickName;
    }

}
