package it.polimi.deib.se2018.server.model.gametable;

import it.polimi.deib.se2018.server.model.dice.Dice;
import it.polimi.deib.se2018.server.model.dice.DiceColor;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Dice bag class
 * @author Simone Mariani
 */
public class DiceBag implements Serializable {
    private ArrayList<Dice> diceList;
    private static DiceBag instance=null;

    /**
     * Creates dice bag if it doesn't already exist, else returns existing dice bag
     * @return instance
     */
    //Singleton
    public static synchronized DiceBag getSingletonDiceBag(){
        if (instance==null) instance=new DiceBag();
        return instance;
    }

    /**
     * Constructor
     */
    //Constructor
    private DiceBag(){
        diceList=new ArrayList<Dice>(90);
        init();
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
     * Extract a random dice
     * @return random dice extracted
     */
    public Dice extractRandomDice(){
        int index = (int) ((Math.random() * diceList.size()));
        return diceList.remove(index);
    }


    /**
     * Initializes dice bag (18 dices x 5 colors)
     */
    private void init(){
        for(DiceColor c: DiceColor.values()) {
            for (int i = 0; i < 18; i++) {
                diceList.add(i, new Dice(c));
            }
        }
    }


    /**
     * Returns the numbers of dice in the bag
     * @return number of dice in the bag
     */
    public int size() {
        return diceList.size();
    }

    /**
     * Returns dice in position i
     * @param i dice position
     * @return dice position
     */
    public Dice get(int i) {
        return diceList.get(i);
    }

    /**
     * Counts number of dice with same color
     * @param c dice color
     * @return number of dice with same color
     */
    public int numColor(DiceColor c){
        int cont = 0;
        for (int i = 0; i < diceList.size(); i++) {
            if (diceList.get(i).getColor().equals(c)) cont++;
        }
        return cont;

    }

    /**
     * clear the bag
     */
    public void clear(){
        instance=null;

    }

    /**
     * Shows dice bag
     * @return string that describes the object
     */
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

