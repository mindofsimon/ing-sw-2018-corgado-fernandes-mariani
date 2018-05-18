package it.polimi.deib.se2018.controller.toolcard;

import it.polimi.deib.se2018.model.dice.DiceColor;
import it.polimi.deib.se2018.model.gametable.DiceStock;
import it.polimi.deib.se2018.model.player.Player;

public interface ToolCard {

    //"Getters" methods
    DiceColor getSolitaryColor();

    int getNumber();

    boolean isAlreadyUsed();

    //"Setters" methods
    void used();

    void activateEffect();
    void activateEffect(Player p, int diceIndex, DiceStock ds, boolean increment);

    String toString();

    String toStringSolitary();


}