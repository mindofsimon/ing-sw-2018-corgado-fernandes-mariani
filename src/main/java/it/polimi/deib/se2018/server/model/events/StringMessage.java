package it.polimi.deib.se2018.server.model.events;

import it.polimi.deib.se2018.server.model.player.Player;

import java.io.Serializable;

/**
 * String message class
 * @author Simone Mariani
 */
public class StringMessage extends Message implements Serializable {

    private String message;
    private Player player;

    /**
     * Constructor, initializes string message class
     * @param message message
     * @param player player
     */
    public StringMessage(String message, Player player) {
        this.message = message;
        this.player=player;
    }

    /**
     * Gets message
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Gets player
     * @return player
     */
    public Player getPlayer(){return player;}
}
