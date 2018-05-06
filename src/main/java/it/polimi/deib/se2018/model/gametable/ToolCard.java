package it.polimi.deib.se2018.model.gametable;

import it.polimi.deib.se2018.model.dice.DiceColor;

public class ToolCard {
    private String name;
    private String description;
    private int number;
    private boolean alreadyUsed;
    private DiceColor solitaryColor;

    //Constructor
    public ToolCard(String name, String description, int number, DiceColor color){
        this.name=name;
        this.description=description;
        this.number=number;
        solitaryColor=color;
        alreadyUsed=false;
    }

    //"Getters" methods
    public DiceColor getSolitaryColor() {
        return solitaryColor;
    }

    public boolean isAlreadyUsed() {
        return alreadyUsed;
    }

    //"Setters" methods
    public void setAlreadyUsed(){
        alreadyUsed=true;
    }
}
