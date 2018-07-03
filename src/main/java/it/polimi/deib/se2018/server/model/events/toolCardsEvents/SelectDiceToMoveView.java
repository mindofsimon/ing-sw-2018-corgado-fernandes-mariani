package it.polimi.deib.se2018.server.model.events.toolCardsEvents;

import it.polimi.deib.se2018.server.model.events.Message;
import it.polimi.deib.se2018.server.model.player.Player;

import java.io.Serializable;

/**
 * Selects dice to move view
 * @author Sirlan Fernandes
 */
public class SelectDiceToMoveView extends Message implements Serializable {
    private Player p;
    private int num;
    private int numD;


    /**
     * Constructor, initializes select dice to move view class
     * @param p player
     * @param num tool card number
     * @param numD dice number
     */
    public SelectDiceToMoveView(Player p,int num,int numD){
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
