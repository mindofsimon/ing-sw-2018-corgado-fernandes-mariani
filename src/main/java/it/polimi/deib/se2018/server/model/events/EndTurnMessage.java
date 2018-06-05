package it.polimi.deib.se2018.server.model.events;

import it.polimi.deib.se2018.server.model.Model;
import it.polimi.deib.se2018.server.model.player.Player;

import java.io.Serializable;

public class EndTurnMessage extends Message implements Serializable {


    private final Player player;
    private final Model model;

    public EndTurnMessage(Player p,Model m) {
        model = m;
        player = p;
    }

    public Player getPlayer() {
        return player;
    }

    public Model getModel() {
        return model;
    }

}