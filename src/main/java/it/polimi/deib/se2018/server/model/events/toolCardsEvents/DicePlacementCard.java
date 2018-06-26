package it.polimi.deib.se2018.server.model.events.toolCardsEvents;

import it.polimi.deib.se2018.server.model.dice.Dice;
import it.polimi.deib.se2018.server.model.events.Event;

import java.io.Serializable;

public class DicePlacementCard extends Event implements Serializable {

    private final String playerNickName;

    private final int row;

    private final int column;

    private final Dice dice;

    private final int numCard;


    public DicePlacementCard(String n, int r, int c, Dice d,int num){
        playerNickName=n;
        column=c;
        row=r;
        dice=d;
        numCard=num;
    }

    public String getPlayerNickName() {
        return playerNickName;
    }

    public int getCardNumber(){return numCard;}

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public Dice getDice() { return dice; }
}

