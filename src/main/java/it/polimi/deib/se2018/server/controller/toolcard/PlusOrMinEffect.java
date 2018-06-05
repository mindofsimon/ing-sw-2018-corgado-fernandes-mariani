package it.polimi.deib.se2018.server.controller.toolcard;

import it.polimi.deib.se2018.server.model.dice.DiceColor;
import it.polimi.deib.se2018.server.model.gametable.DiceStock;
import it.polimi.deib.se2018.server.model.player.Player;


public class PlusOrMinEffect implements ToolCard {

    private String name;
    private DiceColor solitaryColor;
    private int number;
    private boolean alreadyUsed;


    public PlusOrMinEffect(String name, DiceColor solitaryColor, int number){
        this.name=name;
        this.solitaryColor=solitaryColor;
        this.number=number;
        alreadyUsed=false;
    }


    public void activateEffect(){

    }

    public void activateEffect(Player p, int diceIndex, DiceStock ds, boolean increment){
        if(increment)
            ds.getDiceList().get(diceIndex).setValue(ds.getDiceList().get(diceIndex).getValue()+1);
        else ds.getDiceList().get(diceIndex).setValue(ds.getDiceList().get(diceIndex).getValue()-1);
        used();
    }

    public DiceColor getSolitaryColor() {
        return solitaryColor;
    }

    public int getNumber() {
        return number;
    }

    public boolean isAlreadyUsed() {
        return alreadyUsed;
    }

    public void used(){
        alreadyUsed=true;
    }

    public String toString(){
        return("CARD: "+name+"|| NUMBER: "+number+"|| ALREADY USED? "+alreadyUsed);
    }

    public String toStringSolitary(){
        return("CARD: "+name+"|| NUMBER: "+number+"|| COLOR: "+solitaryColor);
    }


}