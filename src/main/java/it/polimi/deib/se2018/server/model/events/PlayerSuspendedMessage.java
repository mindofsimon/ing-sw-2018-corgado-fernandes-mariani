package it.polimi.deib.se2018.server.model.events;

import it.polimi.deib.se2018.server.model.Model;
import it.polimi.deib.se2018.server.model.player.Player;

import java.io.Serializable;

/**
 * Player suspended message class
 * @author Simone Mariani, Coreena Corgado
 */
public class PlayerSuspendedMessage extends Message implements Serializable {

    private Player player;
    private Model model;

    /**
     * Constructor, initializes player suspended message class
     * @param p player
     * @param m model
     */
    public PlayerSuspendedMessage(Player p,Model m){
        player=p; model=m;
    }

    /**
     * Gets model
     * @return model
     */
    @Override
    public Model getModel() {
        return model;
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

