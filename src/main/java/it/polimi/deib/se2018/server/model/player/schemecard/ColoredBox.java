package it.polimi.deib.se2018.server.model.player.schemecard;

import it.polimi.deib.se2018.server.model.dice.DiceColor;

import java.io.Serializable;

/**
 * Colored box class
 * @author Simone Mariani
 */
public class ColoredBox extends Box implements Serializable{
    private DiceColor color;

    /**
     * Constructor
     * @param color dice color
     */
    //Constructor
    public ColoredBox(DiceColor color){
        setColor(color);
    }

    //"Getters" methods
    /**
     * Get color
     * @return color
     */
    public DiceColor getColor() {
        return color;
    }

    //"Setters" methods
    /**
     * Set color
     * @param color dice color
     */
    public void setColor(DiceColor color){
        this.color=color;
    }

}

