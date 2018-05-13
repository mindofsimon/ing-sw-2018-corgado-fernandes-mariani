package it.polimi.deib.se2018.model.events;

import it.polimi.deib.se2018.model.Model;
import it.polimi.deib.se2018.model.player.Player;

public class EndTurnMessage extends Message {

    private final Player player;
    private final Model model;

    public EndTurnMessage(Player p, Model m) {
        model = m;
        player = p;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public Model getModel() {
        return model;
    }

}