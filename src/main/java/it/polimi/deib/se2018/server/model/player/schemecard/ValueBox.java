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
    //Constructor
    public ValueBox(int value){
        setValue(value);
    }

    //"Getters" methods
    /**
     * Get value
     * @return value
     */
    public int getValue() {
        return value;
    }

    //"Setters" methods
    /**
     * Set value
     * @param value box value
     */
    public void setValue(int value){
        this.value=value;
    }
}
