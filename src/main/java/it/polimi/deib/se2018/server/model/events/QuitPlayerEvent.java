package it.polimi.deib.se2018.server.model.events;

import java.io.Serializable;

/**
 * Quit player event class
 * @author Simone Mariani, Coreena Corgado
 */
public class QuitPlayerEvent extends Event implements Serializable {

    private String playerNickName;

    /**
     * Constructor, initializes quit player event class
     * @param name player's nickname
     */
    public QuitPlayerEvent(String name){
        playerNickName=name;
    }

    /**
     * Gets player's nickname
     * @return player's nickname
     */
    @Override
    public String getPlayerNickName() {
        return playerNickName;
    }
}