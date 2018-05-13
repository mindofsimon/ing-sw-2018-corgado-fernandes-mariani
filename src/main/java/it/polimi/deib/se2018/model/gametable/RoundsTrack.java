package it.polimi.deib.se2018.model.gametable;

import it.polimi.deib.se2018.model.dice.Dice;

import java.util.ArrayList;

public class RoundsTrack {
    private ArrayList <Dice> diceList;
    private static RoundsTrack instance=null;

    //Singleton
    public static synchronized RoundsTrack getSingletonRoundsTrack(){
        if (instance==null) instance=new RoundsTrack();
        return instance;
    }

    //Constructor
    private RoundsTrack(){
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


    //toString() to show RoundsTrack
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ROUNDS TRACK: \n");
        for(int i = 0; i < diceList.size(); i++){
            builder.append("COLOR: "+diceList.get(i).getColor()+" VALUE: "+diceList.get(i).getValue());
            builder.append("\n");
        }
        builder.append("\n");
        return builder.toString();
    }
}
