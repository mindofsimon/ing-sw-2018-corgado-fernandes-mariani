package it.polimi.deib.se2018.server.model.events.toolCardsEvents;

import it.polimi.deib.se2018.server.model.events.Message;
import it.polimi.deib.se2018.server.model.player.Player;

import java.io.Serializable;

public class SelectDicesView extends Message implements Serializable {
    private Player p;
    private int num;

    public SelectDicesView(Player p,int num){
        this.p=p;
        this.num=num;


    }

    public Player getPlayer() {
        return p;
    }
    public int getCardNumber(){return num;}
}
