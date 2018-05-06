package it.polimi.deib.se2018.model.gametable;

import it.polimi.deib.se2018.model.dice.Dice;
import it.polimi.deib.se2018.model.dice.DiceColor;

import java.util.ArrayList;

public class DiceBag {
    private ArrayList<Dice> diceList;

    //Constructor
    public DiceBag(){
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

            builder.append("COLORE: " + c + " RIMASTI: " + cont);
            builder.append("\n");
        }
        return builder.toString();
    }

}
