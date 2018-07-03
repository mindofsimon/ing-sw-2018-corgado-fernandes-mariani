package it.polimi.deib.se2018.server.controller.toolcard;

import it.polimi.deib.se2018.server.model.Model;
import it.polimi.deib.se2018.server.model.dice.DiceColor;
import it.polimi.deib.se2018.server.model.events.Event;


import java.rmi.RemoteException;

/**
 * Change of dices class
 * @author Sirlan Fernandes
 */
public class ChangeDices implements ToolCard  {
    private String name;
    private DiceColor solitaryColor;
    private int number;
    private boolean alreadyUsed;
    private boolean activated;

    /**
     * Constructor, initializes change dices class
     * @param name tool card name
     * @param n tool card number
     * @param sColor solitary color
     */
    public ChangeDices(String name, int n, DiceColor sColor){
        this.name=name;
        this.number=n;
        this.solitaryColor=sColor;

    }

    /**
     * Activates tool card effect
     * @param model model
     * @param event event
     * @throws RemoteException
     */
    public void activateEffect(Model model,Event event) throws RemoteException {
        if(event.getAction().equals("N")){
            int value=model.getDiceStock().getDice(model.getDiceStock().findDice(event.getDice())).getValue();
            if(value==1)model.getDiceStock().getDice(model.getDiceStock().findDice(event.getDice())).setValue(6);
            if(value==2)model.getDiceStock().getDice(model.getDiceStock().findDice(event.getDice())).setValue(5);
            if(value==3)model.getDiceStock().getDice(model.getDiceStock().findDice(event.getDice())).setValue(4);
            if(value==4)model.getDiceStock().getDice(model.getDiceStock().findDice(event.getDice())).setValue(3);
            if(value==5)model.getDiceStock().getDice(model.getDiceStock().findDice(event.getDice())).setValue(2);
            if(value==6)model.getDiceStock().getDice(model.getDiceStock().findDice(event.getDice())).setValue(1);

        }
        else if(event.getAction().equals("I")){
            int value =model.getDiceStock().getDice(model.getDiceStock().findDice(event.getDice())).getValue();
            model.getDiceStock().getDice(model.getDiceStock().findDice(event.getDice())).setValue(value+1);


        }
        else {
            int value =model.getDiceStock().getDice(model.getDiceStock().findDice(event.getDice())).getValue();
            model.getDiceStock().getDice(model.getDiceStock().findDice(event.getDice())).setValue(value-1);

        }



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
    public Restriction getRestriction() {return null;}

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
        return("CARD: "+name+"|| NUMBER: "+number+"|| ALREADY USED? "+alreadyUsed+"|| ACTIVATED: "+activated);
    }

    /**
     * String solitary message
     * @return string message
     */
    public String toStringSolitary(){
        return("CARD: "+name+"|| NUMBER: "+number+"|| COLOR: "+solitaryColor+"|| ACTIVATED: "+activated);
    }

    /**
     * Sets to used after using a tool card
     */
    public void used(){
        alreadyUsed=true;
    }
}
