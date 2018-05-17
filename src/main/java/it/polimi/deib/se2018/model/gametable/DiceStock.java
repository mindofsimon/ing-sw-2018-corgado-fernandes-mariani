package it.polimi.deib.se2018.model.gametable;

import it.polimi.deib.se2018.model.dice.Dice;

import java.util.ArrayList;

/**
 * Dice stock class
 * @author Simone Mariani
 */
public class DiceStock {
    private ArrayList<Dice> diceList;
    private static DiceStock instance=null;

    /**
     * Creates dice stock if it doesn't already exist, else returns existing dice stock
     * @return instance
     */
    //Singleton
    public static synchronized DiceStock getSingletonDiceStock(){
        if (instance==null) instance=new DiceStock();
        return instance;
    }

    /**
     * Constructor, initializes dice stock class
     */
    //Constructor
    private DiceStock(){
        diceList=new ArrayList<Dice>();
    }

    //"Getters" methods
    /**
     * Get dice list
     * @return dice list
     */
    public ArrayList<Dice> getDiceList(){
        return diceList;
    }

    //"Setters" methods
    /**
     * Insert dice
     * @param d dice
     */
    public void insertDice(Dice d){
        diceList.add(d);
    }

    /**
     * Set dice value
     * @param i dice index
     */
    public void setDiceValue(int i){
        int value = (int) ((Math.random() * (6)+1));
        diceList.get(i).setValue(value);
    }

    /**
     * Find a dice
     * @param d dice
     * @return dice index if in the dice list, else returns -1
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
     * Extract a selected dice
     * @param d dice
     */
    public void extractDice(Dice d){
        int i=findDice(d);
        diceList.remove(i);
    }

    /**
     * Extract a random dice
     * @return random dice extracted
     */
    public Dice extractRandomDice(){
        int index = (int) ((Math.random() * diceList.size()));
        return diceList.remove(index);
    }

    /**
     * Get a dice
     * @param i dice index
     * @return dice index
     */
    public Dice getDice(int i){
        return diceList.get(i);
    }

    /**
     * Get stock size
     * @return stock size
     */
    public int size(){
        return diceList.size();
    }

    /**
     * Cancels instance
     */
    public void clear(){
        instance=null;

    }

    /**
     * Show dice stock
     * @return string that describes the object
     */
    //toString() to show DiceStock
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("DICE STOCK: \n");
        for(int i = 0; i < diceList.size(); i++){
            builder.append("COLOR: "+diceList.get(i).getColor()+" VALUE: "+diceList.get(i).getValue());
            builder.append("\n");
        }
        builder.append("\n");
        return builder.toString();
    }
}
