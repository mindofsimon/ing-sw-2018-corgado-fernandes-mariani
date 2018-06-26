package it.polimi.deib.se2018.server.model.events;

import it.polimi.deib.se2018.server.model.player.Player;

import java.io.Serializable;

public class StringMessageError extends Message implements Serializable {
    private String message;
    private Player player;
    private int type;

    public StringMessageError(String message, Player player,int type) {
        this.message = message;
        this.player=player;
        this.type=type;
    }

    public String getMessage() {
        return message;
    }
    public int getType() {
        return type;
    }

    public Player getPlayer(){return player;}

}
