package it.polimi.deib.se2018.model.gametable;

import it.polimi.deib.se2018.model.dice.Dice;
import it.polimi.deib.se2018.model.dice.DiceColor;

import java.util.ArrayList;

public class DiceBag {
    private ArrayList<Dice> diceList;
    private static DiceBag instance=null;

    //Singleton
    public static synchronized DiceBag getSingletonDiceBag(){
        if (instance==null) instance=new DiceBag();
        return instance;
    }

    //Constructor
    private DiceBag(){
        diceList=new ArrayList<Dice>(90);
        init();
    }

    //"Getters" methods
    public ArrayList<Dice> getDiceList(){
        return diceList;
    }

    //"Setters" methods
    public void insertDice(Dice d){
        diceList.add(d);
    }

    //To extract a random dice
    public Dice extractRandomDice(){
        int index = (int) ((Math.random() * diceList.size()));
        return diceList.remove(index);
    }

    private void init(){//initializing dice bag (18 dices x 5 colors)
        for(DiceColor c: DiceColor.values()) {
            for (int i = 0; i < 18; i++) {
                diceList.add(i, new Dice(c));
            }
        }
    }

    //return the numbers of dice in the bag
    public int size() {
        return diceList.size();
    }

    //return the dice in the position i
    public Dice get(int i) {
        return diceList.get(i);
    }

    public int numColor(DiceColor c){
        int cont = 0;
        for (int i = 0; i < diceList.size(); i++) {
            if (diceList.get(i).getColor().equals(c)) cont++;
        }
        return cont;

    }

    //clear the bag
    public void clear(){
        instance=null;

    }


    //toString() to show DiceBag
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("DICE BAG: \n");
        int cont=0;
        for(DiceColor c: DiceColor.values()) {
            cont = 0;
            for (int i = 0; i < diceList.size(); i++) {
                if (diceList.get(i).getColor().equals(c)) cont++;
            }

            builder.append("COLOR: " + c + " LEFT: " + cont);
            builder.append("\n");
        }
        builder.append("\n");
        return builder.toString();
    }

}

