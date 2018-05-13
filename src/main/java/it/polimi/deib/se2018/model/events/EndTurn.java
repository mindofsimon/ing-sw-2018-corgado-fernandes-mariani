package it.polimi.deib.se2018.model.events;

import it.polimi.deib.se2018.model.player.Player;

public class EndTurn extends Event {

    private Player player;

    public EndTurn(Player p){
        player=p;
    }

    @Override
    public Player getPlayer() {
        return player;
    }
}
