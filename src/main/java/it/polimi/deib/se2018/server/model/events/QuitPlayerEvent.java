package it.polimi.deib.se2018.server.model.events;

import java.io.Serializable;

public class QuitPlayerEvent extends Event implements Serializable {

    private String playerNickName;

    public QuitPlayerEvent(String name){
        playerNickName=name;
    }

    @Override
    public String getPlayerNickName() {
        return playerNickName;
    }
}