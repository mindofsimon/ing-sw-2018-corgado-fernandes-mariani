package it.polimi.deib.se2018.server.controller.toolcard;

import it.polimi.deib.se2018.server.model.dice.Dice;
import it.polimi.deib.se2018.server.model.dice.DiceColor;
import it.polimi.deib.se2018.server.model.gametable.DiceStock;
import it.polimi.deib.se2018.server.model.gametable.RoundsTrack;
import it.polimi.deib.se2018.server.model.player.Player;
import it.polimi.deib.se2018.server.model.player.schemecard.SchemeCard;

import java.rmi.RemoteException;

public class MoveDices  implements ToolCard {
    private String name;
    private DiceColor solitaryColor;
    private DiceColor colorDice;
    private int number;
    private int numberD;
    private boolean alreadyUsed;
    private Restriction restriction;
    private boolean activated;



    public MoveDices(String name, int n, Restriction res, DiceColor sColor, int num){
        this.name=name;
        this.restriction=res;
        this.number=n;
        this.numberD=num;
        this.solitaryColor=sColor;

    }

    public void activateEffect(Player p, int dr, int cr, int r, int c, Dice dice) throws RemoteException {
        p.getPlayerScheme().getScheme()[r][c].setDice(dice);


    }


    public DiceColor getSolitaryColor() {
        return solitaryColor;
    }
    public int getNumber() {
        return number;
    }
    public int getNumberD() {
        return numberD;
    }
    public Restriction getRestriction(){return restriction;}
    public boolean getActivated() {
        return activated;
    }
    public void activated(boolean act){activated=act;}
    public DiceColor getColorDice(){return colorDice;}
    public void setColorDice(DiceColor color){colorDice=color;}

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

    public void used(){
        alreadyUsed=true;
    }

    public void activateEffect(Dice dice, DiceStock stock, String action) throws RemoteException {




    }

    public void activateEffect(Dice dice, Dice diceR, DiceStock stock, RoundsTrack round) throws RemoteException {

    }
    public void activateEffect(DiceStock stock) throws RemoteException {

    }
    public void activateEffect(Player p, int r, int c, Dice dice) throws RemoteException {

    }
}
