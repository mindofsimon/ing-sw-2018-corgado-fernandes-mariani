package it.polimi.deib.se2018.server.model.gametable.publicgoalcard;

import it.polimi.deib.se2018.server.model.player.Player;

import java.io.Serializable;

/**
 * Shade card class: public goal card
 * @author Simone Mariani,Sirlan Fernandes
 */
public class ShadeCard implements PublicGoalCard,Serializable {

    private final ShadeType type;
    private static final int P=2;
    private final String name;

    /**
     * Constructor, initializes shade card class
     * @param t type
     * @param n name
     */
    public ShadeCard(ShadeType t,String n){
        type=t;
        name=n;
    }

    /**
     * Identifies which public goal card is given and calculates total victory points
     * @param p player
     * @return total victory points depending on the values of given public card: 1 and 2, 3 and 4 or 5 and 6
     */
    public int calculateVictoryPoints(Player p){
        int  cont=0;

        if(type==ShadeType.LIGHT) cont=calculateShades(p,1,2);
        if(type==ShadeType.MEDIUM) cont=calculateShades(p,3,4);
        if(type==ShadeType.DARK) cont=calculateShades(p,5,6);

        return cont;

    }

    /**
     * Calculate number of boxes with given values
     * @param p player
     * @param val1 shade value n.1
     * @param val2 shade value n.2
     * @return total number of boxes with given values * points
     */
    private int calculateShades(Player p, int val1, int val2){
        int contA=0;
        int contB=0;
        for(int i=0;i<p.getPlayerScheme().getROWS();i++){
            for(int j=0;j<p.getPlayerScheme().getCOLS();j++){
                if(p.getPlayerScheme().getScheme()[i][j].getDice()!=null&&p.getPlayerScheme().getScheme()[i][j].getDice().getValue()==val1){
                    contA++;
                }
                if(p.getPlayerScheme().getScheme()[i][j].getDice()!=null&&p.getPlayerScheme().getScheme()[i][j].getDice().getValue()==val2){
                    contB++;
                }
            }
        }
        if(contA>=contB){
            return contB*P;
        }
        else{
            return contA*P;
        }
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

