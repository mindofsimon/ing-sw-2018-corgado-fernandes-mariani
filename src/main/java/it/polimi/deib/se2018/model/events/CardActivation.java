package it.polimi.deib.se2018.model.events;

import it.polimi.deib.se2018.model.player.Player;

public class CardActivation extends Event {

    private Player player;
    private int cardNumber;

    //Constructor
    public CardActivation(Player p,int n){
        player=p;
        cardNumber=n;
    }

    //"Getters" methods
    @Override
    public Player getPlayer() {
        return player;
    }

    public int getCardNumber() {
        return cardNumber;
    }
}
