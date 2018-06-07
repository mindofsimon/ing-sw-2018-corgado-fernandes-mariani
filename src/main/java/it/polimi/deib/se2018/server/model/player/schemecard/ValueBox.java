package it.polimi.deib.se2018.server.model.player.schemecard;

import java.io.Serializable;

/**
 * Value box class
 * @author Simone Mariani
 */
public class ValueBox extends Box implements Serializable {
    private int value;

    /**
     * Constructor
     * @param value box value
     */
    public ValueBox(int value){
        setValue(value);
    }

    //"Getters" methods
    /**
     * Gets value
     * @return value
     */
    public int getValue() {
        return value;
    }

    //"Setters" methods
    /**
     * Sets value
     * @param value box value
     */
    public void setValue(int value){
        this.value=value;
    }
}
