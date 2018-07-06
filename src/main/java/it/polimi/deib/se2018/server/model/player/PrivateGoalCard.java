package it.polimi.deib.se2018.server.model.player;

import it.polimi.deib.se2018.server.model.dice.DiceColor;

import java.io.Serializable;

/**
 * Private goal card class
 * @author Simone Mariani,Sirlan Fernandes
 */
public class PrivateGoalCard implements Serializable {
    private DiceColor color;
    private String name;

    /**
     * Constructor, initializes private goal card class
     * @param color dice color
     * @param name name
     */
    public PrivateGoalCard(DiceColor color,String name) {
        this.color = color;
        this.name = name;
    }

    //"Getters" methods
    /**
     * Gets color
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