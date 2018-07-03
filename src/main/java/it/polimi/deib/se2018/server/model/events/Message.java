package it.polimi.deib.se2018.server.model.events;

import it.polimi.deib.se2018.server.model.Model;
import it.polimi.deib.se2018.server.model.player.Player;

import java.io.Serializable;

/**
 * Message class
 * @author Simone Mariani, Coreena Corgado
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

    /**
     * Gets card number
     * @return -1
     */
    public int getCardNumber() {
        return -1;
    }

    /**
     * Gets card type
     * @return -1
     */
    public int getCardType() {
        return -1;
    }

    /**
     * Gets number of dices
     * @return -1
     */
    public int getNumberOfDices(){return -1;}

    /**
     * Gets single player
     * @return false
     */
    public boolean getSinglePlayer(){return false;}

    /**
     * Gets type
     * @return -1
     */
    public int getType() {
        return -1;
    }

    /**
     * Gets message
     * @return null
     */
    public String getMessage() {
        return null;
    }

}

