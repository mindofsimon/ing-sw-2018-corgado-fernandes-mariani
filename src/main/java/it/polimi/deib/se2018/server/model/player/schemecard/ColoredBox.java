package it.polimi.deib.se2018.server.model.player.schemecard;

import it.polimi.deib.se2018.server.model.dice.DiceColor;

import java.io.Serializable;

/**
 * Colored box class
 * @author Simone Mariani, Coreena Corgado
 */
public class ColoredBox extends Box implements Serializable{
    private DiceColor color;

    /**
     * Constructor
     * @param color dice color
     */
    public ColoredBox(DiceColor color){
        setColor(color);
    }

    //"Getters" methods
    /**
     * Gets color
     * @return color
     */
    public DiceColor getColor() {
        return color;
    }

    //"Setters" methods
    /**
     * Sets color
     * @param color dice color
     */
    public void setColor(DiceColor color){
        this.color=color;
    }

}

