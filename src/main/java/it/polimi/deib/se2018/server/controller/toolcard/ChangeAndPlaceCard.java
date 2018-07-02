package it.polimi.deib.se2018.server.controller.toolcard;

import it.polimi.deib.se2018.server.model.Model;
import it.polimi.deib.se2018.server.model.dice.DiceColor;
import it.polimi.deib.se2018.server.model.player.Player;
import it.polimi.deib.se2018.server.model.events.*;


import java.rmi.RemoteException;

public class ChangeAndPlaceCard implements ToolCard  {
    private String name;
    private DiceColor solitaryColor;
    private int number;
    private boolean alreadyUsed;
    private int numd;
    private boolean activated;

    public ChangeAndPlaceCard(String name, int n, DiceColor sColor,int numd){
        this.name=name;
        this.number=n;
        this.solitaryColor=sColor;
        this.numd=numd;

    }

    public void activateEffect(Model model,Event event) throws RemoteException {
        Player p=model.findPlayerByName(event.getPlayerNickName());
        p.getPlayerScheme().getScheme()[event.getRow()][event.getColumn()].setDice(event.getDice());

    }



    public DiceColor getSolitaryColor() {
        return solitaryColor;
    }
    public int getNumber() {
        return number;
    }

    public int getNumberD() {
        return numd;
    }
    public boolean getActivated() {
        return activated;
    }
    public void activated(boolean act){activated=act;}

    public Restriction getRestriction() {
        return null;
    }

    public boolean isAlreadyUsed() {
        return alreadyUsed;
    }
    public String toString(){
        return("CARD: "+name+"|| NUMBER: "+number+"|| ALREADY USED? "+alreadyUsed+"|| ACTIVATED: "+activated);
    }
    public String toStringSolitary(){
        return("CARD: "+name+"|| NUMBER: "+number+"|| COLOR: "+solitaryColor+"|| ACTIVATED: "+activated);
    }

    public DiceColor getColorDice(){return null;}
    public void setColorDice(DiceColor color){}



    public void used(){
        alreadyUsed=true;
    }


}

