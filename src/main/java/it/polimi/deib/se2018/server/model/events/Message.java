package it.polimi.deib.se2018.server.model.events;

import it.polimi.deib.se2018.server.model.Model;
import it.polimi.deib.se2018.server.model.player.Player;

import java.io.Serializable;

public class Message implements Serializable {


    public Player getPlayer(){
        return null;
    }

    public Model getModel(){
        return null;
    }

    public String getFinalScores(){return null;}
}

