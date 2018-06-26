package it.polimi.deib.se2018.server.model.events;


import it.polimi.deib.se2018.server.model.dice.Dice;
import it.polimi.deib.se2018.server.model.dice.DiceColor;
import it.polimi.deib.se2018.server.model.player.Player;

import java.io.Serializable;

/**
 * Event class
 * @author Simone Mariani
 */
public class Event implements Serializable {

    /**
     * Gets player's nickname
     * @return null
     */
    public String getPlayerNickName(){return null;}

    /**
     * Gets row
     * @return -1
     */
    public int getRow() {
        return -1;
    }

    /**
     * Gets column
     * @return -1
     */
    public int getColumn() {
        return -1;
    }

    /**
     * Gets dice
     * @return null
     */
    public Dice getDice() {
        return null;
    }

    /**
     * Gets card number
     * @return -1
     */
    public int getCardNumber(){
        return -1;
    }

    /**
     * Gets scheme number
     * @return -1
     */
    public int getSchemeNumber(){return -1;}

    public Player getPlayer(){return null;}
    public DiceColor getColor() { return null; }
    public int getDiceRow(){
        return -1;
    }
    public int getValue() {
        return -1;
    }

    public int getDiceColum(){
        return -1;
    }
    public Dice getDiceRound() {
        return null;
    }
    //public Restriction getRestriction() {return null;}
    public String getAction(){return null;}
    public int getDifficult(){return -1;}


}