package it.polimi.deib.se2018.server.controller.toolcard;

import it.polimi.deib.se2018.server.model.dice.Dice;
import it.polimi.deib.se2018.server.model.dice.DiceColor;
import it.polimi.deib.se2018.server.model.gametable.DiceStock;
import it.polimi.deib.se2018.server.model.gametable.RoundsTrack;
import it.polimi.deib.se2018.server.model.player.Player;
import it.polimi.deib.se2018.server.model.player.schemecard.SchemeCard;

import java.rmi.RemoteException;

public class Marteletto implements ToolCard  {
    private String name;
    private DiceColor solitaryColor;
    private int number;
    private boolean alreadyUsed;
    private boolean activated;

    public Marteletto(String name, int n, DiceColor sColor){
        this.name=name;
        this.number=n;
        this.solitaryColor=sColor;

    }


    public void activateEffect(DiceStock stock) throws RemoteException {
        for(int i=0;i<stock.size();i++){
            stock.setDiceValue(i);
        }

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

    public boolean isAlreadyUsed() {
        return alreadyUsed;
    }
    public String toString(){
        return("CARD: "+name+"|| NUMBER: "+number+"|| ALREADY USED? "+alreadyUsed+"|| ACTIVATED: "+activated);
    }
    public String toStringSolitary(){
        return("CARD: "+name+"|| NUMBER: "+number+"|| COLOR: "+solitaryColor+"|| ACTIVATED: "+activated);
    }
    public void setIsActivaded(boolean act){}
    public boolean getIsActivaded(){return false;}
    public DiceColor getColorDice(){return null;}
    public void setColorDice(DiceColor color){}


    public void used(){
        alreadyUsed=true;
    }

    public void activateEffect(Player p, int dr, int dc, int r, int c, Dice dice) throws RemoteException {

    }

    public void activateEffect(Dice dice, DiceStock stock, String action) throws RemoteException {

    }
    public void activateEffect(Dice dice, Dice diceR, DiceStock stock, RoundsTrack round) throws RemoteException {

    }
    public void activateEffect(Player p, int r, int c, Dice dice) throws RemoteException {

    }
}
