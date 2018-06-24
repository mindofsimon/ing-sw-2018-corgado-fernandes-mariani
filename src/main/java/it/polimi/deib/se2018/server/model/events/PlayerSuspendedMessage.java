package it.polimi.deib.se2018.server.model.events;

import it.polimi.deib.se2018.server.model.Model;
import it.polimi.deib.se2018.server.model.player.Player;

import java.io.Serializable;

public class PlayerSuspendedMessage extends Message implements Serializable {

    private Player player;
    private Model model;

    public PlayerSuspendedMessage(Player p,Model m){
        player=p; model=m;
    }

    @Override
    public Model getModel() {
        return model;
    }

    @Override
    public Player getPlayer() {
        return player;
    }
}

