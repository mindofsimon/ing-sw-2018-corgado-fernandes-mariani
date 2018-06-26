package it.polimi.deib.se2018.server.model.events.toolCardsEvents;

import it.polimi.deib.se2018.server.model.events.Message;
import it.polimi.deib.se2018.server.model.player.Player;

import java.io.Serializable;

public class ChooseAndPlaceView extends Message implements Serializable {
    private Player p;
    private int num;
    private int numD;


    public ChooseAndPlaceView(Player p,int num,int numD){
        this.p=p;
        this.num=num;
        this.numD=numD;

    }

    public Player getPlayer() {
        return p;
    }
    public int getCardNumber(){return num;}
    public int getNumberOfDices(){return numD;}
}
