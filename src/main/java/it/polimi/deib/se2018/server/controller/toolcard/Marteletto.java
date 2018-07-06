package it.polimi.deib.se2018.server.controller.toolcard;

import it.polimi.deib.se2018.server.model.Model;
import it.polimi.deib.se2018.server.model.dice.DiceColor;
import it.polimi.deib.se2018.server.model.events.Event;

import java.rmi.RemoteException;

/**
 * Marteletto class
 * @author Sirlan Fernandes
 */
public class Marteletto implements ToolCard  {
    private String name;
    private DiceColor solitaryColor;
    private int number;
    private boolean alreadyUsed;
    private boolean activated;
    private String desc;


    /**
     * Constructor, initializes marteletto class
     * @param name name
     * @param n card number
     * @param sColor solitary color
     * @param desc description of card
     */
    public Marteletto(String name, int n, DiceColor sColor,String desc){
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
        for(int i=0;i<model.getDiceStock().size();i++){
            model.getDiceStock().setDiceValue(i);
        }

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
