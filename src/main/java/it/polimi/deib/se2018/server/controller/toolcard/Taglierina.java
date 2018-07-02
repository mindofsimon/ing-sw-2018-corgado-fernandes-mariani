package it.polimi.deib.se2018.server.controller.toolcard;

import it.polimi.deib.se2018.server.model.Model;
import it.polimi.deib.se2018.server.model.dice.DiceColor;
import it.polimi.deib.se2018.server.model.events.Event;


import java.rmi.RemoteException;

public class Taglierina implements ToolCard  {
    private String name;
    private DiceColor solitaryColor;
    private int number;
    private boolean alreadyUsed;
    private boolean activated;

    public Taglierina(String name, int n, DiceColor sColor){
        this.name=name;
        this.number=n;
        this.solitaryColor=sColor;

    }

    public void activateEffect(Model model,Event event) throws RemoteException {
        int positionS=model.getDiceStock().findDice(event.getDice());
        int positionR=model.getRoundsTrack().findDice(event.getDiceRound());
        model.getDiceStock().extractDice(event.getDice());
        model.getRoundsTrack().extractDice(event.getDiceRound());
        model.getRoundsTrack().insertDiceInPosition(event.getDice(),positionR);
        model.getDiceStock().insertDiceInPosition(event.getDiceRound(),positionS);


    }

    public DiceColor getSolitaryColor() {
        return solitaryColor;
    }
    public int getNumber() {
        return number;
    }

    public int getNumberD() {
        return 0;
    }

    public Restriction getRestriction() {
        return null;
    }
    public boolean getActivated() {
        return activated;
    }
    public void activated(boolean act){activated=act;}


    public DiceColor getColorDice(){return null;}
    public void setColorDice(DiceColor color){}

    public boolean isAlreadyUsed() {
        return alreadyUsed;
    }
    public String toString(){
        return("CARD: "+name+"|| NUMBER: "+number+"|| ALREADY USED? "+alreadyUsed+"|| ACTIVATED: "+activated);
    }
    public String toStringSolitary(){
        return("CARD: "+name+"|| NUMBER: "+number+"|| COLOR: "+solitaryColor+"|| ACTIVATED: "+activated);
    }

    public void used(){
        alreadyUsed=true;
    }


}
