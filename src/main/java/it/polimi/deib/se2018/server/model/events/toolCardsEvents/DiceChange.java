package it.polimi.deib.se2018.server.model.events.toolCardsEvents;

import it.polimi.deib.se2018.server.model.dice.Dice;
import it.polimi.deib.se2018.server.model.events.Message;
import it.polimi.deib.se2018.server.model.player.Player;

import java.io.Serializable;

/**
 * Change of dice class
 * Sends change of dice message to view
 * @author Sirlan Fernandes
 */
public class DiceChange extends Message implements Serializable {


    private String message;
    private Player player;
    private Dice dice;

    /**
     * Constructor, initializes change of dice class
     * @param message msessage
     * @param player player
     * @param dice dice
     */
    public DiceChange(String message, Player player,Dice dice) {
        this.message = message;
        this.player=player;
        this.dice=dice;
    }

    /**
     * Gets message
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Gets dice
     * @return dice
     */
    public Dice getDice() {
        return dice;
    }


    /**
     * Gets player
     * @return player
     */
    public Player getPlayer(){return player;}
}
