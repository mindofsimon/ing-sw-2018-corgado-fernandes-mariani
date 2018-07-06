package it.polimi.deib.se2018.server.controller.toolcard;

import it.polimi.deib.se2018.server.model.Model;
import it.polimi.deib.se2018.server.model.dice.DiceColor;
import it.polimi.deib.se2018.server.model.player.Player;
import it.polimi.deib.se2018.server.model.events.*;


import java.rmi.RemoteException;
/**
 * Changes and places card class
 * @author Sirlan Fernandes
 */
public class ChangeAndPlaceCard implements ToolCard  {
    private String name;
    private DiceColor solitaryColor;
    private int number;
    private boolean alreadyUsed;
    private int numd;
    private boolean activated;
    private String desc;

    /**
     * Constructor, initializes change and place card class
     * @param name tool card name
     * @param n tool card number
     * @param sColor solitary color
     * @param numd number of dices
     * @param desc description of card
     */
    public ChangeAndPlaceCard(String name, int n, DiceColor sColor,int numd,String desc){
        this.name=name;
        this.number=n;
        this.solitaryColor=sColor;
        this.numd=numd;
        this.desc=desc;

    }

    /**
     * Activates tool card effect
     * @param model model
     * @param event event
     * @throws RemoteException
     */
    public void activateEffect(Model model,Event event) throws RemoteException {
        Player p=model.findPlayerByName(event.getPlayerNickName());
        p.getPlayerScheme().getScheme()[event.getRow()][event.getColumn()].setDice(event.getDice());

    }


    /**
     * Gets solitary color
     * @return solitary color
     */
    public DiceColor getSolitaryColor() {
        return solitaryColor;
    }

    /**
     * Gets number
     * @return number
     */
    public int getNumber() {
        return number;
    }

    /**
     * Gets number of dices
     * @return number of dices
     */
    public int getNumberD() {
        return numd;
    }


    /**
     * Checks if tool card is activated
     * @return true if activated
     */
    public boolean getActivated() {
        return activated;
    }


    /**
     * Sets to activated or not
     * @param act activated
     */
    public void activated(boolean act){activated=act;}

    /**
     * Gets restriction
     * @return null
     */
    public Restriction getRestriction() {
        return null;
    }

    /**
     * Checks if card has already been used
     * @return true if already used
     */
    public boolean isAlreadyUsed() {
        return alreadyUsed;
    }

    /**
     * String message
     * @return string message
     */
    public String toString(){
        return("CARD: "+name+"||NUMBER: "+number+"\nDESCRIPTION: "+desc+"\nALREADY USED? "+alreadyUsed+"||ACTIVATED: "+activated);
    }

    /**
     * String solitary message
     * @return string message
     */
    public String toStringSolitary(){
        return("CARD: "+name+"||NUMBER: "+number+"\nDESCRIPTION: "+desc+"\nCOLOR: "+solitaryColor+"||ACTIVATED: "+activated);
    }

    /**
     * Gets dice color
     * @return null
     */
    public DiceColor getColorDice(){return null;}

    /**
     * Sets dice color
     * @param color dice color
     */
    public void setColorDice(DiceColor color){}


    /**
     * Sets to used after using a tool card
     */
    public void used(){
        alreadyUsed=true;
    }


}

