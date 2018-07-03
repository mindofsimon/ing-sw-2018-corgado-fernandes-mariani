package it.polimi.deib.se2018.server.model.events;

import it.polimi.deib.se2018.server.model.player.Player;

import java.io.Serializable;

/**
 * String message error class
 * @author Simone Mariani, Coreena Corgado
 */
public class StringMessageError extends Message implements Serializable {
    private String message;
    private Player player;
    private int type;

    /**
     * Constructor, initializes string message error class
     * @param message message
     * @param player player
     * @param type type
     */
    public StringMessageError(String message, Player player,int type) {
        this.message = message;
        this.player=player;
        this.type=type;
    }

    /**
     * Gets message
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Gets type
     * @return type
     */
    public int getType() {
        return type;
    }

    /**
     * Gets player
     * @return player
     */
    public Player getPlayer(){return player;}

}
