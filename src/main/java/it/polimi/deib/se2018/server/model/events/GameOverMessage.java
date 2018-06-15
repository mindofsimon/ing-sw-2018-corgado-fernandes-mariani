package it.polimi.deib.se2018.server.model.events;

import it.polimi.deib.se2018.server.model.player.Player;

import java.io.Serializable;

/**
 * Game over message class
 * @author Simone Mariani
 */
public class GameOverMessage extends Message implements Serializable {

    private Player p;
    private String finalScores;
    private boolean multiPlayer;

    /**
     * Constructor, initializes game over message class
     * @param p player
     * @param finalScores final scores
     * @param multiPlayer multiplayer
     */
    public GameOverMessage(Player p, String finalScores, boolean multiPlayer){
        this.p=p;
        this.finalScores=finalScores;
        this.multiPlayer=multiPlayer;
    }

    @Override
    /**
     * Gets player
     * @return player
     */
    public Player getPlayer() {
        return p;
    }

    /**
     * Gets final scores
     * @return final scores
     */
    public String getFinalScores(){
        return finalScores;
    }

    /**
     * Defines if it's multiplayer
     * @return multiplayer
     */
    public boolean isMultiPlayer() {
        return multiPlayer;
    }
}