package it.polimi.deib.se2018.server.model.events;

import java.io.Serializable;

public class SetDiff extends Event implements Serializable {

    private String playerNickName;
    private int difficult;

    public SetDiff(String n,int diff){
        playerNickName=n;
        difficult=diff;

    }

    public String getPlayerNickName() {
        return playerNickName;
    }

    public int getDifficult() {
        return difficult;
    }
}
