package it.polimi.deib.se2018.server.model.events;

import java.io.Serializable;

public class EndTurn extends Event implements Serializable {

    private String playerNickName;

    public EndTurn(String n){
        playerNickName=n;
    }

    public String getPlayerNickName() {
        return playerNickName;
    }
}
