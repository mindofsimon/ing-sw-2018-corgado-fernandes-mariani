package it.polimi.deib.se2018.server.model.events;

import it.polimi.deib.se2018.server.model.player.Player;

import java.io.Serializable;

public class StringMessage extends Message implements Serializable {


    private String message;
    private Player player;

    public StringMessage(String message, Player player) {
        this.message = message;
        this.player=player;
    }

    public String getMessage() {
        return message;
    }

    public Player getPlayer(){return player;}
}
