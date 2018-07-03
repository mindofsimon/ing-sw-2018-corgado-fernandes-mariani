package it.polimi.deib.se2018.server.model.events.toolCardsEvents;

import it.polimi.deib.se2018.server.model.events.Message;
import it.polimi.deib.se2018.server.model.player.Player;

import java.io.Serializable;

/**
 * Select dice view class
 * For tool card number 5
 * @author Sirlan Fernandes
 */
public class SelectDiceView extends Message implements Serializable {
    private Player p;
    private int num;

    /**
     * Constructor, initializes select dice view class
     * @param p player
     * @param num tool card number
     */
    public SelectDiceView(Player p,int num){
        this.p=p;
        this.num=num;
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

}
