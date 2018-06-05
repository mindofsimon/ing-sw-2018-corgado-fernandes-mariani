package it.polimi.deib.se2018.server.model.events;

import java.io.Serializable;

public class SchemeSelection extends Event implements Serializable {

    private String playerNickname;
    private int n;

    public SchemeSelection(String playerNickname,int n){
        this.n=n;
        this.playerNickname=playerNickname;
    }

    public String getPlayerNickName() {
        return playerNickname;
    }

    public int getSchemeNumber() {
        return n;
    }
}
