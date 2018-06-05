package it.polimi.deib.se2018.server.model.player;

import it.polimi.deib.se2018.server.model.dice.DiceColor;

import java.io.Serializable;

/**
 * Private goal card class
 * @author Simone Mariani
 */
public class PrivateGoalCard implements Serializable {
    private DiceColor color;
    private String name;

    /**
     * Constructor, initializes private goal card class
     * @param color dice color
     * @param name name
     */
    //Constructor
    public PrivateGoalCard(DiceColor color,String name) {
        this.color = color;
        this.name = name;
    }

    //"Getters" methods
    /**
     * Get color
     * @return dice color
     */
    public DiceColor getColor() {
        return color;
    }


    /**
     * Object text representation
     * @return name
     */
    @Override
    public String toString() {
        return name;
    }
}