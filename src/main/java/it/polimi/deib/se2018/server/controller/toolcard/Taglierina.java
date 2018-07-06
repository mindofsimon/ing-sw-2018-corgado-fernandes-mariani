package it.polimi.deib.se2018.server.controller.toolcard;

import it.polimi.deib.se2018.server.model.Model;
import it.polimi.deib.se2018.server.model.dice.DiceColor;
import it.polimi.deib.se2018.server.model.events.Event;


import java.rmi.RemoteException;

/**
 * Taglierina class
 * @author Sirlan Fernandes
 */
public class Taglierina implements ToolCard  {
    private String name;
    private DiceColor solitaryColor;
    private int number;
    private boolean alreadyUsed;
    private boolean activated;
    private String desc;

    /**
     * Constructor, initializes taglierina class
     * @param name name
     * @param n card number
     * @param sColor solitary color
     */
    public Taglierina(String name, int n, DiceColor sColor,String desc){
        this.name=name;
        this.number=n;
        this.solitaryColor=sColor;
        this.desc=desc;

    }

    /**
     * Activates tool card effect
     * @param model model
     * @param event event
     * @throws RemoteException
     */
    public void activateEffect(Model model,Event event) throws RemoteException {
        int positionS=model.getDiceStock().findDice(event.getDice());
        int positionR=model.getRoundsTrack().findDice(event.getDiceRound());
        model.getDiceStock().extractDice(event.getDice());
        model.getRoundsTrack().extractDice(event.getDiceRound());
        model.getRoundsTrack().insertDiceInPosition(event.getDice(),positionR);
        model.getDiceStock().insertDiceInPosition(event.getDiceRound(),positionS);


    }

    /**
     * Gets solitary color
     * @return solitary color
     */
    public DiceColor getSolitaryColor() {
        return solitaryColor;
    }

    /**
     * Gets card number
     * @return card number
     */
    public int getNumber() {
        return number;
    }

    /**
     * Gets dice number
     * @return 0
     */
    public int getNumberD() {
        return 0;
    }

    /**
     * Gets restriction
     * @return null
     */
    public Restriction getRestriction() {
        return null;
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
     * Gets dice color
     * @return null
     */
    public DiceColor getColorDice(){return null;}

    /**
     * Sets dice colorr
     * @param color dice color
     */
    public void setColorDice(DiceColor color){}

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
     * Sets to used after using a tool card
     */
    public void used(){
        alreadyUsed=true;
    }


}
