package it.polimi.deib.se2018.model.player.schemecard;

/**
 * Value box class
 * @author Simone Mariani
 */
public class ValueBox extends Box {
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
