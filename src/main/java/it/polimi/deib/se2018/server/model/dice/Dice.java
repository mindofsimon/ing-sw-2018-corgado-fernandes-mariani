package it.polimi.deib.se2018.server.model.dice;

import java.io.Serializable;

/**
 * Virtual dice class
 * @author Simone Mariani
 */
public class Dice implements Serializable {
    private DiceColor color;
    private int value;

    /**
     * Constructor, initializes dice class with initial value 0
     * @param color dice color
     */
    //Constructor
    public Dice(DiceColor color){
        setValue(0);
        setColor(color);
    }

    //"Getters" methods

    /**
     * Gets dice value
     * @return dice value
     */
    public int getValue() {
        return value;
    }

    /**
     * Gets dice color
     * @return dice color
     */
    public DiceColor getColor() {
        return color;
    }

    //"Setters" methods

    /**
     * Sets dice value
     * @param value dice value
     */
    public void setValue(int value){
        this.value=value;
    }

    /**
     * Sets dice color
     * @param color dice color
     */
    public void setColor(DiceColor color){
        this.color=color;
    }

}
