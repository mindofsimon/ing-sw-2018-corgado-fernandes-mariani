package it.polimi.deib.se2018.server.model.events;

import it.polimi.deib.se2018.server.model.Model;
import it.polimi.deib.se2018.server.model.player.Player;

import java.io.Serializable;

/**
 * Card activation message class
 * @author Simone Mariani, Coreena Corgado
 */
public class CardActivationMessage extends Message implements Serializable {

    private final Player player;
    private final Model model;

    /**
     * Constructor, initializes card activation message class
     * @param p player
     * @param m model
     */
    public CardActivationMessage(Player p,Model m){
        model=m;
        player=p;
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