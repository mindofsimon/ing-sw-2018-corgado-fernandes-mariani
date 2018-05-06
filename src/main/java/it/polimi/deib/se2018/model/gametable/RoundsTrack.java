package it.polimi.deib.se2018.model.gametable;

import it.polimi.deib.se2018.model.dice.Dice;

import java.util.ArrayList;

public class RoundsTrack {
    private ArrayList <Dice> diceList;

    //Constructor
    public RoundsTrack(){
        diceList=new ArrayList<Dice>();
    }

    //"Getters" methods
    public ArrayList<Dice> getDiceList(){
        return diceList;
    }

    //"Setters" methods
    public void insertDice(Dice d){
        diceList.add(d);
    }

    //toString() to show RoundsRoute
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ROUNDS ROUTE: \n");
        for(int i = 0; i < diceList.size(); i++){
            builder.append("COLORE: "+diceList.get(i).getColor()+" VALORE: "+diceList.get(i).getValue());
            builder.append("\n");
        }
        return builder.toString();
    }
}
