package it.polimi.deib.se2018.server.model.events;


import it.polimi.deib.se2018.server.model.dice.Dice;

import java.io.Serializable;

public class Event implements Serializable {

    public String getPlayerNickName(){return null;}

    public int getRow() {
        return -1;
    }

    public int getColumn() {
        return -1;
    }

    public Dice getDice() { return null; }

    public int getCardNumber(){return -1;}

    public int getSchemeNumber(){return -1;}
}