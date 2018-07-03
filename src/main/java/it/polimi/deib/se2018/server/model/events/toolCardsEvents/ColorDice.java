package it.polimi.deib.se2018.server.model.events.toolCardsEvents;

import it.polimi.deib.se2018.server.model.dice.DiceColor;
import it.polimi.deib.se2018.server.model.events.Event;

import java.io.Serializable;

/**
 * Dice color class
 * @author Sirlan Fernandes
 */
public class ColorDice extends Event implements Serializable {
    private String playerNickname;
    private DiceColor color;
    private final int numCard;

    /**
     * Constructor, initializes dice color class
     * @param name player's nickname
     * @param color dice color
     * @param num tool card number
     */
    public ColorDice(String name,DiceColor color, int num){
        playerNickname=name;
        this.color=color;
        this.numCard=num;


    }

    /**
     * Gets player's nickname
     * @return player's nickname
     */
    public String getPlayerNickName() {
        return playerNickname;
    }

    /**
     * Gets dice color
     * @return dice color
     */
    public DiceColor getColor() {
        return color;
    }

    /**
     * Gets tool card number
     * @return tool card number
     */
    public int getCardNumber() {
        return numCard;
    }

}
