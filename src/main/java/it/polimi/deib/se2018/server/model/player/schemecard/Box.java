package it.polimi.deib.se2018.server.model.player.schemecard;

import it.polimi.deib.se2018.server.model.dice.Dice;
import it.polimi.deib.se2018.server.model.dice.DiceColor;

import java.io.Serializable;

/**
 * Box class
 * @author Simone Mariani, Coreena Corgado
 */
public class Box implements Serializable {//It's a white box (no color, no value)
    private Dice dice;

    /**
     * Gets dice
     * @return dice
     */
    public Dice getDice(){
        return dice;
    }

    /**
     * Sets dice
     * @param d dice
     */
    public void setDice(Dice d){
        dice=d;
    }

    /**
     * Gets color
     * @return null
     */
    public DiceColor getColor(){return null;}

    /**
     * Gets value
     * @return 0
     */
    public int getValue(){return 0;}

}

