package it.polimi.deib.se2018.server.model.gametable;

import it.polimi.deib.se2018.server.model.dice.Dice;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Rounds track class
 * @author Simone Mariani
 */
public class RoundsTrack implements Serializable {
    private ArrayList <Dice> diceList;
    private static RoundsTrack instance=null;

    /**
     * Creates rounds track if it doesn't already exist, else returns existing rounds track
     * @return instance
     */
    //Singleton
    public static synchronized RoundsTrack getSingletonRoundsTrack(){
        if (instance==null) instance=new RoundsTrack();
        return instance;
    }

    /**
     * Constructor, initializes round track class
     */
    //Constructor
    private RoundsTrack(){
        diceList=new ArrayList<Dice>();
    }

    //"Getters" methods
    /**
     * Gets dice list
     * @return dice list
     */
    public ArrayList<Dice> getDiceList(){
        return diceList;
    }

    //"Setters" methods
    /**
     * Inserts dice
     * @param d dice
     */
    public void insertDice(Dice d){
        diceList.add(d);
    }

    /**
     * Cancels instance
     */
    public void clear(){
        instance=null;

    }

    /**
     * Finds a dice by index
     * @param d dice
     * @return dice index
     */
    public int findDice(Dice d){
        for(int i=0;i<diceList.size();i++){
            if(diceList.get(i).getColor().equals(d.getColor())&&diceList.get(i).getValue()==d.getValue()){
                return i;
            }
        }
        return -1;
    }

    /**
     * Gets track size
     * @return track size
     */
    public int size(){
        return diceList.size();
    }


    /**
     * Shows round track
     * @return string that describes the object
     */
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
