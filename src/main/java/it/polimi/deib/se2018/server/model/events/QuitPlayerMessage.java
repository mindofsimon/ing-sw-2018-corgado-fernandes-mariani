package it.polimi.deib.se2018.server.model.events;

import it.polimi.deib.se2018.server.model.player.Player;

import java.io.Serializable;

public class QuitPlayerMessage extends Message implements Serializable {

    private Player player;

    public QuitPlayerMessage(Player p){
        player=p;
    }

    @Override
    public Player getPlayer() {
        return player;
    }
}
