package it.polimi.deib.se2018.server.model.events;

import it.polimi.deib.se2018.server.model.player.Player;

import java.io.Serializable;

public class GameOverMessage extends Message implements Serializable {

    private Player p;
    private String finalScores;
    private boolean multiPlayer;

    public GameOverMessage(Player p, String finalScores, boolean multiPlayer){
        this.p=p;
        this.finalScores=finalScores;
        this.multiPlayer=multiPlayer;
    }

    @Override
    public Player getPlayer() {
        return p;
    }

    public String getFinalScores(){
        return finalScores;
    }

    public boolean isMultiPlayer() {
        return multiPlayer;
    }
}