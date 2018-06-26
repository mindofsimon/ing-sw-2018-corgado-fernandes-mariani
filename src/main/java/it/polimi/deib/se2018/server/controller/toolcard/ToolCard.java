package it.polimi.deib.se2018.server.controller.toolcard;

import it.polimi.deib.se2018.server.model.dice.Dice;
import it.polimi.deib.se2018.server.model.dice.DiceColor;
import it.polimi.deib.se2018.server.model.gametable.DiceStock;
import it.polimi.deib.se2018.server.model.gametable.RoundsTrack;
import it.polimi.deib.se2018.server.model.player.Player;
import it.polimi.deib.se2018.server.model.player.schemecard.SchemeCard;

import java.rmi.RemoteException;

public interface ToolCard {

    //"Getters" methods
    DiceColor getSolitaryColor();

    int getNumber();
    int getNumberD();
    Restriction getRestriction();
    boolean isAlreadyUsed();
    boolean getActivated();
    void activated(boolean act);
    void setIsActivaded(boolean act);
    boolean getIsActivaded();
    DiceColor getColorDice();
    void setColorDice(DiceColor color);

    //"Setters" methods
    void used();

    void activateEffect(Player p, int dr, int dc, int r, int c, Dice dice) throws RemoteException;
    void activateEffect(Dice dice,DiceStock stock,String action) throws RemoteException;
    void activateEffect(Dice dice,Dice diceR, DiceStock stock, RoundsTrack round) throws RemoteException;
    void activateEffect(DiceStock stock) throws RemoteException;
    void activateEffect(Player p, int r, int c, Dice dice) throws RemoteException;


    String toString();

    String toStringSolitary();


}