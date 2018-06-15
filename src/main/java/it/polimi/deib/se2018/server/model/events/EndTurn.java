package it.polimi.deib.se2018.server.model.events;

import java.io.Serializable;

/**
 * End turn class
 * @author Simone Mariani
 */
public class EndTurn extends Event implements Serializable {

    private String playerNickName;

    /**
     * Constructor, initializes end turn class
     * @param n player's nickname
     */
    public EndTurn(String n){
        playerNickName=n;
    }

    /**
     * Gets player's nickname
     * @return player's nickname
     */
    public String getPlayerNickName() {
        return playerNickName;
    }
}
