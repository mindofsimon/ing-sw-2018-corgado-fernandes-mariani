package it.polimi.deib.se2018.model.events;

import it.polimi.deib.se2018.model.Model;
import it.polimi.deib.se2018.model.player.Player;

/**
 *Dice placement message class
 * @author Simone Mariani
 */
public class DicePlacementMessage extends Message {

    private final Player player;
    private final Model model;

    /**
     * Constructor, initializes dice placement message class
     * @param p p value is assigned to player
     * @param m m value is assigned to player
     */
    public DicePlacementMessage(Player p,Model m){
        model=m;
        player=p;
    }

    /**
     * Get player
     * @return player
     */
    @Override
    public Player getPlayer() {
        return player;
    }

    /**
     * Get model
     * @return model
     */
    @Override
    public Model getModel() {
        return model;
    }
}

