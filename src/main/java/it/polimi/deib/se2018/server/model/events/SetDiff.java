package it.polimi.deib.se2018.server.model.events;

import java.io.Serializable;

/**
 * Sets difficulty class
 * @author Simone Mariani, Coreena Corgado
 */
public class SetDiff extends Event implements Serializable {

    private String playerNickName;
    private int difficult;

    /**
     * Constructor, initializes set difficulty class
     * @param n player's nickname
     * @param diff difficulty
     */
    public SetDiff(String n,int diff){
        playerNickName=n;
        difficult=diff;
    }

    /**
     * Gets player's nickname
     * @return player's nickname
     */
    public String getPlayerNickName() {
        return playerNickName;
    }

    /**
     * Gets difficulty
     * @return difficulty
     */
    public int getDifficult() {
        return difficult;
    }
}
