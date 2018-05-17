package it.polimi.deib.se2018.model.dice;

/**
 * Virtual dice class
 * @author Simone Mariani
 */
public class Dice {
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
     * Get dice value
     * @return dice value
     */
    public int getValue() {
        return value;
    }

    /**
     * Get dice color
     * @return dice color
     */
    public DiceColor getColor() {
        return color;
    }

    //"Setters" methods

    /**
     * Set dice value
     * @param value dice value
     */
    public void setValue(int value){
        this.value=value;
    }

    /**
     * Set dice color
     * @param color dice color
     */
    public void setColor(DiceColor color){
        this.color=color;
    }

}
