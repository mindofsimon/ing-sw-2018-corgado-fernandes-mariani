package it.polimi.deib.se2018.server.model.events;

import java.io.Serializable;

/**
 * Scheme selection class
 * @author Simone Mariani, Coreena Corgado
 */
public class SchemeSelection extends Event implements Serializable {

    private String playerNickname;
    private int n;

    /**
     * Constructor, initializes scheme selection class
     * @param playerNickname player's nickname
     * @param n scheme number
     */
    public SchemeSelection(String playerNickname,int n){
        this.n=n;
        this.playerNickname=playerNickname;
    }

    /**
     * Gets player's nickname
     * @return player's nickname
     */
    public String getPlayerNickName() {
        return playerNickname;
    }

    /**
     * Gets scheme number
     * @return scheme number
     */
    public int getSchemeNumber() {
        return n;
    }
}
