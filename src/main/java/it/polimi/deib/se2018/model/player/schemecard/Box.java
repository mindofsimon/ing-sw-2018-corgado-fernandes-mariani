package it.polimi.deib.se2018.model.player.schemecard;

import it.polimi.deib.se2018.model.dice.Dice;
import it.polimi.deib.se2018.model.dice.DiceColor;

public class Box {//It's a white box (no color, no value)
    private Dice dice;

    public Dice getDice(){
        return dice;
    }


    public void setDice(Dice d){
        dice=d;
    }

    public DiceColor getColor(){return null;}

    public int getValue(){return 0;}

}

