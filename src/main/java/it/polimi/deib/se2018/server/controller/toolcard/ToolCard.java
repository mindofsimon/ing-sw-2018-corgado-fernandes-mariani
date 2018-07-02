package it.polimi.deib.se2018.server.controller.toolcard;

import it.polimi.deib.se2018.server.model.Model;
import it.polimi.deib.se2018.server.model.dice.Dice;
import it.polimi.deib.se2018.server.model.dice.DiceColor;
import it.polimi.deib.se2018.server.model.events.Event;
import it.polimi.deib.se2018.server.model.gametable.DiceStock;
import it.polimi.deib.se2018.server.model.gametable.RoundsTrack;
import it.polimi.deib.se2018.server.model.player.Player;


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
    DiceColor getColorDice();
    void setColorDice(DiceColor color);

    //"Setters" methods
    void used();

    void activateEffect(Model model, Event event) throws RemoteException;


    String toString();

    String toStringSolitary();


}