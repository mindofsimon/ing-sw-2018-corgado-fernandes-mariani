package it.polimi.deib.se2018.server.model.events.toolCardsEvents;

import it.polimi.deib.se2018.server.model.dice.Dice;
import it.polimi.deib.se2018.server.model.events.Event;

import java.io.Serializable;

public class SetDice extends Event implements Serializable {
    private final int value;
    private final Dice dice;


    public SetDice(Dice dice,int val){
        value=val;
        this.dice=dice;

    }



    public int getValue() {
        return value;
    }

    @Override
    public Dice getDice() {
        return this.dice;
    }
}
