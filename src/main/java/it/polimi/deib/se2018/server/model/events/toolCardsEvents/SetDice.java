package it.polimi.deib.se2018.server.model.events.toolCardsEvents;

import it.polimi.deib.se2018.server.model.dice.Dice;
import it.polimi.deib.se2018.server.model.events.Event;

import java.io.Serializable;

/**
 * Sets dice class
 * @author Sirlan Fernandes
 */
public class SetDice extends Event implements Serializable {
    private final int value;
    private final Dice dice;

    /**
     * Constructor, initializes set dice class
     * @param dice dice
     * @param val dice value
     */
    public SetDice(Dice dice,int val){
        value=val;
        this.dice=dice;

    }


    /**
     * Gets dice value
     * @return dice value
     */
    public int getValue() {
        return value;
    }

    /**
     * Gets dice
     * @return dice
     */
    @Override
    public Dice getDice() {
        return this.dice;
    }
}
