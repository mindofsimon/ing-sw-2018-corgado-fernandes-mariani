package it.polimi.deib.se2018.server.model.events.toolCardsEvents;

import it.polimi.deib.se2018.server.model.events.Message;
import it.polimi.deib.se2018.server.model.player.Player;

import java.io.Serializable;

/**
 * Chooses and places dice class
 * Event message sent to view
 * @author Sirlan Fernandes
 */
public class ChooseAndPlaceView extends Message implements Serializable {
    private Player p;
    private int num;
    private int numD;


    /**
     * Chooses and places dice from dice stock
     * @param p player
     * @param num tool card number
     * @param numD number of dices
     */
    public ChooseAndPlaceView(Player p,int num,int numD){
        this.p=p;
        this.num=num;
        this.numD=numD;

    }

    /**
     * Gets player
     * @return player
     */
    public Player getPlayer() {
        return p;
    }

    /**
     * Gets tool card number
     * @return tool card number
     */
    public int getCardNumber(){return num;}

    /**
     * Gets number of dices
     * @return number of dices
     */
    public int getNumberOfDices(){return numD;}
}
