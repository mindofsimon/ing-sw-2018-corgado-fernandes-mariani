package it.polimi.deib.se2018.server.model.events;

import it.polimi.deib.se2018.server.model.player.Player;

import java.io.Serializable;

/**
 * Quit player message class
 * @author Simone Mariani, Coreena Corgado
 */
public class QuitPlayerMessage extends Message implements Serializable {

    private Player player;

    /**
     * Constructor, initializes quit player message class
     * @param p player
     */
    public QuitPlayerMessage(Player p){
        player=p;
    }

    /**
     * Gets player
     * @return player
     */
    @Override
    public Player getPlayer() {
        return player;
    }
}
