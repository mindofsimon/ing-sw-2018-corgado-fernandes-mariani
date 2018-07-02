package it.polimi.deib.se2018.server.controller.toolcard;

import it.polimi.deib.se2018.server.model.Model;
import it.polimi.deib.se2018.server.model.dice.Dice;
import it.polimi.deib.se2018.server.model.dice.DiceColor;
import it.polimi.deib.se2018.server.model.events.Event;
import it.polimi.deib.se2018.server.model.player.Player;

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

    public void activateEffect(Model model,Event event) throws RemoteException {
        Player p=model.findPlayerByName(event.getPlayerNickName());
        Dice dice= p.getPlayerScheme().getScheme()[event.getDiceRow()][event.getDiceColum()].getDice();
        p.getPlayerScheme().getScheme()[event.getDiceRow()][event.getDiceColum()].setDice(null);
        p.getPlayerScheme().getScheme()[event.getRow()][event.getColumn()].setDice(dice);


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



    public void used(){
        alreadyUsed=true;
    }

}
