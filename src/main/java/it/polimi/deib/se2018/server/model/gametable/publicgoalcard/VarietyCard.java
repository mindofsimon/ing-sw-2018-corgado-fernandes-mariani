package it.polimi.deib.se2018.server.model.gametable.publicgoalcard;

import it.polimi.deib.se2018.server.model.dice.DiceColor;
import it.polimi.deib.se2018.server.model.player.Player;

import java.io.Serializable;

/**
 * Variety card class: public goal card
 * @author Simone Mariani,Sirlan Fernandes
 */
public class VarietyCard implements PublicGoalCard,Serializable {

    private final ElementType elementType;
    private final int points;
    private final String name;

    /**
     * Constructor, initializes variety card class. Identifies which public goal card is given: color or shade variety
     * @param et element type
     * @param n name
     */
    public VarietyCard(ElementType et,String n){
        elementType=et;
        name=n;
        if(elementType.equals(ElementType.COLOR))points=4;
        else points=5;
    }

    /**
     * Calculates victory points depending on element type
     * @param p player
     * @return total victory points depending on element type: color or shade
     */
    public int calculateVictoryPoints(Player p){
        if(elementType.equals(ElementType.COLOR)) return calculateColorsVariety(p);
        else return calculateShadesVariety(p);
    }

    /**
     * Counts how many sets of one of each color anywhere there are
     * @param p player
     * @return sets of color variety * points
     */
    private int calculateColorsVariety(Player p){
       int cont=20;
       for(DiceColor c: DiceColor.values()) {
           if(countNumberColors(p,c)<=cont){
               cont=countNumberColors(p,c);
           }
       }
       return points*cont;
    }

    /**
     * Counts how many sets of one of each shade anywhere there are
     * @param p player
     * @return sets of shade variety * points
     */
    private int calculateShadesVariety(Player p){
        int cont=20;
        for(int i=1;i<7;i++) {
            if(countNumberShades(p,i)<=cont){
                cont=countNumberShades(p,i);
            }
        }
        return points*cont;
    }

    /**
     * Counts how many dices of a given color there are
     * @param p player
     * @param c dice's color to count
     * @return number of dices counted
     */
    private int countNumberColors(Player p,DiceColor c){
        int cont=0;

        for(int i=0;i<p.getPlayerScheme().getROWS();i++) {
            for (int j = 0; j < p.getPlayerScheme().getCOLS(); j++) {
                if (p.getPlayerScheme().getScheme()[i][j].getDice() != null && p.getPlayerScheme().getScheme()[i][j].getDice().getColor().equals(c)){
                    cont++;
                }
            }
        }
        return cont;
    }

    /**
     * Counts how many dices of a given shade there are
     * @param p player
     * @param val dice's shade to count
     * @return number of dices counted
     */
    private int countNumberShades(Player p,int val){
        int cont=0;

        for(int i=0;i<p.getPlayerScheme().getROWS();i++) {
            for (int j = 0; j < p.getPlayerScheme().getCOLS(); j++) {
                if (p.getPlayerScheme().getScheme()[i][j].getDice() != null && p.getPlayerScheme().getScheme()[i][j].getDice().getValue()==val){
                    cont++;
                }
            }
        }
        return cont;
    }

    /**
     * Object text representation
     * @return name
     */
    @Override
    public String toString() {
        return name;
    }
}

