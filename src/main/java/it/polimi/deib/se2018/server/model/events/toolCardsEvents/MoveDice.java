package it.polimi.deib.se2018.server.model.events.toolCardsEvents;

import it.polimi.deib.se2018.server.model.events.Event;

import java.io.Serializable;

public class MoveDice extends Event implements Serializable {


    private final String playerNickName;

    private final int row;

    private final int column;

    private final int dicerow;

    private final int dicecolum;

    private final int numCard;



    public MoveDice(String n, int rd, int cd,int r, int c,int num){
        playerNickName=n;
        column=c;
        row=r;
        dicerow=rd;
        dicecolum=cd;
        numCard=num;

    }

    public String getPlayerNickName() {
        return playerNickName;
    }

    public int getDiceRow(){
        return dicerow;
    }

    public int getDiceColum(){
        return dicecolum;
    }


    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getCardNumber() {
        return numCard;
    }
}

