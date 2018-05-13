package it.polimi.deib.se2018.model.gametable;

import it.polimi.deib.se2018.model.dice.Dice;

import java.util.ArrayList;

public class DiceStock {
    private ArrayList<Dice> diceList;
    private static DiceStock instance=null;

    //Singleton
    public static synchronized DiceStock getSingletonDiceStock(){
        if (instance==null) instance=new DiceStock();
        return instance;
    }

    //Constructor
    private DiceStock(){
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

    //To set dice value
    public void setDiceValue(int i){
        int value = (int) ((Math.random() * (6)+1));
        diceList.get(i).setValue(value);
    }

    //To find a dice
    public int findDice(Dice d){
        for(int i=0;i<diceList.size();i++){
            if(diceList.get(i).getColor().equals(d.getColor())&&diceList.get(i).getValue()==d.getValue()){
                return i;
            }
        }
        return -1;
    }

    //To extract a selected dice
    public void extractDice(Dice d){
        int i=findDice(d);
        diceList.remove(i);
    }

    //To extract a random dice
    public Dice extractRandomDice(){
        int index = (int) ((Math.random() * diceList.size()));
        return diceList.remove(index);
    }

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
