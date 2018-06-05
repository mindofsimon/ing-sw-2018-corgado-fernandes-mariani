package it.polimi.deib.se2018.server.model.gametable.publicgoalcard;

import it.polimi.deib.se2018.server.model.player.Player;

import java.io.Serializable;

public interface PublicGoalCard extends Serializable {

    public int calculateVictoryPoints(Player p);
}
