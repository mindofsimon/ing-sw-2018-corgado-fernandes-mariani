package it.polimi.deib.se2018.model.events;

import it.polimi.deib.se2018.model.player.Player;

/**
 * Card activation class for tool cards
 * @author Simone Mariani
 */
public class CardActivation extends Event {

    private Player player;
    private int cardNumber;

    /**
     * Constructor, initializes card activation class
     * @param p p value is assigned to player
     * @param n n value is assigned to cardNumber
     */
    //Constructor
    public CardActivation(Player p,int n){
        player=p;
        cardNumber=n;
    }

    /**
     * Get player
     * @return player
     */
    //"Getters" methods
    @Override
    public Player getPlayer() {
        return player;
    }

    /**
     * Get card number
     * @return card number
     */
    public int getCardNumber() {
        return cardNumber;
    }
}
