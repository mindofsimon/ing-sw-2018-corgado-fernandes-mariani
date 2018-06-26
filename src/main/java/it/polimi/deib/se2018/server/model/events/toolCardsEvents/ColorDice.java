package it.polimi.deib.se2018.server.model.events.toolCardsEvents;

import it.polimi.deib.se2018.server.model.dice.DiceColor;
import it.polimi.deib.se2018.server.model.events.Event;

import java.io.Serializable;

public class ColorDice extends Event implements Serializable {
    private String playerNickname;
    private DiceColor color;
    private final int numCard;


    public ColorDice(String name,DiceColor color, int num){
        playerNickname=name;
        this.color=color;
        this.numCard=num;


    }

    public String getPlayerNickName() {
        return playerNickname;
    }

    public DiceColor getColor() {
        return color;
    }
    public int getCardNumber() {
        return numCard;
    }

}
