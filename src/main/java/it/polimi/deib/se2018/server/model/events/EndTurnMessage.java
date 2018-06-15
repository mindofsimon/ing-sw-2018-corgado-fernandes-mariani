package it.polimi.deib.se2018.server.model.events;

import it.polimi.deib.se2018.server.model.Model;
import it.polimi.deib.se2018.server.model.player.Player;

import java.io.Serializable;

/**
 * End turn message class
 * @author Simone Mariani
 */
public class EndTurnMessage extends Message implements Serializable {


    private final Player player;
    private final Model model;

    /**
     * Constructor, initializes end turn message class
     * @param p player
     * @param m model
     */
    public EndTurnMessage(Player p,Model m) {
        model = m;
        player = p;
    }

    /**
     * Gets player
     * @return player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets model
     * @return model
     */
    public Model getModel() {
        return model;
    }

}