package it.polimi.deib.se2018.model.events;

import it.polimi.deib.se2018.model.player.Player;

/**
 * End of turn class
 * @author Simone Mariani
 */
public class EndTurn extends Event {

    private Player player;

    /**
     * Constructor, initializes end of turn class
     * @param p p value is assigned to player
     */
    public EndTurn(Player p){
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
}
