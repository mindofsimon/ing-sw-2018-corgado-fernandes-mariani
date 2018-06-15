package it.polimi.deib.se2018.server.model.events;

import it.polimi.deib.se2018.server.model.Model;
import it.polimi.deib.se2018.server.model.player.Player;

import java.io.Serializable;

/**
 * message class
 */
public class Message implements Serializable {


    /**
     * Gets player
     * @return player
     */
    public Player getPlayer(){
        return null;
    }

    /**
     * Gets model
     * @return null
     */
    public Model getModel(){
        return null;
    }

    /**
     * Gets final scores
     * @return null
     */
    public String getFinalScores(){
        return null;
    }
}

