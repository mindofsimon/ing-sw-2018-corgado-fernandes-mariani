package it.polimi.deib.se2018.server.controller.toolcard;

import it.polimi.deib.se2018.server.model.Model;
import it.polimi.deib.se2018.server.model.dice.Dice;
import it.polimi.deib.se2018.server.model.dice.DiceColor;
import it.polimi.deib.se2018.server.model.events.Event;
import it.polimi.deib.se2018.server.model.player.Player;

import java.rmi.RemoteException;

/**
 * Move dices class
 * @author Sirlan Fernandes
 */
public class MoveDices  implements ToolCard {
    private String name;
    private DiceColor solitaryColor;
    private DiceColor colorDice;
    private int number;
    private int numberD;
    private boolean alreadyUsed;
    private Restriction restriction;
    private boolean activated;


    /**
     * Constructor, initializes move dices class
     * @param name name
     * @param n card number
     * @param res restriction
     * @param sColor solitary color
     * @param num dice number
     */
    public MoveDices(String name, int n, Restriction res, DiceColor sColor, int num){
        this.name=name;
        this.restriction=res;
        this.number=n;
        this.numberD=num;
        this.solitaryColor=sColor;

    }

    /**
     * Activates tool card effect
     * @param model model
     * @param event event
     * @throws RemoteException
     */
    public void activateEffect(Model model,Event event) throws RemoteException {
        Player p=model.findPlayerByName(event.getPlayerNickName());
        Dice dice= p.getPlayerScheme().getScheme()[event.getDiceRow()][event.getDiceColum()].getDice();
        p.getPlayerScheme().getScheme()[event.getDiceRow()][event.getDiceColum()].setDice(null);
        p.getPlayerScheme().getScheme()[event.getRow()][event.getColumn()].setDice(dice);


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
     * @return dice number
     */
    public int getNumberD() {
        return numberD;
    }

    /**
     * Gets restriction
     * @return restriction
     */
    public Restriction getRestriction(){return restriction;}

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
     * @return dice color
     */
    public DiceColor getColorDice(){return colorDice;}

    /**
     * Sets dice color
     * @param color dice color
     */
    public void setColorDice(DiceColor color){colorDice=color;}

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
